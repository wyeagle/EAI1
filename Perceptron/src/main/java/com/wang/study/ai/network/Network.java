package com.wang.study.ai.network;

import com.wang.study.ai.common.NetworkUnit;
import com.wang.study.ai.common.Unit;
import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.function.cost.CostFunction;
import com.wang.study.ai.data.TrainingData;
import com.wang.study.ai.data.TrainingSet;
import com.wang.study.ai.network.result.EpochResult;
import com.wang.study.ai.network.result.NetworkResult;
import com.wang.study.ai.util.LocalUtil;
import com.wang.study.ai.util.NumUtil;
import com.wang.study.ai.util.PubUtil;

import java.util.ArrayList;
import java.util.List;

public class Network extends NetworkUnit {

    private static double DEFAULT_DELTA = 0.01d;
    private static double DEFAULT_RATE = 0.1d;
    List<Layer> _layerList;

    double _delta = 0d;

    int _batchSize = 100;

    int _epoch = 1;

    CostFunction _cf;

    double _rate = 0d;

    boolean _assignWeightFlag = false;

    WorkMemory _memory;

    public static Network build(int xNumber,int[] neuronNumOfLayers,double delta,double rate,int epoch, int batchSize){
        List<Layer> layerList = new ArrayList<Layer>(neuronNumOfLayers.length);
        Network network = new Network(layerList);
        network._delta = delta;
        network._rate = rate;
        network._batchSize = batchSize;
        network._epoch = epoch;
        network._memory = new WorkMemory();

        Layer frontLayer = null;
        for(int index=0;index<neuronNumOfLayers.length;index++){
            Layer layer = Layer.build(xNumber,neuronNumOfLayers[index],frontLayer);
            layer.setDelta(network._delta);
            layer.setRate(rate);
            layerList.add(layer);
            frontLayer = layer;
        }

        layerList.get(layerList.size()-1).setLastFlag(true);

        return network;
    }

    private Network(List<Layer> layerList){
        this._layerList = layerList;
    }

    public void configActivationFunc(int layerOrder, ActivationFunction af){
        _layerList.get(layerOrder).setActivationFunction(af);
    }

    public void configCostFunc(CostFunction cf){
        _layerList.get(_layerList.size()-1).setCostFunction(cf);
        _cf = cf;
    }

    void clear(TrainingSet trainingSet){
        _memory.clear();
        initWeight();
        trainingSet.reset();
    }

    protected NetworkResult train(TrainingSet trainingSet) throws Exception{
        NetworkResult nResult = new NetworkResult();

        int batchNum = 0;
        for(int i=0;i<_epoch;i++) {
            long startTime = System.currentTimeMillis();
            clear(trainingSet);
            while (true) {
                batchNum++;
                _memory.clearDiff();
                List<TrainingData> miniDatas = trainingSet.nextBatch(_batchSize);
                if (miniDatas == null) {
                    break;
                }
                batchTrain(batchNum, miniDatas);
            }
            EpochResult eResult = new EpochResult(i);
            eResult.addResult(_memory._adjustCount,_memory._lastDiff,(System.currentTimeMillis()-startTime),this);
            nResult.add(eResult);
            System.out.println("+++++++++++++++++++++eporch "+i+" end!");
        }
        return nResult;
    }

    private void batchTrain(int batchNo,List<TrainingData> miniDatas) throws Exception{
        int count = 0;

        while(true){
            count++;
            PubUtil.random(miniDatas);
            boolean isOver = singleTrain(batchNo,count,miniDatas);
            if(isOver){
                break;
            }
        }
    }

    private boolean singleTrain(int batchNo, int count, List<TrainingData> miniDatas) throws Exception{
        //误差
        double diff = 0d;
        //是否完成，需要检查每个调整权重小于delta.
        boolean isOver = false;

        int errorCount = 0;
        for (int i = 0; i < miniDatas.size(); i++) {

            TrainingData data = miniDatas.get(i);
            double[] actualOutputs = run(data.x);

            //_delta用于做权重调整，如调整量<_delta，则w不用调整。因此这里实际值和期望值就不用delta
            double[] currDiffs = NumUtil.diff(actualOutputs,data.expectedValues,0);
            //没有偏差
            if(currDiffs == null){
                continue;
            }

            //仅对输出层设置预期值
            lastLayer().setExpectedValue(data.expectedValues);
            diff += _cf.fByArray(actualOutputs,data.expectedValues);
            boolean currentOver = calcWeight();
            if(currentOver){
                continue;
            }
            errorCount++;
        }

        if(errorCount == 0){
            System.out.println("errorCount = 0 ");
            isOver = true;
        }
        //计算当次批次平均误差，理论越来越小
        diff = diff/miniDatas.size();

        _memory.addDiff(diff);
        if(_memory.canAbort() || diff < _delta/10){
            if(diff < _delta/10){
                System.out.println("diff < _delta/10 = "+diff);
            }
            isOver = true;
        }

        /*if(count % 10000 == 1 || isOver) {
            System.out.println("Current batch[" + batchNo + "][" + count + "] error = " + diff + ":isOver = " + isOver+": abort = "+_memory.canAbort());
            if(isOver)
                System.out.println("{" + this + "}");
        }*/

        adjustWeight();

        return isOver;
    }

    boolean calcWeight(){
        boolean isOver = true;
        for(int i=_layerList.size()-1;i>=0;i--){
            Layer layer = _layerList.get(i);
            boolean layerOver = layer.calcWeight();
            if(layerOver == false){
                isOver = false;
            }
        }
        return isOver;
    }

    void adjustWeight(){
        _memory.addAdjustCount();
        LogAction.logFirstAdjustW(this);
        for(Layer layer:_layerList){
            layer.adjustWeight();
        }
    }


    public void assignWeight(double aw){
        _assignWeightFlag = true;
        for(Layer layer:_layerList){
            layer.assignWeight(aw);
        }
    }


    protected void initWeight() {
        if(_assignWeightFlag)
            return;
        for(Layer layer:_layerList){
            layer.initWeight();
        }
    }

    public int getXSize(){
        return _layerList.get(0).getNeuronList().size();
    }

    public int getYSize(){
        return _layerList.get(_layerList.size()-1).getNeuronList().size();
    }

    public double[] run(double[] x) throws Exception{
        double[] results = null;

        for(int i=0;i<_layerList.size();i++){
            Layer layer = _layerList.get(i);
            results = layer.run(x);
        }

        return results;
    }

    public List<Layer> getLayerList(){
        return _layerList;
    }

    private Layer lastLayer(){
        return _layerList.get(_layerList.size()-1);
    }


    public String toString(){
        StringBuffer buffer = new StringBuffer();
        int index = 0;
        buffer.append("Network["+getXSize()+":"+getYSize()+"] = \n");
        for(Layer layer:_layerList){
            buffer.append("\tlayer["+index+"] = \n").append(layer);
            index++;
        }
        return buffer.toString();
    }

}
