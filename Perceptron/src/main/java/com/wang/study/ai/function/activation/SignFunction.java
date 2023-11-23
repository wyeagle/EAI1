package com.wang.study.ai.function.activation;

/**
 * 仅供感知器使用的激活函数，其他网络不能使用，因为其导数为0，无法做反向传播
 */
public class SignFunction extends ActivationFunction {

    protected double func(Object... params) {
        double x = (Double)params[0];
        if(x > 0){
            return 1d;
        }
        return -1d;
    }

    protected double dfunc(Object... params){
        return 0d;
    }
}
