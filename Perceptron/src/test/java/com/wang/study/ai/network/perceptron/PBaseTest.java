package com.wang.study.ai.network.perceptron;

import com.wang.study.ai.common.CommonTest;
import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.function.LinearFunction;
import com.wang.study.ai.function.activation.SignFunction;

public class PBaseTest extends CommonTest {

    LinearFunction _lf = new LinearFunction();
    ActivationFunction _af = new SignFunction();

    protected double output(Double[] w, Double[] x,Double delta){
        return _af.f(_lf.f(w,x),delta);
    }
}
