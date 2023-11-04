package com.wang.study.ai.data;

import com.wang.study.ai.data.ptype.PreType;
import com.wang.study.ai.util.NumUtil;

import java.util.ArrayList;
import java.util.List;

public class TrainingSet {
    private List<TrainingData> _trainingDatas;

    private int _current = 0;

    public TrainingSet(List<TrainingData> datas){
        _trainingDatas = datas;
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


    /**
     * 对数据进行预处理
     */
    public void preprocessing(PreType preType){
        preType.load(_trainingDatas);
        preType.pre();
    }

}
