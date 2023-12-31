package com.wang.study.ai.function.activation;


/**
 *
 */
public class SigmoidFunction extends ActivationFunction {

    protected double func(Object... params) {
        Double x = super.actualValue(params);
        Double r = calc(x);
        return r;
    }

    protected double dfunc(Object... params){
        double df = calc(super.actualValue(params));
        df = df*(1-df);
        return df;
    }

    protected double calc(double x){
        return 1/ (1+Math.exp(-x));
    }

}
