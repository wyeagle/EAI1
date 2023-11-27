package com.wang.study.ai.network.cnn;

import com.wang.study.ai.network.cnn.enums.WeightEnum;
import com.wang.study.ai.network.cnn.strategyImpl.WeightStrategyHe;

public abstract class WeightStrategy {
    public static WeightStrategy newInstance(WeightEnum we) {
        WeightStrategy ws = null;
        switch(we){
            case HE:ws = new WeightStrategyHe();break;
        }
        return ws;
    }

    public abstract double getWeight(long n);
    public abstract double getBias(long n);
}
