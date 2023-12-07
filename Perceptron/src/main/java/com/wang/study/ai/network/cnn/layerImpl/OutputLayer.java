package com.wang.study.ai.network.cnn.layerImpl;

import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.function.cost.CostFunction;
import com.wang.study.ai.tensor.Tensor;
import com.wang.study.ai.tensor.Tensors;

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

    /**
     * 计算输出层反向传播
     * Tensor(loss求导)*Tensor(激活函数求导)*Tensor(上一层输出结果)*rate(学习率)
     */
    protected void calcLayerDiff(){
        //[_size,1]
        Tensor ad = (Tensor)_output.clone();
        ad.func(_af,false);
        _layerDiff = (Tensor)ad.clone();
        _layerDiff.func(_cf,false);

        //[_size,1]
        _layerDiff.multiply(ad);

    }
}
