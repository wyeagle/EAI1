package com.wang.study.ai.network;

import com.wang.study.ai.common.CommonTest;
import com.wang.study.ai.common.TestParam;
import com.wang.study.ai.function.cost.BCECostFunction;
import com.wang.study.ai.function.cost.CostFunction;
import com.wang.study.ai.function.cost.MSECostFunction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class NetworkTest10 extends CommonTest {

    @Test
    public void testTrain_01() throws Exception{

        List<CostFunction> functions = new ArrayList<CostFunction>();
        functions.add(new MSECostFunction());
        functions.add(new BCECostFunction());

        TestParam tp = new TestParam();
        tp.xNumber = 2;
        tp.delta = 0.0001d;
        tp.rate = 0.1d;
        tp.batchSize = 100;
        tp.neuronNumOfLayers = new int[]{2, 1};
        tp.epoch = 10;

        tp.trainingFile = "Network/nts10.txt";
        tp.testFile = tp.trainingFile;
        tp.compareDelta = 0.5d;
        tp.logType = 9;

        for (CostFunction cf : functions) {
            tp.cf = cf;
            testTrain(tp);
        }
    }
}