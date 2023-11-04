package com.wang.study.ai.common;

import com.wang.study.ai.data.ptype.PreType;
import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.function.cost.CostFunction;
import com.wang.study.ai.util.PubUtil;

import java.util.List;

public class TestParam {
    public CostFunction cf;
    public List<ActivationFunction> afs;
    public int xNumber;
    public int[] neuronNumOfLayers;
    public double delta;
    public double rate;
    public int epoch;
    public int batchSize;
    public String trainingFile;
    public String testFile;
    public double compareDelta;
    public PreType preType;

    public String toString(){
        return cf.getClass().getSimpleName()+":"+ PubUtil.print(neuronNumOfLayers);
    }
}