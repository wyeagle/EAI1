package com.wang.study.ai.tensor;

import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.util.NumUtil;

import java.util.Arrays;

public class Tensor4 extends Tensor{
    Tensor3[] _value;

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
        Tensor3[] newValue = new Tensor3[_value.length];
        for(int i=0;i<_value.length;i++){
            newValue[i] = (Tensor3)_value[i].slice2D(startH,startW,endH,endW);
        }
        Tensor4 t3 = (Tensor4)Tensors.create(mixShape(this,newValue[0]));
        t3._value = newValue;
        return t3;
    }

    @Override
    public void padding(int padNum, double value) {
        for(Tensor3 t3:_value){
            t3.padding(padNum,value);
        }
        _shape[_shape.length-2] = _value[0].height();
        _shape[_shape.length-1] = _value[0].width();
    }

    @Override
    protected void add1(Tensor t) {
        Tensor4 dest4 = (Tensor4)t;
        for(int i=0;i<_value.length;i++){
            _value[i].add1(dest4._value[i]);
        }
    }

    @Override
    public void func(ActivationFunction af) {
        for(Tensor3 t3:_value){
            t3.func(af);
        }
    }

    @Override
    void createData(double min, double max) {
        _value = new Tensor3[numOfD(0)];
        int[] subShape = Arrays.copyOfRange(_shape,1,_shape.length);
        for(int i=0;i< _value.length;i++){
            _value[i] = (Tensor3)Tensors.create(NumUtil.clone(subShape),min,max);
        }
    }

    @Override
    protected void init() {
        _value = new Tensor3[numOfD(0)];
    }

    @Override
    public Tensor conv(Tensor t) {
        Tensor4 t4 = (Tensor4)t;
        Tensor0 nt = null;
        for(int i=0;i<_value.length;i++){
            Tensor0 tmpt = (Tensor0)_value[i].conv(t4._value[i]);
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
        Tensor4 t3 = (Tensor4)t;
        Tensor4 nt = null;
        for(int i=0;i<_value.length;i++){
            Tensor3 tmpt = (Tensor3)_value[i].product(t3._value[i]);
            if(nt == null){
                nt = (Tensor4)Tensors.create(mixShape(this,tmpt));
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
