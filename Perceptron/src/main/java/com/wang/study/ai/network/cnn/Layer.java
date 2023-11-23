package com.wang.study.ai.network.cnn;

import com.wang.study.ai.network.Matrix;
import com.wang.study.ai.tensor.Location;
import com.wang.study.ai.tensor.Tensor;

import java.util.List;
import java.util.Map;

public abstract class Layer {
    protected Tensor _input;
    public Tensor _output;
    protected Tensor _weight;
    protected Tensor _bias;
    protected Layer _frontLayer;
    protected WeightStrategy _weightStrategy;
    protected List<Neuron> _neurons;
    protected Map<Location,Neuron> _neuronMap;


    protected void build(Layer frontLayer,WeightStrategy weightStrategy){
        _frontLayer = frontLayer;
        _weightStrategy = weightStrategy;
        build();
    }

    protected abstract void build();

    public abstract Tensor run();

    protected void calcWeight(){

    }

    protected void adjustWeight(){

    }
}
