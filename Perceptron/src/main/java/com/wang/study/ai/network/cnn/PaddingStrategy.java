package com.wang.study.ai.network.cnn;

import com.wang.study.ai.network.cnn.enums.PaddingEnum;
import com.wang.study.ai.network.cnn.strategyImpl.PaddingStrategyValid;

public abstract class PaddingStrategy {
    public static PaddingStrategy newInstance(PaddingEnum pe) {
        PaddingStrategy ps = null;
        switch(pe){
            case VALID:ps = new PaddingStrategyValid();
        }
        return ps;
    }
    public abstract int calcPadding(int[] input, int[] kernel, int stride);
}
