package com.wang.study.ai.common;

import com.wang.study.ai.data.ptype.DefaultPreType;
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
    public PreType preType = new DefaultPreType();
    public long maxAdjustCount = 10000;

    public int logType = 1;//0-全不写，1-只写失败,9-全写

    public String toString(){
        String str = "";
        if(cf != null){
            str += cf.getClass().getSimpleName()+":";
        }
        return str+ PubUtil.print(neuronNumOfLayers);
    }

    public static void main(String[] args){
        double[][][] d = new double[2][2][2];
        d[0][0][0] = 1;
        d[1][0][0] = 2;
        print(d[0]);
        print(d[1]);
    }

    static void print(double[][] d){
        System.out.println(d[0][0]);
    }
}