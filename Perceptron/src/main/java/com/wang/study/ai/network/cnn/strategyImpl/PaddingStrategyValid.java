package com.wang.study.ai.network.cnn.strategyImpl;

import com.wang.study.ai.network.cnn.PaddingStrategy;

public class PaddingStrategyValid extends PaddingStrategy {
    @Override
    public int calcPadding(int[] input, int[] kernel, int stride) {
        return 0;
    }
}
