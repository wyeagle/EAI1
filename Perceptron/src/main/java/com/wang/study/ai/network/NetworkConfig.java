package com.wang.study.ai.network;

import com.wang.study.ai.function.cost.CostFunction;

import java.util.List;

public class NetworkConfig {

    private int xnumber;
    private int[] neuronNumOfLayers;
    private double delta;
    private double rate;
    private int epoch;
    private int batchSize;

    private List<String> activationFuncs;

    private String costFunc;

    private List<double[]> weights;


    public int getXnumber() {
        return xnumber;
    }

    public void setXnumber(int xnumber) {
        this.xnumber = xnumber;
    }

    public int[] getNeuronNumOfLayers() {
        return neuronNumOfLayers;
    }

    public void setNeuronNumOfLayers(int[] neuronNumOfLayers) {
        this.neuronNumOfLayers = neuronNumOfLayers;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getEpoch() {
        return epoch;
    }

    public void setEpoch(int epoch) {
        this.epoch = epoch;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public List<String> getActivationFuncs() {
        return activationFuncs;
    }

    public void setActivationFuncs(List<String> activationFuncs) {
        this.activationFuncs = activationFuncs;
    }

    public String getCostFunc() {
        return costFunc;
    }

    public void setCostFunc(String costFunc) {
        this.costFunc = costFunc;
    }

    public List<double[]> getWeights() {
        return weights;
    }

    public void setWeights(List<double[]> weights) {
        this.weights = weights;
    }



}
