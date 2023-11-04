package com.wang.study.ai.function;

import com.wang.study.ai.util.NumUtil;

/**
 * 线性函数 w1x1+w2x2+...+wnxn+b
 * 其中b=w0x0(其中x0==1), 所以b=w0
 */
public class LinearFunction extends BaseFunction {


    public double f(Object... params) {
        double[] w = (double[])params[0];
        double[] x = (double[])params[1];
        return NumUtil.matrixMultiply(w,x);
    }

    public double df(Object... params) {
        double[] w = (double[])params[0];
        Integer index = (Integer)params[1];
        return w[index];
    }
}
