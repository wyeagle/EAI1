package com.wang.study.ai.tensor;

import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.util.NumUtil;

import java.util.Arrays;

public class Tensor3 extends Tensor{
    Tensor2[] _value;

    @Override
    protected void setValue1(Location loc, double value) {
        _value[loc.loc()[0]].setValue1(loc.subLoc(),value);
    }

    @Override
    protected double getValue1(Location loc) {
        return _value[loc.loc()[0]].getValue1(loc.subLoc());
    }

    @Override
    public Tensor sliceSubD(int loc) {
        return _value[loc];
    }

    @Override
    public double max() {
        double[] maxValues = new double[_value.length];
        for(int i=0;i<_value.length;i++){
            maxValues[i] = _value[i].max();
        }
        return NumUtil.max(maxValues);
    }

    @Override
    public Tensor slice2D(int startH, int startW, int endH, int endW) {
        Tensor2[] newValue = new Tensor2[_value.length];
        for(int i=0;i<_value.length;i++){
            newValue[i] = (Tensor2)_value[i].slice2D(startH,startW,endH,endW);
        }
        Tensor3 t3 = (Tensor3)Tensors.create(mixShape(this,newValue[0]));
        t3._value = newValue;
        return t3;
    }

    @Override
    public void padding(int padNum, double value) {
        for(Tensor2 t2:_value){
            t2.padding(padNum,value);
        }
        _shape[_shape.length-2] = _value[0].height();
        _shape[_shape.length-1] = _value[0].width();
    }

    @Override
    protected void add1(Tensor t) {
        Tensor3 dest3 = (Tensor3)t;
        for(int i=0;i<_value.length;i++){
            _value[i].add1(dest3._value[i]);
        }
    }

    @Override
    public void func(ActivationFunction af) {
        for(Tensor2 t2:_value){
            t2.func(af);
        }
    }

    @Override
    void createData(double min, double max) {
        int[] subShape = Arrays.copyOfRange(_shape,1,_shape.length);
        for(int i=0;i<_value.length;i++){
            _value[i] = (Tensor2)Tensors.create(NumUtil.clone(subShape),min,max);
        }
    }

    @Override
    protected void init() {
        _value = new Tensor2[numOfD(0)];
        int[] subShape = Arrays.copyOfRange(_shape,1,_shape.length);
        for(int i=0;i<_value.length;i++){
            _value[i] = (Tensor2)Tensors.create(NumUtil.clone(subShape));
        }
    }

    @Override
    public Tensor conv(Tensor t) {
        Tensor3 t3 = (Tensor3)t;
        Tensor0 nt = null;
        for(int i=0;i<_value.length;i++){
            Tensor0 tmpt = (Tensor0)_value[i].conv(t3._value[i]);
            if(nt == null){
                nt = tmpt;
                continue;
            }
            nt._value += tmpt._value;
        }
        return nt;
    }

    @Override
    public Tensor product(Tensor t) {
        Tensor3 t3 = (Tensor3)t;
        Tensor3 nt = null;
        for(int i=0;i<_value.length;i++){
            Tensor2 tmpt = (Tensor2)_value[i].product(t3._value[i]);
            if(nt == null){
                nt = (Tensor3)Tensors.create(mixShape(this,tmpt));
            }
            nt._value[i] = tmpt;
        }
        return nt;
    }

    @Override
    public double[] flat1D() {
        double[] ds3 = new double[numValues()];
        int index = 0;
        for(int i=0;i<depth();i++) {
            double[] ds = _value[i].flat1D();
            for (int j = 0; j < ds.length; j++) {
                ds3[index++] = ds[j];
            }
        }
        return ds3;
    }
}
