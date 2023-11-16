package com.wang.study.ai.common;

import com.wang.study.ai.data.ptype.DefaultPreType;
import com.wang.study.ai.data.ptype.PreType;
import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.function.activation.SigmoidFunction;
import com.wang.study.ai.function.cost.CostFunction;
import com.wang.study.ai.network.Network;
import com.wang.study.ai.network.NetworkUtil;
import com.wang.study.ai.network.result.EpochResult;
import com.wang.study.ai.network.result.NetworkResult;
import com.wang.study.ai.perceptron.Perceptron;
import com.wang.study.ai.data.TrainingData;
import com.wang.study.ai.data.TrainingSet;
import com.wang.study.ai.util.FileUtil;
import com.wang.study.ai.util.NumUtil;
import com.wang.study.ai.util.PubUtil;
import com.wang.study.ai.util.TestUtil;
import org.junit.Assert;
import sun.nio.ch.Net;

import java.math.BigDecimal;
import java.util.List;

public class CommonTest {

    protected void compareList(List<double[]> orgins, List<double[]> expecteds, List<double[]> actuals){
        Assert.assertEquals(expecteds.size(),actuals.size());
        for(int i=0;i<expecteds.size();i++){
            double[] es = expecteds.get(i);
            double[] as = actuals.get(i);
            double[] os = orgins.get(i);
            Assert.assertEquals(es.length,as.length);


            for(int j=0;j<es.length;j++){

                Assert.assertEquals(es[j], as[j], 0.0000000000001d);
                if((es[j]-os[j])*(as[j]-os[j]) < 0){
                    Assert.assertFalse(false);
                }

            }
        }
    }

    protected TrainingSet prepareTrainingSet(PreType preType, String file){
        try {
            TrainingSet trainingSet = TestUtil.file2Ts(preType,file);
            return trainingSet;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }


    protected void compareTrainingSet(Perceptron unit, String file) throws Exception{
        TrainingSet testSet = prepareTrainingSet(new DefaultPreType(),file);

        double avgError = 0d;
        for (int i = 0; i < testSet.size(); i++) {
            TrainingData data = testSet.get(i);
            double[] y = unit.run(data.x);

            for(int j=0;j<y.length;j++) {
                if(unit instanceof Perceptron)
                    Assert.assertEquals(String.valueOf(data.expectedValues[j]), String.valueOf(y[j]));
            }
        }
    }

    protected void compareTrainingSet(Network network, String file, double delta) throws Exception{
        compareTrainingSet(network, file, delta, new DefaultPreType());
    }

    protected void compareTrainingSet(Network network, String file, double delta,PreType preType) throws Exception{
        TrainingSet testSet = prepareTrainingSet(preType,file);

        int errorCount = 0;
        double avgError = 0d;
        for (int i = 0; i < testSet.size(); i++) {
            TrainingData data = testSet.get(i);
            double[] y = network.run(data.x);

            for (int j = 0; j < y.length; j++) {
                try {
                    Assert.assertEquals(data.expectedValues[j], y[j], delta);
                    //System.out.println("test data = " + data + " : actual value = " + String.valueOf(y[j]) + " : error = " + (y[j] - data.expectedValues[j]));
                }catch(Error e){
                    errorCount++;
                    if(errorCount % 100 == 0)
                        System.err.println("test data = " + data + " : actual value = " + String.valueOf(y[j]) + " : error = " + (y[j] - data.expectedValues[j]));
                }


                avgError += Math.abs(y[j] - data.expectedValues[j]);
            }
        }
        int sum = testSet.size()*testSet.get(0).expectedValues.length;
        BigDecimal successRate = new BigDecimal((double)(sum - errorCount)*100/sum);
        successRate = successRate.setScale(2,BigDecimal.ROUND_HALF_DOWN);
        System.out.println("total count/errorCount/successCount/success rate = " + sum+"/"+errorCount+"/"+(sum-errorCount)+"/"+successRate+"%");
        System.out.println("total error = " + PubUtil.print(avgError) + ": avgError = " + PubUtil.print(avgError / sum));
    }

    protected void compareMultiType(Network network, PreType preType,String file) throws Exception{
        TrainingSet testSet = prepareTrainingSet(preType,file);

        int errorCount = 0;
        double avgError = 0d;
        for (int i = 0; i < testSet.size(); i++) {
            TrainingData data = testSet.get(i);
            double[] y = network.run(data.x);

            int actualValue = NumUtil.maxIndex(y);
            double actualPercent = y[actualValue];

            int expectedValue = getIndex(data.expectedValues,1d);

            if(actualValue == expectedValue){
                //System.out.println("actualValue = "+actualValue+": percent = "+actualPercent+": expectedValue = "+expectedValue);
                avgError += Math.abs(1-actualPercent);
            }else{
                errorCount++;
                //System.err.println("actualValue = "+actualValue+": percent = "+y[expectedValue]+": expectedValue = "+expectedValue);

                avgError += Math.abs(1-y[expectedValue]);
            }
        }
        int sum = testSet.size();
        BigDecimal successRate = new BigDecimal((double)(sum - errorCount)*100/sum);
        successRate = successRate.setScale(2,BigDecimal.ROUND_HALF_DOWN);
        System.out.println("total count/errorCount/successCount/success rate = " + sum+"/"+errorCount+"/"+(sum-errorCount)+"/"+successRate+"%");
        System.out.println("total error = " + PubUtil.print(avgError) + ": avgError = " + PubUtil.print(avgError / sum));
        System.out.println(file);
    }

    private int getIndex(double[] ds,double digit){
        for(int i=0;i<ds.length;i++){
            if(ds[i] == digit){
                return i;
            }
        }
        return -1;
    }

    protected void compareTrainingSet(NetworkResult netResult, PreType preType,String file, double delta, int logType) throws Exception{
        TrainingSet testSet = prepareTrainingSet(preType,file);

        EpochResult bestResult = netResult.bestResult();
        if(logType >= 0)
            System.out.println("total time/diff/adjustCount = "+netResult._totalTime+" / "+PubUtil.print(netResult._totalDiff)+" / "+netResult._totalAdjustCount);

        //int size = netResult.getEporchResults().size();


        //for(int index=size-1;index>=0;index--) {
        //EpochResult eResult = netResult.getEporchResults().get(index);
        Network network = NetworkUtil.json2Network(bestResult._networkJson);
        if(logType >= 0) {
            System.out.println("network eporch " + PubUtil.substring(bestResult.toString(), 1000));
            //FileUtil.str2File(System.currentTimeMillis()+".nw",bestResult._networkJson);
        }
        double avgError = 0d;
        int errorCount = 0;
        for (int i = 0; i < testSet.size(); i++) {
            TrainingData data = testSet.get(i);
            double[] y = network.run(data.x);

            for (int j = 0; j < y.length; j++) {
                try {
                    Assert.assertEquals(data.expectedValues[j], y[j], delta);
                    if(logType > 1) {
                        //System.out.println("test data = " + data + " : y[" + j + "] actual value = " + String.valueOf(y[j]) + " : error = " + (y[j] - data.expectedValues[j]));
                        System.out.println("y[" + j + "] actual value = " + String.valueOf(y[j]) + " : error = " + (y[j] - data.expectedValues[j]));
                    }
                }catch(Error e){
                    errorCount++;
                    if(logType >= 1) {
                        //System.err.println("test data = " + data + " : y[" + j + "] actual value = " + String.valueOf(y[j]) + " : error = " + (y[j] - data.expectedValues[j]));
                        System.err.println("y[" + j + "] actual value = " + String.valueOf(y[j]) + " : error = " + (y[j] - data.expectedValues[j]));
                    }
                }


                avgError += Math.abs(y[j] - data.expectedValues[j]);
            }
        }
        int sum = testSet.size()*testSet.get(0).expectedValues.length;
        BigDecimal successRate = new BigDecimal((double)(sum - errorCount)*100/sum);
        successRate = successRate.setScale(2,BigDecimal.ROUND_HALF_DOWN);
        if(logType >= 0){
            System.out.println("total count/errorCount/successCount/success rate = " + sum+"/"+errorCount+"/"+(sum-errorCount)+"/"+successRate+"%");
            System.out.println("total error = " + PubUtil.print(avgError) + ": avgError = " + PubUtil.print(avgError / sum));
        }
    }


    protected void testTrain(TestParam tp) throws Exception{
        System.err.println("Start SubTest : "+ tp);
        Network network = Network.build(tp.xNumber, tp.neuronNumOfLayers, tp.delta, tp.rate, tp.epoch, tp.batchSize,tp.maxAdjustCount);

        int length = tp.neuronNumOfLayers.length;
        for (int i = 0; i < length; i++) {
            ActivationFunction af = null;
            if(tp.afs == null){
                af = new SigmoidFunction();
            }else{
                af = tp.afs.get(i);
            }
            network.configActivationFunc(i, af);
        }

        //配置cost
        network.configCostFunc(tp.cf);

        TrainingSet trainingSet = prepareTrainingSet(tp.preType,tp.trainingFile);

        Assert.assertEquals(TrainStatus.TRAIN_WAITING, network.getStatus());

        NetworkResult result = network.train(trainingSet, true);

        EpochResult nResult = result.bestResult();


        Assert.assertEquals(TrainStatus.TRAIN_SUCCESS, network.getStatus());

        try {
            compareTrainingSet(result, tp.preType,tp.testFile, tp.compareDelta, tp.logType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("End SubTest : "+ tp);
    }


    protected void testTrain(Network network,TestParam tp) throws Exception{

        TrainingSet trainingSet = prepareTrainingSet(tp.preType,tp.trainingFile);


        Assert.assertEquals(TrainStatus.TRAIN_WAITING, network.getStatus());

        NetworkResult result = network.train(trainingSet, true);

        EpochResult nResult = result.bestResult();


        Assert.assertEquals(TrainStatus.TRAIN_SUCCESS, network.getStatus());

        try {
            compareTrainingSet(result, tp.preType,tp.testFile, tp.compareDelta, tp.logType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("End SubTest : "+ tp);
    }

}
