package com.wang.study.ai.function.activation;

public class SignFunction extends ActivationFunction {

    public double f(Object... params) {
        double x = (Double)params[0];
        if(x > 0){
            return 1d;
        }
        return -1d;
    }

    public double df(Object... params){
        return 0d;
    }
}
