package com.wang.study.ai.function.activation;


/**
 *
 */
public class ReluFunction extends ActivationFunction {

    public double f(Object... params) {
        Double x = super.actualValue(params);
        if(x >=0){
            return x;
        }
        return 0;
    }

    public double df(Object... params){
        Double x = super.actualValue(params);
        if(x >=0){
            return 1;
        }
        return 0;
    }

}
