package com.wang.study.ai.function.activation;


/**
 *
 */
public class SigmoidFunction extends ActivationFunction {

    public double f(Object... params) {
        Double x = (Double)params[0];
        Double r = calc(x);
        return r;
    }

    public double df(Object... params){
        double df = calc((Double)params[0]);
        df = df*(1-df);
        return df;
    }

    protected double calc(double x){
        return 1/ (1+Math.exp(-x));
    }
}
