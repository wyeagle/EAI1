package com.wang.study.ai.tensor;

import com.wang.study.ai.function.BaseFunction;
import com.wang.study.ai.util.NumUtil;

import java.util.Arrays;

public abstract class CompositeTensor extends Tensor{

    Tensor[] _value;

    protected abstract Tensor newSubTensor();

    protected abstract Tensor[] newSubTensors(int length);

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
        Tensor[] newValue = newSubTensors(_value.length);
        for(int i=0;i<_value.length;i++){
            newValue[i] = _value[i].slice2D(startH,startW,endH,endW);
        }
        CompositeTensor t3 = (CompositeTensor)Tensors.create(mixShape(this,newValue[0]));
        t3._value = newValue;
        return t3;
    }

    @Override
    public void padding(int padNum, double value) {
        for(Tensor t2:_value){
            t2.padding(padNum,value);
        }
        _shape[_shape.length-2] = _value[0].height();
        _shape[_shape.length-1] = _value[0].width();
    }

    @Override
    protected void add1(Tensor t) {
        CompositeTensor dest3 = (CompositeTensor)t;
        for(int i=0;i<_value.length;i++){
            _value[i].add1(dest3._value[i]);
        }
    }

    @Override
    public void func(BaseFunction af, boolean isFunc) {
        for(Tensor t2:_value){
            t2.func(af,isFunc);
        }
    }

    @Override
    void createData(double min, double max) {
        int[] subShape = Arrays.copyOfRange(_shape,1,_shape.length);
        for(int i=0;i<_value.length;i++){
            _value[i] = Tensors.create(NumUtil.clone(subShape),min,max);
        }
    }

    @Override
    protected void init() {
        _value = newSubTensors(numOfD(0));
        int[] subShape = Arrays.copyOfRange(_shape,1,_shape.length);
        for(int i=0;i<_value.length;i++){
            _value[i] = Tensors.create(NumUtil.clone(subShape));
        }
    }

    @Override
    public Tensor conv(Tensor t) {
        CompositeTensor t3 = (CompositeTensor)t;
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
    public Tensor matmul(Tensor t) {
        CompositeTensor t3 = (CompositeTensor)t;
        CompositeTensor nt = null;
        for(int i=0;i<_value.length;i++){
            Tensor tmpt = _value[i].matmul(t3._value[i]);
            if(nt == null){
                nt = (CompositeTensor)Tensors.create(mixShape(this,tmpt));
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

    protected void subtract1(Tensor t){
        CompositeTensor t3 = (CompositeTensor)t;
        for(int i=0;i<_value.length;i++){
            _value[i].subtract1(t3._value[i]);
        }
    }
    protected void multiply1(Tensor t){
        CompositeTensor t3 = (CompositeTensor)t;
        for(int i=0;i<_value.length;i++){
            _value[i].multiply1(t3._value[i]);
        }
    }
    protected void divide1(Tensor t){
        CompositeTensor t3 = (CompositeTensor)t;
        for(int i=0;i<_value.length;i++){
            _value[i].divide1(t3._value[i]);
        }
    }

    public void add(double d){
        for(int i=0;i<_value.length;i++){
            _value[i].add(d);
        }
    }

    public void multiply(double d){
        for(int i=0;i<_value.length;i++){
            _value[i].multiply(d);
        }
    }

    @Override
    public Object clone() {
        CompositeTensor cloneTensor = (CompositeTensor)super.clone();
        cloneTensor._value = newSubTensors(_value.length);
        for(int i=0;i<_value.length;i++){
            cloneTensor._value[i] = (CompositeTensor)_value[i].clone();
        }
        return cloneTensor;
    }

    @Override
    public Tensor transpose(){
        CompositeTensor nt = (CompositeTensor) Tensors.create(new int[]{_shape[0],_shape[2],_shape[1]});
        nt._value = newSubTensors(nt._shape[0]);
        for(int i=0;i<nt._shape[0];i++){
            nt._value[i] = _value[i].transpose();
        }
        return nt;
    }
}
