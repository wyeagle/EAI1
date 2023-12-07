package com.wang.study.ai.network.cnn;

import com.wang.study.ai.network.Matrix;
import com.wang.study.ai.tensor.Location;
import com.wang.study.ai.tensor.Tensor;

import java.util.List;
import java.util.Map;

public abstract class Layer {
    protected Tensor _input;
    public Tensor _output;
    public Tensor _weight;
    protected Tensor _deltaWeight;
    protected Tensor _deltaBias;
    protected Tensor _bias;

    //损失函数的导数*激活函数导数
    public Tensor _layerDiff;

    protected Layer _frontLayer;
    protected Layer _nextLayer;
    protected WeightStrategy _weightStrategy;
    protected List<Neuron> _neurons;
    protected Map<Location,Neuron> _neuronMap;


    protected void build(Layer frontLayer,Layer nextLayer,WeightStrategy weightStrategy){
        _frontLayer = frontLayer;
        _nextLayer = nextLayer;
        _weightStrategy = weightStrategy;
        build();
    }

    protected abstract void build();

    public abstract Tensor run();

    protected void calcWeight(double rate){
        _layerDiff = _nextLayer._layerDiff;
    }

    protected void adjustWeight(int batch){
        _deltaWeight.divide(batch);
        _bias = _deltaWeight.sliceWidth(_deltaWeight.width()-2,1);
        _deltaWeight = _deltaWeight.sliceWidth(0,_deltaWeight.width()-2);
        _weight.subtract(_deltaWeight);
    }
}
