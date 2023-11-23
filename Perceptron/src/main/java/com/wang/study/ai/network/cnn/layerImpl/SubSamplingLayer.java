package com.wang.study.ai.network.cnn.layerImpl;

import com.wang.study.ai.network.cnn.Layer;
import com.wang.study.ai.network.cnn.Neuron;
import com.wang.study.ai.tensor.Location;
import com.wang.study.ai.tensor.Tensor;
import com.wang.study.ai.tensor.Tensors;
import com.wang.study.ai.util.StringUtil;

/**
 * 下采集层（池化），上层只能接卷积层
 * input(3维): [depth,height,width]
 * 无权重，无Bias
 * Output(3维):[depth,采集后height,采集后width]
 */
public class SubSamplingLayer extends Layer {

    private final int _width;
    private final int _height;
    private final int _stride;

    public SubSamplingLayer(int w, int h, int stride){
        _width = w;
        _height = h;
        _stride = stride;
    }

    @Override
    protected void build() {
        int[] size = calcOutputSize();
        _output = Tensors.create(new int[]{_frontLayer._output.depth(),size[0],size[1]});
    }

    @Override
    public Tensor run() {
        _input = _frontLayer._output;
        for(int i=0;i<_output.depth();i++){
            for(int j=0;j<_output.height();j++){
                int hIndex = j*_stride;
                for(int k=0;k<_output.width();k++){
                    int wIndex = k*_stride;
                    //按maxPool处理池化
                    //将3维输入降为2维，然后截取指定范围，最后对此范围求最大值
                    double max = _input.sliceSubD(i)
                            .slice2D(hIndex, wIndex, hIndex + _height, wIndex + _width)
                            .max();
                    Location loc = new Location(new int[]{i,j,k});
                    _output.setValue(loc,max);
                }
            }
        }
        return _output;
    }

    private int[] calcOutputSize(){
        int[] size = new int[2];
        size[0] = (_frontLayer._output.height()-_height)/_stride+1;
        size[1] = (_frontLayer._output.width()-_width)/_stride+1;

        return size;
    }
}
