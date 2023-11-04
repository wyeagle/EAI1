package com.wang.study.ai.function.activation;

public class SameFunction extends ActivationFunction {
    public double f(Object... params) {
        Double x = (Double)params[0];
        return x;
    }

    public double df(Object... params) {
        return 1d;
    }
}
