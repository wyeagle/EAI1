package com.wang.study.ai.network.fc;

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
        //functions.add(new MSECostFunction());
        functions.add(new BCECostFunction());

        TestParam tp = new TestParam();
        tp.xNumber = 2;
        tp.delta = 0.0001d;
        tp.rate = 0.3d;
        tp.batchSize = 4;
        tp.neuronNumOfLayers = new int[]{2, 1};
        tp.epoch = 1;

        tp.trainingFile = "Network/nts10.txt";
        tp.testFile = tp.trainingFile;
        tp.compareDelta = 0.1d;
        tp.logType = 9;

        for (CostFunction cf : functions) {
            tp.cf = cf;
            testTrain(tp);
        }
    }

    @Test
    public void testTrained_02() throws Exception{
        String json = "{\"xnumber\":2,\"neuronNumOfLayers\":[2,1],\"delta\":1.0E-4,\"rate\":0.1,\"epoch\":1,\"batchSize\":4,\"activationFuncs\":[\"com.wang.study.ai.function.activation.SigmoidFunction\",\"com.wang.study.ai.function.activation.SigmoidFunction\"],\n" +
                "\"costFunc\":\"com.wang.study.ai.function.cost.MSECostFunction\"," +
                "\"weights\":[[-0.8,0.5,0.4]," +
                "[0.1,0.9,1]," +
                "[-0.3,-1.2,1.1]]}";

        Network network = NetworkUtil.json2Network(json);

        TestParam tp = new TestParam();


        tp.trainingFile = "Network/nts10.txt";
        tp.testFile = tp.trainingFile;
        tp.compareDelta = 0.1d;
        tp.logType = 9;
        tp.cf = network._cf;

        testTrain(network,tp);
    }
}