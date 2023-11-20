package com.wang.study.ai.function.activation;

public class SignFunction extends ActivationFunction {

    protected double func(Object... params) {
        double x = (Double)params[0];
        if(x > 0){
            return 1d;
        }
        return -1d;
    }

    protected double dfunc(Object... params){
        return 0d;
    }
}
