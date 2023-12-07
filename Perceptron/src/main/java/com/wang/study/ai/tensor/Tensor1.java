package com.wang.study.ai.tensor;

import com.wang.study.ai.common.EAIException;
import com.wang.study.ai.function.BaseFunction;
import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.util.NumUtil;

public class Tensor1 extends Tensor{
    double[] _value;

    @Override
    protected void init() {
        _value = new double[_shape[0]];
    }

    @Override
    protected void setValue1(Location loc, double value) {
        int index = loc.loc()[0];
        _value[index] = value;
    }

    @Override
    protected double getValue1(Location loc) {
        int index = loc.loc()[0];
        return _value[index];
    }

    @Override
    public Tensor sliceSubD(int loc) {
        int[] shape = new int[0];

        Tensor subT = Tensors.create(shape,_value[loc]);

        return subT;
    }

    @Override
    public double max() {
        return NumUtil.max(_value);
    }

    @Override
    public Tensor slice2D(int startH, int startW, int endH, int endW) {
        unsupoort();
        return null;
    }

    @Override
    public void padding(int padNum, double value) {
        int size = _value.length+2*padNum;
        double[] newValue = new double[size];
        for(int i=0;i<size;i++){
            if(i<padNum || i>=padNum+_value.length){
                newValue[i] = value;
                continue;
            }
            newValue[i] = _value[i-padNum];
        }
        _value = newValue;
        _shape[0] = size;
    }

    @Override
    protected void add1(Tensor t) {
        Tensor1 t1 = (Tensor1)t;
        for(int i=0;i<_value.length;i++){
            _value[i] += t1._value[i];
        }
    }

    @Override
    public void func(BaseFunction af, boolean isFunc) {
        for(int i=0;i<_value.length;i++){
            if(isFunc)
                _value[i] = af.f(_value[i]);
            else
                _value[i] = af.df(_value[i]);
        }
    }

    @Override
    void createData(double min, double max) {
        if(min == max && min == 0){
            return;
        }
        for(int i=0;i<_value.length;i++){
            _value[i] = NumUtil.random(min,max);
        }
    }

    @Override
    public Tensor conv(Tensor t) {
        Tensor1 t1 = (Tensor1)t;
        Tensor0 newT = (Tensor0)Tensors.create(new int[0]);
        double value = 0d;
        for(int i=0;i<_value.length;i++){
            value += _value[i]*t1._value[i];
        }
        Location loc = new Location(new int[0]);
        newT._value = value;
        return newT;
    }

    @Override
    public Tensor matmul(Tensor t) {
        Tensor1 t1 = (Tensor1)t;
        Tensor1 newT = (Tensor1)Tensors.create(_shape,0);
        for(int i=0;i<_value.length;i++){
            newT._value[i] = t1._value[i]*_value[i];
        }
        return newT;
    }

    @Override
    public double[] flat1D() {
        return _value;
    }

    protected void subtract1(Tensor t){
        Tensor1 t1 = (Tensor1)t;
        for(int i=0;i<_value.length;i++){
            _value[i] -= t1._value[i];
        }
    }
    protected void multiply1(Tensor t){
        Tensor1 t1 = (Tensor1)t;
        for(int i=0;i<_value.length;i++){
            _value[i] *= t1._value[i];
        }
    }

    protected void divide1(Tensor t){
    	Tensor1 t1 = (Tensor1)t;
    	for(int i=0;i<_value.length;i++){
    		_value[i] /= t1._value[i];
    	}
    }

    public void add(double d){
    	for(int i=0;i<_value.length;i++){
    		_value[i] += d;
    	}
    }

    public void multiply(double d){
        for(int i=0;i<_value.length;i++){
            _value[i] *= d;
        }
    }

    @Override
    public Object clone() {
        Tensor1 cloneTensor = (Tensor1)super.clone();
        cloneTensor._value = NumUtil.clone(_value);
        return cloneTensor;
    }
}
