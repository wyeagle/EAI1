package com.wang.study.ai.function.activation;


/**
 *
 */
public class ReluFunction extends ActivationFunction {

    protected double func(Object... params) {
        Double x = super.actualValue(params);
        if(x > 0){
            return x;
        }
        return 0;
    }

    protected double dfunc(Object... params){
        Double x = super.actualValue(params);
        if(x > 0){
            return 1;
        }
        return 0;
    }

}
