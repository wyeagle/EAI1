package com.wang.study.ai.tensor;

public class Tensor4 extends CompositeTensor {

    @Override
    protected Tensor newSubTensor() {
        return new Tensor3();
    }

    @Override
    protected Tensor[] newSubTensors(int length) {
        return new Tensor3[length];
    }
}
