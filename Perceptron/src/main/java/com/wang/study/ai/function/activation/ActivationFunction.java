package com.wang.study.ai.function.activation;

import com.wang.study.ai.function.BaseFunction;
import com.wang.study.ai.util.NumUtil;

public abstract class ActivationFunction extends BaseFunction {
    public static ActivationFunction newInstance(ActivationEnum ae){
        ActivationFunction af = null;
        switch(ae){
            case Relu:af = new ReluFunction();
            case LeakyRelu:af = new LeakyReluFunction();
            case Sigmoid:af = new SigmoidFunction();
            case Softmax:af = new SoftmaxFunction();
            case Sign:af = new SignFunction();
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
