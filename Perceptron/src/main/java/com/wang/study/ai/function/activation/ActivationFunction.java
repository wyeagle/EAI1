package com.wang.study.ai.function.activation;

import com.wang.study.ai.function.BaseFunction;

public abstract class ActivationFunction extends BaseFunction {
    protected double[] allOutputs(Object... params){
        return (double[])params[1];
    }

    protected double actualValue(Object... params){
        return (double)params[0];
    }

}
