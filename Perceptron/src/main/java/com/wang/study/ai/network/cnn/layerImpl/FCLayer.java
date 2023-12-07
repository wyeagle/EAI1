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
        _output = _weight.matmul(_input);
        _output.add(_bias);
        _output.func(_af,true);
        return _output;
    }

    /**
     * 当前层数是m, 前一层是n, 下一层是k (（【W(l+1)]T * layDiff） 点积 Al')*[A(l-1)]T
     * 1. 当前层的layDiff = (l+1层权重W的转置 * l+1层的layDiff) 点积 (当前层激活函数导数) ([m,k]*[k,1]) 点积 [m,1] = [m,1]
     * 2. 当前层的layDiff * 上一层输出矩阵的转置 [m,1]*[1,n] = [m,n]
     * @param rate
     */
    protected void calcWeight(double rate){
        calcLayerDiff();

        //[_size,1]*[1,front.output.size] = [_size,front.output.size]
        Tensor tmpWeight = _layerDiff.matmul(_frontLayer._output.transpose());
        if(_deltaWeight == null){
            _deltaWeight = tmpWeight;
        }else{
            _deltaWeight.add(tmpWeight);
        }
        _deltaWeight.multiply(rate);

        //计算bias，因为bias=w0*x0,x0=1，w0当bias,所以bias调整就是layerDiff,
        // [_size,1]
        Tensor tmpBias = (Tensor)_layerDiff.clone();
        if(_deltaBias == null){
            _deltaBias = tmpBias;
        }else{
            _deltaBias.add(tmpBias);
        }
        _deltaBias.multiply(rate);
    }

    protected void calcLayerDiff(){
        _layerDiff = _nextLayer._weight.transpose().matmul(_nextLayer._layerDiff);
        Tensor ad = (Tensor)_output.clone();
        ad.func(_af,false);
        _layerDiff.multiply(ad);
    }

}
