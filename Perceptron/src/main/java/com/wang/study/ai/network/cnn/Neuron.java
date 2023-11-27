package com.wang.study.ai.network.cnn;

import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.tensor.Location;
import com.wang.study.ai.tensor.Tensor;

public class Neuron {
    Location _loc;
    Tensor _input;
    Tensor _weight;
    Tensor _bias;

    public Neuron(Location loc){
        _loc = loc;
    }

    public Location location() {
        return _loc;
    }


    public void assignMatrix(Tensor input,Tensor weight, Tensor bias){
        _input = input;
        _weight = weight;
        _bias = bias;
    }

    public double run(ActivationFunction af){
        //0维张量
        Tensor output = _input.conv(_weight);
        output.add(_bias);

        double f = af.f(output.getValue());

        return f;
    }
}
