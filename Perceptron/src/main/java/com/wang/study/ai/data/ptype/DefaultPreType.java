package com.wang.study.ai.data.ptype;

public class DefaultPreType extends PreType{
    @Override
    public void pre() {

    }

    @Override
    public double[] adaptExpectedValue(double[] eds) {
        return eds;
    }

    @Override
    public double[] adaptParams(double[] params) {
        return params;
    }
}
