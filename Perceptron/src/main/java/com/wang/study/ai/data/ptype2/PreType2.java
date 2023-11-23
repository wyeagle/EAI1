package com.wang.study.ai.data.ptype2;

import com.wang.study.ai.data.TrainingData;
import com.wang.study.ai.data.TrainingData2;
import com.wang.study.ai.tensor.Tensor;

import java.util.List;

public abstract class PreType2 {
    private List<TrainingData2> _datas;

    public void load(List<TrainingData2> datas){
        _datas = datas;
    }

    public abstract void pre();

    public abstract Tensor adaptExpectedValue(Tensor eds);

    public abstract Tensor adaptParams(Tensor params);
}
