package com.wang.study.ai.network.perceptron;

import com.wang.study.ai.common.Unit;
import com.wang.study.ai.data.TrainingData;
import com.wang.study.ai.data.TrainingSet;
import com.wang.study.ai.function.LinearFunction;
import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.function.activation.SignFunction;
import com.wang.study.ai.util.NumUtil;
import com.wang.study.ai.util.PubUtil;

import java.util.ArrayList;
import java.util.List;

public class Perceptron extends Unit {
    //input start
    private int _xNumber;

    private ActivationFunction _af;

    private LinearFunction _lf;

    private double _rate;

    private long _maxCount;

    private double _delta;
    //end

    //output
    private double[] _w;

    public Perceptron(int xNumber,double rate, double delta,long maxCount){
        this._xNumber = xNumber;
        this._af = new SignFunction();
        this._rate = rate;
        this._delta = delta;
        this._maxCount = maxCount;
        _lf = new LinearFunction();
    }

    protected void initWeight(){
        _w = new double[_xNumber+1];
        NumUtil.random(_w,-1,1);
    }

    protected void train(TrainingSet trainingSet) throws Exception{
        int count = 0;

        initWeight();

        while(true) {
            count++;

            List<TrainingData> errorDatas = new ArrayList<TrainingData>();
            for (int i = 0; i < trainingSet.size(); i++) {
                TrainingData data = trainingSet.get(i);
                double signResult = run(data.x)[0];
                if (signResult * data.expectedValues[0] < 0) {
                    errorDatas.add(data);
                }
            }
            System.out.println("count = "+count+";error size = "+errorDatas.size()+"; w = "+ PubUtil.print(_w));
            if (errorDatas.size() == 0 || count > _maxCount) {
                break;
            }

            TrainingData randomData = errorDatas.get(PubUtil.randomInt(errorDatas.size()));
            //randomData = errorDatas.get(0);
            adjustWeight(randomData);
        }
    }


    private void adjustWeight(TrainingData errorData) {
        double[] x = NumUtil.insertArray(errorData.x,0,1);
        for(int i=0;i<_w.length;i++){
            _w[i] += x[i]*_rate*errorData.expectedValues[0];
        }
    }

    public double[] run(double[] bx){

        double[] x = NumUtil.insertArray(bx,0,1);
        double[] results = new double[1];
        results[0] = _af.f(_lf.f(_w,x));

        return results;
    }
}
