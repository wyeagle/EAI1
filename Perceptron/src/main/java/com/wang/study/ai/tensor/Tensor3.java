package com.wang.study.ai.tensor;

public class Tensor3 extends CompositeTensor {

    @Override
    protected Tensor newSubTensor() {
        return new Tensor2();
    }

    @Override
    protected Tensor[] newSubTensors(int length) {
        return new Tensor2[length];
    }
}
