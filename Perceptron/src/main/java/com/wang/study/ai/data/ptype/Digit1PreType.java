package com.wang.study.ai.data.ptype;

public class Digit1PreType extends DefaultPreType{

    @Override
    public double[] adaptExpectedValue(double[] eds) {
        eds[0] = eds[0]/10+0.05;
        return eds;
    }

    @Override
    public double[] adaptParams(double[] params) {
        for(int i=0;i<params.length;i++){
            params[i] = params[i]/256;
        }
        return params;
    }
}
