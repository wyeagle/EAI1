package com.wang.study.ai.data.ptype;

public class DigitPreType extends DefaultPreType{

    @Override
    public double[] adaptExpectedValue(double[] eds) {
        double[] nds = new double[10];

        for(int i=0;i<nds.length;i++){
            if(i == eds[0]){
                nds[i] = 1;
                continue;
            }
            nds[i] = 0;
        }
        return nds;
    }

    @Override
    public double[] adaptParams(double[] params) {
        for(int i=0;i<params.length;i++){
            params[i] = params[i]/256;
        }
        return params;
    }
}
