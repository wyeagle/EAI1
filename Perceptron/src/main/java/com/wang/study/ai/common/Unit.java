package com.wang.study.ai.common;

import com.wang.study.ai.data.TrainingSet;

public abstract class Unit {

    protected TrainStatus _status = TrainStatus.TRAIN_WAITING;

    public void train(TrainingSet trainingSet,boolean endFlag){
        _status = TrainStatus.TRAIN_RUNNING;

        try {
            train(trainingSet);

            if(endFlag){
                _status = TrainStatus.TRAIN_SUCCESS;
            }else{
                _status = TrainStatus.TRAIN_BATCH_SUCCESS;
            }

        }catch (Exception e){
            e.printStackTrace();
            if(endFlag){
                _status = TrainStatus.TRAIN_FAILED;
            }else{
                _status = TrainStatus.TRAIN_BATCH_FAILED;
            }
        }
    }

    public TrainStatus getStatus(){
        return _status;
    }

    protected abstract void train(TrainingSet trainingSet) throws Exception;

    //public abstract void adjustWeight();

    protected abstract void initWeight();

    public abstract double[] run(double[] x) throws Exception;
}
