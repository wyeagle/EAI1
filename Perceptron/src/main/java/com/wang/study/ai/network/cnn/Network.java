package com.wang.study.ai.network.cnn;

import com.wang.study.ai.data.TrainingData2;
import com.wang.study.ai.data.TrainingSet2;
import com.wang.study.ai.network.cnn.layerImpl.InputLayer;
import com.wang.study.ai.network.cnn.layerImpl.OutputLayer;
import com.wang.study.ai.network.WorkMemory;
import com.wang.study.ai.network.result.EpochResult;
import com.wang.study.ai.network.result.NetworkResult;
import com.wang.study.ai.tensor.Tensor;
import com.wang.study.ai.util.FileUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Network {
    List<Layer> _layerList;
    TrainingParam _trainingParam;

    private InputLayer _inputLayer;
    private OutputLayer _outputLayer;
    private WeightStrategy _weightStrategy;

    WorkMemory _memory;

    protected Network(List<Layer> layerList,WeightStrategy ws){
        _inputLayer = (InputLayer)layerList.get(0);
        _outputLayer = (OutputLayer)layerList.get(layerList.size()-1);
        _weightStrategy = ws;
        build();
    }

    protected void build(){
        Layer frontLayer = null;
        for(Layer layer:_layerList){
            layer.build(frontLayer,_weightStrategy);
            frontLayer = layer;
        }
        _memory = new WorkMemory();
    }

    public Tensor run(Tensor inputs){

        _inputLayer.assignInputs(inputs);

        Tensor result = null;
        for(Layer layer:_layerList){
            result = layer.run();
        }

        return result;
    }

    public NetworkResult train(TrainingSet2 trainingSet, TrainingParam trainingParam) throws Exception {
        _trainingParam = trainingParam;
        NetworkResult nResult = new NetworkResult();
        initWeight();

        for(int i=0;i<_trainingParam._epoch;i++) {
            int batchNum = 0;
            long startTime = System.currentTimeMillis();
            trainingSet.reset();

            while (true) {
                batchNum++;
                _memory.clear();
                List<TrainingData2> miniDatas = trainingSet.nextBatch(_trainingParam._batchSize);
                if (miniDatas == null) {
                    break;
                }
                batchTrain(batchNum, miniDatas);
                FileUtil.str2File(_trainingParam._backupFile+"_"+batchNum+".nw", "NetworkUtil.network2Json(this)");
            }
            EpochResult eResult = new EpochResult(i);
            //eResult.addResult(_memory._adjustCount,_memory._lastDiff,(System.currentTimeMillis()-startTime),this);
            nResult.add(eResult);
            System.out.println("+++++++++++++++++++++eporch "+i+" end!");
        }
        return nResult;
    }

    private void batchTrain(int batchNo,List<TrainingData2> miniDatas) throws Exception{
        int count = 0;

        while(true){
            count++;
            Collections.shuffle(miniDatas);
            boolean isOver = singleTrain(batchNo,count,miniDatas);
            if(isOver){
                break;
            }
        }
    }

    private boolean singleTrain(int batchNo, int count, List<TrainingData2> miniDatas) throws Exception{
        //误差
        double avgDiff = 0d;
        //是否完成，需要检查每个调整权重小于delta.
        boolean isOver = false;

        int errorCount = 0;

        for (int i = 0; i < miniDatas.size(); i++) {

            TrainingData2 data = miniDatas.get(i);

            Tensor resultT = run(data.x);

            double[] actualOutputs = resultT.flat();

            //仅对输出层设置预期值
            _outputLayer.setExpectedValue(data.expectedValues);

            double currDiff = _outputLayer._cf.fByArray(actualOutputs,data.expectedValues.flat());

            avgDiff += currDiff;
            if(currDiff < _trainingParam._delta){
                continue;
            }
            calcWeight();

            errorCount++;
        }

        if(errorCount == 0){
            System.out.println("errorCount = 0 ");
            isOver = true;
        }

        avgDiff = avgDiff/miniDatas.size();

        _memory.addDiff(avgDiff,count);
        boolean canAbort = _memory.canAbort();
        if(canAbort || avgDiff < _trainingParam._delta){
            if(avgDiff < _trainingParam._delta){
                System.out.println("diff < _delta = "+avgDiff);
            }
            isOver = true;
        }

        if(isOver || (count % 10 == 1 && _trainingParam._batchSize >= 10000) || (count % 1000 == 1 && _trainingParam._batchSize < 10000)) {

            System.out.println(new Date()+":Current batch[" + batchNo + "][" + count + "] " +
                    "avgerage error = " + avgDiff + ":isOver = " + isOver+": abort = "+canAbort+": current errorcount = "+errorCount);
        }
        if(count % 100 == 0 && _trainingParam._batchSize >= miniDatas.size() && _trainingParam._batchSize >= 10000){
            //FileUtil.str2File(_trainingParam._backupFile+"_"+count+".nw",NetworkUtil.network2Json(this));
        }

        if(!isOver)
            adjustWeight();

        return isOver;
    }

    void calcWeight(){
        for(int i=_layerList.size()-1;i>=0;i--){
            Layer layer = _layerList.get(i);
            layer.calcWeight();
        }
    }

    void adjustWeight(){
        _memory.addAdjustCount();

        for(int i=_layerList.size()-1;i>=0;i--){
            Layer layer = _layerList.get(i);
            layer.adjustWeight();
        }
       // LogAction.logFirstAdjustW(this);
    }


    public void assignWeight(Tensor weights){

    }


    protected void initWeight() {

    }

}
