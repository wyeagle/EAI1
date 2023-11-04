package com.wang.study.ai.network;

import com.wang.study.ai.common.CommonTest;
import com.wang.study.ai.common.TestParam;
import com.wang.study.ai.common.TrainStatus;
import com.wang.study.ai.data.TrainingData;
import com.wang.study.ai.data.TrainingSet;
import com.wang.study.ai.data.ptype.DefaultPreType;
import com.wang.study.ai.function.activation.SigmoidFunction;
import com.wang.study.ai.function.cost.BCECostFunction;
import com.wang.study.ai.function.cost.CostFunction;
import com.wang.study.ai.function.cost.MSECostFunction;
import com.wang.study.ai.network.result.EpochResult;
import com.wang.study.ai.network.result.NetworkResult;
import com.wang.study.ai.util.NumUtil;
import com.wang.study.ai.util.PubUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class NetworkTest11 extends CommonTest {



    @Test
    public void testTrain_01() throws Exception{
        List<CostFunction> functions = new ArrayList<>();
        functions.add(new MSECostFunction());
        functions.add(new BCECostFunction());

        TestParam tp = new TestParam();
        tp.xNumber = 2;
        tp.delta = 0.0001d;
        tp.rate = 0.1d;
        tp.batchSize = 500;
        tp.epoch = 5;

        tp.trainingFile = "Network/nts11_1.txt";
        tp.testFile = "Network/nts11_9.txt";
        tp.compareDelta = 0.5d;

        List<int[]> nums = new ArrayList<>();

        //nums.add(new int[]{2, 1});
        //nums.add(new int[]{3, 1});
        //nums.add(new int[]{4, 1});
        nums.add(new int[]{5, 1});

        for (CostFunction cf : functions) {
            tp.cf = cf;
            for (int[] neuronNumOfLayers : nums) {
                tp.neuronNumOfLayers = neuronNumOfLayers;
                testTrain(tp);
            }
        }
    }

    @Test
    public void testTrainedNetwork_01() throws Exception{
        String json = "{\"xnumber\":5,\"neuronNumOfLayers\":[5,1],\"delta\":1.0E-4,\"rate\":0.1,\"epoch\":5,\"batchSize\":500,\"activationFuncs\":[\"com.wang.study.ai.function.activation.SigmoidFunction\",\"com.wang.study.ai.function.activation.SigmoidFunction\"],\"costFunc\":\"com.wang.study.ai.function.cost.MSECostFunction\",\"weights\":[[2.0058721658021716,-1.2365697661254644,-0.05026690612510282],[-1.921967886980531,3.9299282554790382,1.1109835356967157],[0.13126718867759654,0.8317563167428897,-0.9912232122919421],[-0.7694492828889946,-3.4936364882321906,-0.06678478624729971],[-7.400316813642184,7.2806230203489735,-0.015883843686040636],[-0.12149411451632892,-5.180517697558073,-1.2773849396871246,-0.3523455557377948,11.624959227569843,6.725924114949544]]}";
        Network network = NetworkUtil.json2Network(json);
        String testFile = "Network/nts11_9.txt";
        double compareDelta = 0.5d;

        try {
            compareTrainingSet(network, testFile, compareDelta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        int n = 5000;
        for(int i=0;i<n;i++){
            System.out.println(NumUtil.random(-10,0)+","+NumUtil.random(-10,10)+":1");
            System.out.println(NumUtil.random(1.01,10)+","+NumUtil.random(-10,10)+":1");
        }
        for(int i=0;i<2*n;i++){
            System.out.println(NumUtil.random(0.0001,0.99999)+","+NumUtil.random(-10,10)+":0");
        }
    }
}