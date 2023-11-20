package com.wang.study.ai.function;

import com.wang.study.ai.common.EAIException;
import com.wang.study.ai.util.PubUtil;

public abstract class BaseFunction {
    protected abstract double func(Object... params);

    protected abstract double dfunc(Object... params);


    public double f(Object... params){
        Double d = func(params);
        if(d.isNaN() || d.isInfinite()){
            throw new EAIException(this.getClass().getSimpleName()+": params = "+ PubUtil.print(params)+": result f = "+d);
        }
        return d;
    }

    public double df(Object... params){
        Double d = dfunc(params);
        if(d.isNaN() || d.isInfinite()){
            throw new EAIException(this.getClass().getSimpleName()+": params = "+ PubUtil.print(params)+": result df = "+d);
        }
        return d;
    }
}
