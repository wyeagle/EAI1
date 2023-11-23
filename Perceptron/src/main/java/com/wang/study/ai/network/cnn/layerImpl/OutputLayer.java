package com.wang.study.ai.network.cnn.layerImpl;

import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.function.cost.CostFunction;
import com.wang.study.ai.tensor.Tensor;

/**
 * 输出层（同FC层，只是反向传播不一样,参见FC描述)
 */
public class OutputLayer extends FCLayer {

    public final CostFunction _cf;

    public OutputLayer(int size, ActivationFunction af, CostFunction cf){
        super(size,af);
        _cf = cf;
    }


    public void setExpectedValue(Tensor expectedValues){

    }

}
