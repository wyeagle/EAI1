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

public class NetworkTest10 extends CommonTest {

    @Test
    public void testTrain_01() throws Exception{

        List<CostFunction> functions = new ArrayList<>();
        functions.add(new MSECostFunction());
        functions.add(new BCECostFunction());

        TestParam tp = new TestParam();
        tp.xNumber = 2;
        tp.delta = 0.0001d;
        tp.rate = 0.1d;
        tp.batchSize = 100;
        tp.neuronNumOfLayers = new int[]{3, 1};
        tp.epoch = 10;

        tp.trainingFile = "Network/nts10.txt";
        tp.testFile = tp.trainingFile;
        tp.compareDelta = 0.5d;

        for (CostFunction cf : functions) {
            tp.cf = cf;
            testTrain(tp);
        }
    }
}