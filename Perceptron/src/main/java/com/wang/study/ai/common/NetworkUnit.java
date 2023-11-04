package com.wang.study.ai.common;

import com.wang.study.ai.data.TrainingSet;
import com.wang.study.ai.network.result.NetworkResult;

public abstract class NetworkUnit {

    protected TrainStatus _status = TrainStatus.TRAIN_WAITING;

    public NetworkResult train(TrainingSet trainingSet, boolean endFlag){
        _status = TrainStatus.TRAIN_RUNNING;
        NetworkResult result = null;
        try {
            result = train(trainingSet);

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
        return result;
    }

    public TrainStatus getStatus(){
        return _status;
    }

    protected abstract NetworkResult train(TrainingSet trainingSet) throws Exception;

    //public abstract void adjustWeight();

    protected abstract void initWeight();

    public abstract double[] run(double[] x) throws Exception;
}
