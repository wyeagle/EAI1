package com.wang.study.ai.network.fc;

import com.wang.study.ai.common.CommonTest;
import com.wang.study.ai.common.TestParam;
import com.wang.study.ai.function.cost.BCECostFunction;
import com.wang.study.ai.function.cost.CostFunction;
import com.wang.study.ai.util.NumUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class NetworkTest12 extends CommonTest {
    
    @Test
    public void testTrain_01() throws Exception{
        List<CostFunction> functions = new ArrayList<>();
        //functions.add(new MSECostFunction());
        functions.add(new BCECostFunction());

        TestParam tp = new TestParam();
        tp.xNumber = 3;
        tp.delta = 0.001d;
        tp.rate = 0.2d;
        tp.batchSize = 500;
        tp.epoch = 1;
        tp.maxAdjustCount = 10001;

        tp.trainingFile = "Network/nts12_1.txt";
        tp.testFile = "Network/nts12_9.txt";
        tp.compareDelta = 0.01d;
        tp.logType = 0;

        List<int[]> nums = new ArrayList<>();

        nums.add(new int[]{2, 1});
        nums.add(new int[]{3, 1});
        nums.add(new int[]{4, 1});
        nums.add(new int[]{5, 1});
        nums.add(new int[]{6, 1});
        for (CostFunction cf : functions) {
            tp.cf = cf;
            for (int[] neuronNumOfLayers : nums) {
                tp.neuronNumOfLayers = neuronNumOfLayers;
                testTrain(tp);
            }
        }
    }

    public static void main(String[] args){
        int num = 500;
        double d = 100;
        double x = 1;

        for(int i=0;i<num;i++){
            System.out.println(NumUtil.random(x,d)+","+NumUtil.random(-d,d)+","+NumUtil.random(-d,d)+":1");
            System.out.println(NumUtil.random(-d,-x)+","+NumUtil.random(-d,d)+","+NumUtil.random(-d,d)+":1");

            System.out.println(NumUtil.random(-d,d)+","+NumUtil.random(x,d)+","+NumUtil.random(-d,d)+":1");
            System.out.println(NumUtil.random(-d,d)+","+NumUtil.random(-d,x)+","+NumUtil.random(-d,d)+":1");

            System.out.println(NumUtil.random(-d,d)+","+NumUtil.random(-d,d)+","+NumUtil.random(x,d)+":1");
            System.out.println(NumUtil.random(-d,d)+","+NumUtil.random(-d,d)+","+NumUtil.random(-d,-x)+":1");
        }
        for(int i=0;i<6*num;i++){
            System.out.println(NumUtil.random(-x,x)+","+NumUtil.random(-x,x)+","+NumUtil.random(-x,x)+":0");
        }
    }
}