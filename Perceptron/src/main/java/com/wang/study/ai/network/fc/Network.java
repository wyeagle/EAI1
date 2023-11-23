package com.wang.study.ai.network.fc;

import com.wang.study.ai.common.NetworkUnit;
import com.wang.study.ai.data.TrainingData;
import com.wang.study.ai.data.TrainingSet;
import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.function.cost.CostFunction;
import com.wang.study.ai.network.WorkMemory;
import com.wang.study.ai.network.result.EpochResult;
import com.wang.study.ai.network.result.NetworkResult;
import com.wang.study.ai.util.FileUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Network extends NetworkUnit {


    List<Layer> _layerList;

    double _delta = 0d;

    int _batchSize = 100;

    int _epoch = 1;

    CostFunction _cf;

    double _rate = 0d;

    boolean _assignWeightFlag = false;

    int _xNumber = 0;

    String _baseFile;

    WorkMemory _memory;

    public static Network build(int xNumber,int[] neuronNumOfLayers,double delta,double rate,int epoch, int batchSize,long maxAdjustCount){
        List<Layer> layerList = new ArrayList<Layer>(neuronNumOfLayers.length);
        Network network = new Network(layerList);
        network._delta = delta;
        network._rate = rate;
        network._batchSize = batchSize;
        network._epoch = epoch;
        network._memory = new WorkMemory();
        network._memory._maxAdjustCount = maxAdjustCount;
        network._memory._delta = delta;
        network._xNumber = xNumber;
        network._baseFile = System.currentTimeMillis()+"";

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


        trainingSet.reset();
    }

    protected NetworkResult train(TrainingSet trainingSet) throws Exception {
        NetworkResult nResult = new NetworkResult();
        initWeight();

        for(int i=0;i<_epoch;i++) {
            int batchNum = 0;
            long startTime = System.currentTimeMillis();
            clear(trainingSet);

            while (true) {
                batchNum++;
                _memory.clear();
                List<TrainingData> miniDatas = trainingSet.nextBatch(_batchSize);
                if (miniDatas == null) {
                    break;
                }
                batchTrain(batchNum, miniDatas);
                FileUtil.str2File(_baseFile+"_"+batchNum+".nw",NetworkUtil.network2Json(this));
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
            Collections.shuffle(miniDatas);
            boolean isOver = singleTrain(batchNo,count,miniDatas);
            if(isOver){
                break;
            }
        }
    }

    private boolean singleTrain(int batchNo, int count, List<TrainingData> miniDatas) throws Exception{
        //误差
        double avgDiff = 0d;
        //是否完成，需要检查每个调整权重小于delta.
        boolean isOver = false;

        int errorCount = 0;

        for (int i = 0; i < miniDatas.size(); i++) {

            TrainingData data = miniDatas.get(i);
            double[] actualOutputs = run(data.x);
            //System.err.println("test data = " + data + " :  actual value = " + PubUtil.print(actualOutputs));

            //仅对输出层设置预期值
            lastLayer().setExpectedValue(data.expectedValues);

            double currDiff = _cf.fByArray(actualOutputs,data.expectedValues);

            avgDiff += currDiff;
            if(currDiff < _delta){
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
        if(canAbort || avgDiff < _delta){
            if(avgDiff < _delta){
                System.out.println("diff < _delta = "+avgDiff);
            }
            isOver = true;
        }

        if(isOver || (count % 10 == 1 && _batchSize >= 10000) || (count % 1000 == 1 && _batchSize < 10000)) {

            System.out.println(new Date()+":Current batch[" + batchNo + "][" + count + "] " +
                    "avgerage error = " + avgDiff + ":isOver = " + isOver+": abort = "+canAbort+": current errorcount = "+errorCount);
        }
        if(count % 100 == 0 && _batchSize >= miniDatas.size() && _batchSize >= 10000){
            FileUtil.str2File(_baseFile+"_"+count+".nw",NetworkUtil.network2Json(this));
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

        for(Layer layer:_layerList){
            layer.adjustWeight();
        }
        LogAction.logFirstAdjustW(this);
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
        return _xNumber;
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


    /*private boolean singleTrain1(int batchNo, int count, List<TrainingData> miniDatas) throws Exception{
        //误差
        double diff = 0d;
        //是否完成，需要检查每个调整权重小于delta.
        boolean isOver = false;

        int errorCount = 0;
        List<TrainingData> errorDatas = new ArrayList<>();
        for (int i = 0; i < miniDatas.size(); i++) {

            TrainingData data = miniDatas.get(i);
            double[] actualOutputs = run(data.x);
            //System.err.println("test data = " + data + " :  actual value = " + PubUtil.print(actualOutputs));

            //_delta用于做权重调整，如调整量<_delta，则w不用调整。因此这里实际值和期望值就不用delta
            double[] currDiffs = NumUtil.diff(actualOutputs,data.expectedValues,0.03);
            //没有偏差
            if(currDiffs == null){
                continue;
            }

            errorDatas.add(data);

            //System.err.println("Adjust data = "+data);
            //仅对输出层设置预期值
            lastLayer().setExpectedValue(data.expectedValues);

            calcWeight();

            adjustWeight();
            //System.out.println("1");
        }

        if(errorDatas.size() == 0){
            System.out.println("errorCount = 0 ");
            isOver = true;
        }

        if(_memory.canAbort() ){
            isOver = true;
        }



        return isOver;
    }*/


}
