package com.wang.study.ai.function.cost;

/**
 * Mean Square Error 均方误差
 * 1/n* sum(Yr-Ye)2  Yr-实际值  Ye-期望值， n是sum的个数
 */
public class MSECostFunction extends CostFunction {

    protected double func(Object... params) {
        double diff = acutalValue(params)-expectedValue(params);
        diff *= diff;
        //diff = diff/2;
        return diff;
    }

    protected double dfunc(Object... params) {
        double df = 2*(acutalValue(params)-expectedValue(params));
        return df;
    }

    protected double funcByArray(double[] actuals, double[] expecteds){
        double diff = 0d;
        for(int i=0;i<actuals.length;i++){
            diff += f(actuals[i],expecteds[i]);
        }
        diff = diff/actuals.length;
        return diff;
    }
}
