package com.wang.study.ai.network;

import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.function.cost.CostFunction;
import com.wang.study.ai.util.Matrix;
import com.wang.study.ai.util.MatrixUtil;
import com.wang.study.ai.util.NumUtil;

import java.util.ArrayList;
import java.util.List;

public class Layer {
    private List<Neuron> _neuronList = null;

    ActivationFunction _af;

    private CostFunction _cf;

    private double[] _x;

    private Layer _frontLayer;

    private boolean _lastFlag = false;

    private double _delta;

    public static Layer build(int xNumberOfNet, int size, Layer frontLayer){

        List<Neuron> neuronList = new ArrayList<Neuron>(size);

        int xNumber = xNumberOfNet;
        if(frontLayer != null){
            xNumber = frontLayer.getNeuronList().size();
        }
        Layer layer = new Layer(neuronList);
        for(int i=0;i<size;i++){
            Neuron n = Neuron.build(xNumber,frontLayer == null ? new ArrayList<Neuron>():frontLayer.getNeuronList());
            layer.addNeuron(n);
        }

        layer._frontLayer = frontLayer;
        return layer;
    }

    private Layer(List<Neuron> neuronList){
        _neuronList = neuronList;
    }

    void setActivationFunction(ActivationFunction af){
        _af = af;

        for(Neuron neuron:_neuronList){
            neuron.setActivationFunction(_af);
        }
    }

    void setRate(double rate){
        for(Neuron neuron:_neuronList){
            neuron.setRate(rate);
        }
    }

    void setDelta(double delta){_delta = delta;}

    void setExpectedValue(double[] values){
        for(int i=0;i<_neuronList.size();i++){
            Neuron neuron = _neuronList.get(i);
            neuron.setExpectedValue(values[i]);
        }
    }

    void setCostFunction(CostFunction cf){
        _cf = cf;

        for(Neuron neuron:_neuronList){
            neuron.setCostFunction(_cf);
        }
    }

    void setX(){
        for(Neuron neuron:_neuronList){
            neuron.setX(_x);
        }
    }

    void initWeight(){
        for(Neuron n:_neuronList){
            n.initWeight();
        }
    }

    void addNeuron(Neuron n){
        _neuronList.add(n);
    }

    List<Neuron> getNeuronList(){
        return _neuronList;
    }

    /**
     * 输入层
     * @param x
     * @return
     */
    double[] run(double[] x) throws Exception{
        if(_frontLayer == null){
            _x = new double[x.length+1];
            _x[0] = 1d;
            for(int i=0;i<x.length;i++){
                _x[i+1] = x[i];
            }
        }else{
            _x = new double[_frontLayer.getNeuronList().size()+1];
            _x[0] = 1d;

            for(int i=0;i<_frontLayer.getNeuronList().size();i++){
                Neuron front = _frontLayer.getNeuronList().get(i);
                _x[i+1] = front.getY();
            }
        }

        setX();

        double[] result = runMatrix();
        return result;
    }

    private double[] runMatrix() throws Exception{
        double[][] ws = new double[getNeuronList().size()][_x.length];
        Matrix wM = Matrix.build(ws);
        for(int i=0;i<getNeuronList().size();i++){
            Neuron neuron = getNeuronList().get(i);
            wM.fillRowData(i,neuron.getW());
        }

        double[][] xs = new double[_x.length][1];
        Matrix xM = Matrix.build(xs);
        xM.fillColData(0,_x);

        Matrix resultM = MatrixUtil.multiply2(wM,xM);
        double[] resultY1 = resultM.getColData(0);
        MatrixUtil.func(resultM,_af);
        double[] resultY2 = resultM.getColData(0);
        for(int i=0;i<_neuronList.size();i++){
            Neuron n = _neuronList.get(i);
            n.setY1(resultY1[i]);
            n.setY2(resultY2[i]);
        }
        return resultY2;
    }

    boolean calcWeight(){
        boolean isOver = true;

        for(Neuron neuron:_neuronList){
            double[] dw = neuron.calcDeltaWeight();
            int count = NumUtil.statLargeDelta(dw,_delta);
            if(count > 0){
                isOver = false;
            }
        }

        return isOver;
    }

    void adjustWeight(){
        for(Neuron n:_neuronList){
            n.adjustWeight();
        }
    }

    boolean isFirst(){
        if(_frontLayer == null){
            return true;
        }
        return false;
    }

    void setLastFlag(boolean isLast){
        _lastFlag = isLast;
    }

    public String toString(){
        StringBuffer buffer = new StringBuffer();
        int index = 0;
        for(Neuron n:_neuronList){
            buffer.append("\t\tNeuron["+index+"] = \n").append(n).append("\n");
            index++;
        }
        return buffer.toString();
    }

    void assignWeight(double d){
        for(Neuron n:_neuronList){
            n.assignWeight(d);
        }
    }
}
