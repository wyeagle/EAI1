package com.wang.study.ai.function.activation;

import com.wang.study.ai.function.BaseFunction;
import com.wang.study.ai.util.NumUtil;

public abstract class ActivationFunction extends BaseFunction {
    public static ActivationFunction newInstance(ActivationEnum ae){
        ActivationFunction af = null;
        switch(ae){
            case Relu:af = new ReluFunction();break;
            case LeakyRelu:af = new LeakyReluFunction();break;
            case Sigmoid:af = new SigmoidFunction();break;
            case Softmax:af = new SoftmaxFunction();break;
            case Sign:af = new SignFunction();break;
        }

        return af;
    }
    protected double[] allOutputs(Object... params){
        return NumUtil.clone((double[])params[1]);
    }

    protected double actualValue(Object... params){
        return (double)params[0];
    }

}
