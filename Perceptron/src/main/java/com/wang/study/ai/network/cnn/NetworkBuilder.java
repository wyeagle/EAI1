package com.wang.study.ai.network.cnn;

import com.wang.study.ai.common.EAIException;
import com.wang.study.ai.function.activation.ActivationEnum;
import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.function.cost.CostEnum;
import com.wang.study.ai.function.cost.CostFunction;
import com.wang.study.ai.network.cnn.enums.PaddingEnum;
import com.wang.study.ai.network.cnn.enums.WeightEnum;
import com.wang.study.ai.network.cnn.layerImpl.*;
import com.wang.study.ai.util.PubUtil;

import java.util.ArrayList;
import java.util.List;

public class NetworkBuilder {
    private NetworkBuilder _builder = new NetworkBuilder();

    List<Layer> _layerList = new ArrayList<>();
    WeightEnum _weightEnum = WeightEnum.HE;

    public NetworkBuilder build(WeightEnum we){
        _weightEnum = we;
        return _builder;
    }

    /**
     * 构建输入层参数
     * @param w 宽度
     * @param h 高度
     * @param d 深度
     * @return
     */
    public NetworkBuilder buildInputLayer(int w, int h, int d){
        InputLayer layer = new InputLayer(w,h,d);
        _layerList.add(layer);
        return _builder;
    }

    /**
     * 构建卷积层参数
     * @param w 宽度
     * @param h 高度
     * @param d 通道数（必须和前一层输出通道数相同)
     * @param stride 步长
     * @param type 填充方式
     * @param kernelSize 卷积核数量
     * @param ae 激活函数
     * @return
     */
    public NetworkBuilder buildConvolutionLayer(int w, int h, int d, int stride, PaddingEnum type,
                                                int kernelSize, ActivationEnum ae){
        ConvolutionLayer layer = new ConvolutionLayer(w,h,d,stride,PaddingStrategy.newInstance(type),kernelSize,ActivationFunction.newInstance(ae));
        _layerList.add(layer);
        return _builder;
    }

    /**
     * 构建下采样(池化层)参数
     * @param w 宽度
     * @param h 高度
     * @param stride 步长
     * @return
     */
    public NetworkBuilder buildSubSamplingLayer(int w, int h, int stride){
        SubSamplingLayer layer = new SubSamplingLayer(w,h,stride);
        _layerList.add(layer);
        return _builder;
    }

    /**
     * 构建全连接层参数
     * @param size 神经元数量
     * @param ae 激活函数
     * @return
     */
    public NetworkBuilder buildFCLayer(int size,ActivationEnum ae){
        FCLayer layer = new FCLayer(size,ActivationFunction.newInstance(ae));
        _layerList.add(layer);
        return _builder;
    }

    /**
     * 构建输出层
     * @param size 输出神经元数量
     * @param ae 激活函数
     * @param ce 损失函数
     * @return
     */
    public NetworkBuilder buildOutputLayer(int size, ActivationEnum ae, CostEnum ce){
        OutputLayer layer = new OutputLayer(size,ActivationFunction.newInstance(ae),CostFunction.newInstance(ce));
        _layerList.add(layer);
        return _builder;
    }

    public Network createNetwork(){
        if(PubUtil.isEmpty(_layerList)){
            throw new EAIException("LayerList must not be null!");
        }
        if(_layerList.size() <= 2){
            throw new EAIException("the number of layer must large than 2!");
        }
        Layer layer = _layerList.get(0);
        if(!(layer instanceof InputLayer)){
            throw new EAIException("First layer must be InputLayer!");
        }
        layer = _layerList.get(_layerList.size()-1);
        if(!(layer instanceof OutputLayer)){
            throw new EAIException("Last layer must be OutputLayer!");
        }
        for(int i=1;i<_layerList.size()-1;i++){
            layer = _layerList.get(i);
            if((layer instanceof InputLayer) || (layer instanceof OutputLayer)){
                throw new EAIException("Middle layer must not be InputLayer or OutputLayer!");
            }
        }

        Network network = new Network(_layerList,WeightStrategy.newInstance(_weightEnum));

        return network;
    }
}
