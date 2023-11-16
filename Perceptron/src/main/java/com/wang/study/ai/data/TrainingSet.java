package com.wang.study.ai.data;

import com.wang.study.ai.data.ptype.PreType;
import com.wang.study.ai.util.NumUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainingSet {
    private List<TrainingData> _trainingDatas = new ArrayList<>();

    private int _current = 0;
    private PreType _preType;

    public TrainingSet(PreType preType){
        _preType = preType;
    }

    public void addData(TrainingData data){
        data.expectedValues = _preType.adaptExpectedValue(data.expectedValues);
        data.x = _preType.adaptParams(data.x);
        _trainingDatas.add(data);
    }

    public int size(){
        return _trainingDatas.size();
    }

    public TrainingData get(int index){
        return _trainingDatas.get(index);
    }

    public List<TrainingData> getTrainingDatas(){
        return _trainingDatas;
    }

    /*public void addBias(){
        for(TrainingData training : _trainingDatas){
            training.x = NumUtil.insertArray(training.x,0,1);
        }
    }*/

    public void reset(){
        _current = 0;
        Collections.shuffle(_trainingDatas);
    }

    private void random(){

    }

    public List<TrainingData> nextBatch(int batch){
        int size = size();
        if(_current >= size){
            return null;
        }
        int end = _current+batch>size ? size:_current+batch;

        List<TrainingData> datas = new ArrayList<TrainingData>(size);
        for(int i=_current;i<end;i++){
            datas.add(get(i));
        }
        _current = end;

        return datas;
    }

}
