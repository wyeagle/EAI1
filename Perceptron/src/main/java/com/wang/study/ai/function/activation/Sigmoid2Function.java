package com.wang.study.ai.function.activation;


/**
 *
 */
public class Sigmoid2Function extends SigmoidFunction {

    public double f(Object... params) {
        double d = super.f(params);
        if(d >= 0.5){
            d = 1;
        }else{
            d = 0;
        }
        return d;
    }
}
