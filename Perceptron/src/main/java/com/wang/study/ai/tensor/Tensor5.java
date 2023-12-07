package com.wang.study.ai.tensor;

public class Tensor5 extends CompositeTensor {
    @Override
    protected Tensor newSubTensor() {
        return new Tensor4();
    }

    @Override
    protected Tensor[] newSubTensors(int length) {
        return new Tensor4[length];
    }
}
