package com.wang.study.ai.tensor;

public class TensorTest {
    protected Tensor create(double min, double max,int... shape){
        Tensor t = Tensors.create(shape,min,max);
        return t;
    }

}
