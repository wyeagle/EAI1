package com.wang.study.ai.function.activation;


/**
 *
 */
public class LeakyReluFunction extends ActivationFunction {
    private double _a = 0.01;
    public LeakyReluFunction(){
    }
    public LeakyReluFunction(double a){
        _a = a;
    }

    protected double func(Object... params) {
        Double x = super.actualValue(params);
        if(x <= 0) {
            x = _a * x;
        }
        return x;
    }

    protected double dfunc(Object... params){
        Double x = super.actualValue(params);
        if(x > 0){
            return 1;
        }
        return _a;
    }

}
