package com.wang.study.ai.data.ptype;

import com.wang.study.ai.data.TrainingData;

import java.util.List;

public abstract class PreType {
    private List<TrainingData> _datas;

    public void load(List<TrainingData> datas){
        _datas = datas;
    }

    public abstract void pre();
}
