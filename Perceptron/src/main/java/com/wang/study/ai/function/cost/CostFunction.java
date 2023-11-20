package com.wang.study.ai.function.cost;

import com.wang.study.ai.common.EAIException;
import com.wang.study.ai.function.BaseFunction;
import com.wang.study.ai.util.PubUtil;

public abstract class CostFunction extends BaseFunction {

    protected Double acutalValue(Object... params){
        return (Double)params[0];
    }

    protected Double expectedValue(Object... params){
        return (Double)params[1];
    }

    protected abstract double funcByArray(double[] actuals, double[] expecteds);

    public double fByArray(double[] actuals, double[] expecteds){
        Double d = funcByArray(actuals,expecteds);
        if(d.isNaN() || d.isInfinite()){
            throw new EAIException(this.getClass().getSimpleName()+": actuals = "+ PubUtil.print(actuals)+
                    ":expecteds = "+PubUtil.print(expecteds)+": result f = "+d);
        }
        return d;
    }
}
