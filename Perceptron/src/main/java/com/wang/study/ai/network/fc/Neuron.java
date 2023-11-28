package com.wang.study.ai.network.fc;

import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.function.cost.CostFunction;
import com.wang.study.ai.util.NumUtil;
import com.wang.study.ai.util.PubUtil;

import java.util.ArrayList;
import java.util.List;

public class Neuron {

    //frontList是上一层Neurons,即上层的输出当做此Neuron的输入，正向计算使用
    private List<Neuron> _frontList;

    //反向传播误差需要
    private List<Neuron> _nextList;

    private ActivationFunction _af;

    private CostFunction _cf;

    private double[]  _x;

    double[] _w;
    //w的调整量
    private double[] _dw;

    private double[] _batchDw;
    private int _batch = 0;

    //z函数结果
    private double _y1;

    //a函数结果
    private double _y2;

    private double _expectedValue;

    //dCost/dA * dA/dZ
    private double _e;

    private double _rate;

    private Layer _layer;

    void setActivationFunction(ActivationFunction af){
        _af = af;
    }
    void setCostFunction(CostFunction cf){
        _cf = cf;
    }
    void setExpectedValue(double value){
        this._expectedValue = value;
    }

    public static Neuron build(Layer layer,int xNumber,List<Neuron> frontList){

        Neuron n = new Neuron();
        n._layer = layer;
        n._frontList = frontList;
        //n._x = new double[xNumber+1];
        //n._x[0] = 1d;
        n._w = new double[xNumber+1];
        n._dw = new double[n._w.length];
        n._batchDw = new double[n._w.length];

        for(Neuron front:frontList){
            front.addNextNeuron(n);
        }

        return n;
    }

    void setX(double[] x){
        _x = x;
    }

    void addNextNeuron(Neuron next){
        if(_nextList == null)
            _nextList = new ArrayList<>();

        _nextList.add(next);
    }
    /**
     * 初始化权重
     */
    void initWeight(){
        double r = _w.length;
        r = Math.sqrt(6/r);
        NumUtil.random(_w,-r,r);
    }

    void assignWeight(double d){
        for(int i=0;i<_w.length;i++){
            _w[i] = d;
        }
    }

    void assignWeight(double[] d){
        _w = d;
    }

    public int getXNumber(){
        return _w.length-1;
    }


    double getY(){
        return _y2;
    }

    void setY2(double y){
        _y2 = y;
    }
    void setY1(double y){
        _y1 = y;
    }

    void setRate(double rate){
        this._rate = rate;
    }

    double[] getW(){
        return _w;
    }

    double[] calcDeltaWeight(){
        //cost函数求导
        double dc = 0d;
        //激活函数求导
        double dy1 = _af.df(_y1,_layer.getY1());

        if(_nextList == null){
            //输出层
            dc = _cf.df(_y2,_expectedValue);
        }else{
            for(Neuron n:_nextList){
                double e1 = n._e;
                double e2 = n.getWByFrontNeuron(this);
                dc += e1*e2;
            }
        }

        //基础误差
        _e = dc*dy1;

        for(int i=0;i<_x.length;i++){
            _dw[i] = _e*_x[i]*_rate;
            _batchDw[i] += _dw[i];
        }
        _batch++;
        return _dw;
    }

    private double getWByFrontNeuron(Neuron front){
        for(int i=0;i<_frontList.size();i++){
            if(front.equals(_frontList.get(i))){
                return _w[i+1];
            }
        }
        return 0d;
    }

    void adjustWeight(){
        for(int i=0;i< _w.length;i++){
            _w[i] -= _batchDw[i] / _batch;
        }

        _batchDw = new double[_w.length];
        _batch = 0;
    }

    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("\t\t\tx = ["+ PubUtil.print(_x)).append("]")
                .append(" w=["+PubUtil.print(_w)).append("]:dw=["+PubUtil.print(_dw)).append("]:batchdw =["+PubUtil.print(_batchDw)+"]:batch = "+_batch)
                .append(" y1=").append(PubUtil.print(_y1)).append("/y2=").append(PubUtil.print(_y2)).append("/e=").append(PubUtil.print(_e));
        return buffer.toString();
    }
}
