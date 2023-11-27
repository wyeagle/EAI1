package com.wang.study.ai.tensor;

import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.util.NumUtil;

public class Tensor0 extends Tensor{
    double _value;

    @Override
    protected void setValue1(Location loc, double value) {
        _value = value;
    }

    @Override
    protected double getValue1(Location loc) {
        return _value;
    }

    @Override
    public Tensor sliceSubD(int loc) {
        unsupoort();
        return null;
    }

    @Override
    public double max() {
        return _value;
    }

    @Override
    public Tensor slice2D(int startH, int startW, int endH, int endW) {
        unsupoort();
        return null;
    }

    @Override
    protected void init() {

    }

    @Override
    public void padding(int padNum, double value) {
        unsupoort();
    }

    @Override
    protected void add1(Tensor t) {
        Tensor0 t0 = (Tensor0)t;
        _value += t0._value;
    }

    @Override
    public void func(ActivationFunction af) {
        _value = af.f(_value);
    }

    @Override
    void createData(double min, double max) {
        _value = NumUtil.random(min,max);
    }

    @Override
    public Tensor conv(Tensor t) {
        Tensor0 t0 = (Tensor0)t;
        Tensor0 newConv = (Tensor0)Tensors.create(shape());
        newConv._value = _value * t0._value;
        return newConv;
    }

    @Override
    public Tensor product(Tensor t) {
        return conv(t);
    }

    @Override
    public double[] flat1D() {
        return new double[]{_value};
    }
}
