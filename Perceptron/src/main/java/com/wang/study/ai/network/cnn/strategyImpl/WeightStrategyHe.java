package com.wang.study.ai.network.cnn.strategyImpl;

import com.wang.study.ai.network.cnn.WeightStrategy;

public class WeightStrategyHe extends WeightStrategy {
    @Override
    public double getWeight(long n) {
        double d = Math.sqrt(6/n);
        return d;
    }

    @Override
    public double getBias(long n) {
        return 0;
    }
}
