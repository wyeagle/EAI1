package com.wang.study.ai.network.cnn.layerImpl;

import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.network.cnn.Layer;
import com.wang.study.ai.network.cnn.Neuron;
import com.wang.study.ai.network.cnn.PaddingStrategy;
import com.wang.study.ai.tensor.Location;
import com.wang.study.ai.tensor.Tensor;
import com.wang.study.ai.tensor.Tensors;
import com.wang.study.ai.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 卷积层
 * input(3维): depth*height*width
 * 权重(4维):[_kernelSize,_filterDepth,_filterHeight,_filterWidth]
 * Bias(1维):[_kernelSize]
 * Output(3维):[_kernelSize*卷积后的高度*卷积后的宽度]
 */
public class ConvolutionLayer extends Layer {

    private final int _filterWidth;
    private final int _filterHeight;
    private final int _filterDepth;
    private final int _stride;
    private final PaddingStrategy _paddingStrategy;
    private final int _kernelSize;
    private final ActivationFunction _af;

    public ConvolutionLayer(int w, int h, int d, int stride, PaddingStrategy paddingStrategy,
                               int kernelSize, ActivationFunction af){
        _filterWidth = w;
        _filterHeight = h;
        _filterDepth = d;
        _stride = stride;
        _paddingStrategy = paddingStrategy;
        _kernelSize = kernelSize;
        _af = af;
    }

    @Override
    protected void build() {
        initOutput();
        initWeight();
        initNeuron();
    }

    @Override
    /**
     * 返回的是3维张量_kernelSize*卷积后的高度*卷积后的宽度
     */
    public Tensor run() {
        //对输入值做填充处理
        padding();

        //准备所有神经元的数据，入参,权重,bias.
        prepareData();

        //并行计算所有神经元数据，并将结果放入output
        calcOutput();

        return _output;
    }

    private void calcOutput(){
        _neurons.parallelStream().forEach(neuron-> {
            _output.setValue(neuron.location(), neuron.run(_af));
        });
    }

    private void prepareData(){
        Map<String,Tensor> subInputMap = new HashMap<>();

        for(int i=0;i<_kernelSize;i++){
            //返回一个三维权重张量
            Tensor subWeight = _weight.sliceSubD(i);

            for(int j=0;j<_output.height();j++){
                int hIndex = j*_stride;
                for(int k=0;k<_output.width();k++){
                    int wIndex = k*_stride;
                    String key = StringUtil.int2Str(hIndex, wIndex, hIndex + _filterHeight, wIndex + _filterWidth);
                    Tensor subInput = subInputMap.get(key);
                    if(subInput == null) {
                        subInput = _input.slice2D(hIndex, wIndex, hIndex + _filterHeight, wIndex + _filterWidth);
                        subInputMap.put(key,subInput);
                    }
                    Location loc = new Location(new int[]{i,j,k});
                    Neuron neuron = _neuronMap.get(loc);
                    neuron.assignMatrix(subInput,subWeight,_bias.sliceSubD(i));
                }
            }
        }
    }

    private void initWeight(){
        long number = _kernelSize*(_filterDepth*_filterHeight*_filterWidth+1);
        double value = _weightStrategy.getWeight(number);
        _weight = Tensors.create(new int[]{_kernelSize,_filterDepth,_filterHeight,_filterWidth},-value,value);

        value = _weightStrategy.getBias(number);
        _bias = Tensors.create(new int[]{_kernelSize},-value,value);
    }

    private void initOutput(){
        int paddingNum = calcPadding();
        int[] size = calcOutputSize(paddingNum);
        _output = Tensors.create(new int[]{_kernelSize,size[0],size[1]});
    }

    private void initNeuron(){
        _neurons = new ArrayList<>();
        _neuronMap = new HashMap<>();

        Location loc = null;
        Neuron neuron = null;
        for(int i=0;i<_kernelSize;i++){
            for(int j=0;j<_output.height();j++){
                for(int k=0;k<_output.width();k++){
                    loc = new Location(new int[]{i,j,k});
                    neuron = new Neuron(loc);
                    _neurons.add(neuron);
                    _neuronMap.put(loc,neuron);
                }
            }
        }
    }

    private void padding(){
        _input = _frontLayer._output;
        int padNum = calcPadding();
        _input.padding(padNum,0);
    }

    private int calcPadding(){
        int padNum = _paddingStrategy.calcPadding(new int[]{
                _frontLayer._output.height(), _frontLayer._output.width()},
                new int[]{_filterHeight,_filterWidth},_stride);
        if(padNum <= 0){
            return 0;
        }
        return padNum;
    }

    /**
     * 因为第一步已经处理Padding,即input已包含padding数，所以这里计算时Padding为0.
     * @return [0]-height,[1]-width
     */
    private int[] calcOutputSize(int paddingNum){
        int[] size = new int[2];
        size[0] = (_frontLayer._output.height()-_filterHeight+2*paddingNum)/_stride+1;
        size[1] = (_frontLayer._output.width()-_filterWidth+2*paddingNum)/_stride+1;

        return size;
    }
}
