package com.wang.study.ai.function.cost;

import com.wang.study.ai.function.BaseFunction;

public abstract class CostFunction extends BaseFunction {

    protected Double acutalValue(Object... params){
        return (Double)params[0];
    }

    protected Double expectedValue(Object... params){
        return (Double)params[1];
    }

    public abstract double fByArray(double[] actuals, double[] expecteds);
}
