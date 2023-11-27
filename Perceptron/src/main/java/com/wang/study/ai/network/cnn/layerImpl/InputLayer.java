package com.wang.study.ai.network.cnn.layerImpl;

import com.wang.study.ai.network.cnn.Layer;
import com.wang.study.ai.tensor.Tensor;
import com.wang.study.ai.tensor.Tensors;

/**
 * 输入层，接受外部图片数据
 * input(3维): [depth,height,width]
 * 无权重，无Bias
 * Output = input
 */
public class InputLayer extends Layer {
    private final int _width;
    private final int _height;
    private final int _depth;
    private Tensor _input;

    public InputLayer(int d,int h,int w){
        _width = w;
        _height = h;
        _depth = d;
    }


    @Override
    protected void build() {
        int[] shape = null;
        if(_depth > 0 && _height >0 && _width >0){
            shape = new int[]{_depth,_height,_width};
        }else if(_depth <= 0){
            shape = new int[]{_height,_width};
        }
        _output = Tensors.create(shape);
    }

    @Override
    public Tensor run() {
        _output = _input;
        return _output;
    }

    public void assignInputs(Tensor input){
        _input = input;
    }
}
