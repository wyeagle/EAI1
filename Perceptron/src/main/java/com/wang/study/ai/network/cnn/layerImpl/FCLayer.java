package com.wang.study.ai.network.cnn.layerImpl;

import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.network.cnn.Layer;
import com.wang.study.ai.tensor.Tensor;
import com.wang.study.ai.tensor.Tensors;

/**
 * 全连接层
 * input(2维): [H,1],将上层输出按高度打平，H-上层输出的元素的总数量，比如上层是3维张量(3,2,3),那么总数量即是3*2*3=18
 * 权重(2维): [_size,H]
 * Bias(2维): [_size,1]
 * Output(2维):[_size,1](公式=权重*input+bias)
 */
public class FCLayer extends Layer {

    protected final int _size;

    protected final ActivationFunction _af;

    public FCLayer(int size,ActivationFunction af){
        _size = size;
        _af = af;
    }

    @Override
    protected void build() {
        //不需要初始化或创建Neuron, 可以通过张量计算得到输出值
        _output = Tensors.create(new int[]{_size,1});

        int number = _frontLayer._output.numValues();
        double value = _weightStrategy.getWeight(number);
        _weight = Tensors.create(new int[]{_size,number},-value,value);

        value = _weightStrategy.getBias(number);
        _bias = Tensors.create(new int[]{_size,1},-value,value);
    }

    @Override
    public Tensor run() {
        _input = _frontLayer._output.flat2D();
        //[_size*n]*[n*1]=[_size*1]
        _output = _weight.product(_input);
        _output.add(_bias);
        _output.func(_af);
        return _output;
    }


}
