package com.wang.study.ai.data;

import com.wang.study.ai.data.ptype.PreType;
import com.wang.study.ai.data.ptype2.PreType2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainingSet2 {
    private List<TrainingData2> _trainingDatas = new ArrayList<>();

    private int _current = 0;
    private PreType2 _preType;

    public TrainingSet2(PreType2 preType){
        _preType = preType;
    }

    public void addData(TrainingData2 data){
        data.expectedValues = _preType.adaptExpectedValue(data.expectedValues);
        data.x = _preType.adaptParams(data.x);
        _trainingDatas.add(data);
    }

    public int size(){
        return _trainingDatas.size();
    }

    public TrainingData2 get(int index){
        return _trainingDatas.get(index);
    }

    public List<TrainingData2> getTrainingDatas(){
        return _trainingDatas;
    }

    public void reset(){
        _current = 0;
        Collections.shuffle(_trainingDatas);
    }

    public List<TrainingData2> nextBatch(int batch){
        int size = size();
        if(_current >= size){
            return null;
        }
        int end = _current+batch>size ? size:_current+batch;

        List<TrainingData2> datas = new ArrayList<TrainingData2>(size);
        for(int i=_current;i<end;i++){
            datas.add(get(i));
        }
        _current = end;
        return datas;
    }

}
