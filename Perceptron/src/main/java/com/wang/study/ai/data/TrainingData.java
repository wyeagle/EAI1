package com.wang.study.ai.data;

import com.wang.study.ai.util.PubUtil;

public class TrainingData {
    public double[] x;
    public double[] expectedValues;

    public String toString(){
        StringBuffer buffer = new StringBuffer();

        buffer.append(PubUtil.print(x)).append(":").append(PubUtil.print(expectedValues));

        return buffer.toString();
    }
}
