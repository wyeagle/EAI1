package com.wang.study.ai.function.activation;

public class SameFunction extends ActivationFunction {
    protected double func(Object... params) {
        Double x = (Double)params[0];
        return x;
    }

    protected double dfunc(Object... params) {
        return 1d;
    }
}
