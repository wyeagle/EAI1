package com.wang.study.ai.network;


import com.wang.study.ai.common.CommonTest;
import com.wang.study.ai.common.TestParam;
import com.wang.study.ai.data.TrainingData;
import com.wang.study.ai.data.TrainingSet;
import com.wang.study.ai.data.ptype.DefaultPreType;
import com.wang.study.ai.function.activation.SigmoidFunction;
import com.wang.study.ai.function.cost.MSECostFunction;
import com.wang.study.ai.network.result.EpochResult;
import com.wang.study.ai.network.result.NetworkResult;
import com.wang.study.ai.util.LocalUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NetworkTest00 extends CommonTest {
    ThreadLocal<Map<String,Object>> _loaal = new ThreadLocal<Map<String, Object>>();

    @Test
    public void testRun_01() throws Exception{
        int[] neuronNumOfLayers = new int[]{2,1};
        Network network = Network.build(2,neuronNumOfLayers,0.001d,0.1,1,100);

        network.configActivationFunc(0,new SigmoidFunction());
        network.configActivationFunc(1,new SigmoidFunction());

        TrainingSet trainingSet = super.prepareTrainingSet("Network/nts00.txt");
        TrainingData data = trainingSet.get(0);

        double[] w = new double[]{0.278,-0.278,1d,-0.1,-1d,10d};
        double[] expected = new double[]{0.674881,0.405728,0.952123,0.456583,0.266992,1d};

        for(int i=0;i<w.length;i++) {
            network.assignWeight(w[i]);

            double[] results = network.run(data.x);
            Assert.assertEquals(expected[i], results[0], 0.000001);
            List<Neuron> list = network.getLayerList().get(neuronNumOfLayers.length-1).getNeuronList();
            Assert.assertEquals(expected[i], list.get(list.size()-1).getY(), 0.000001);
        }
    }

    private double[] getWeight(int size, double d){
        double[] ds = new double[size];
        for(int i=0;i<ds.length;i++){
            ds[i] = d;
        }
        return ds;
    }

    /*@Test
    public void testRun_02() throws Exception{


        TestParam tp = new TestParam();
        tp.cf = new MSECostFunction();
        tp.xNumber = 3;
        tp.delta = 0.0001d;
        tp.rate = 0.1d;
        tp.batchSize = 100;
        tp.neuronNumOfLayers = new int[]{3,3,2};
        tp.epoch = 1;

        tp.trainingFile = "Network/nts01.txt";
        tp.testFile = tp.trainingFile;
        tp.compareDelta = 0.5d;
        tp.logType = 9;


        testTrain(tp);
    }*/

    @Test
    public void testBP() throws Exception{
        String json = "{\"xnumber\":3,\"neuronNumOfLayers\":[3,3,2],\"delta\":1.0E-4,\"rate\":0.1,\"epoch\":1,\"batchSize\":100," +
                "\"activationFuncs\":[\"com.wang.study.ai.function.activation.SigmoidFunction\",\"com.wang.study.ai.function.activation.SigmoidFunction\"," +
                "\"com.wang.study.ai.function.activation.SigmoidFunction\"],\"costFunc\":\"com.wang.study.ai.function.cost.MSECostFunction\"," +
                "\"weights\":[[-0.72,0.34,0.78,-0.43]," +
                "[0.32,0.92,0.49,-0.10]," +
                "[0.38,0.30,3.06,0.07]," +
                "[0.73,1.39,0.13,-2.53]," +
                "[0.77,0.31,1.67,-3.83]," +
                "[-0.01,-2.88,0.96,0.59]," +
                "[1.15,1.84,0.82,0.79]," +
                "[-2.54,-1.56,-0.47,-0.79]]}";

        Network network = NetworkUtil.json2Network(json);

        TrainingSet trainingSet = new TrainingSet(new ArrayList<TrainingData>());
        TrainingData data = new TrainingData();
        data.expectedValues = new double[]{1,0};
        data.x = new double[]{5,6,7};
        trainingSet.getTrainingDatas().add(data);
        trainingSet.preprocessing(new DefaultPreType());

        LocalUtil.add("UNITTEST",new Boolean(true));

        NetworkResult result = network.train(trainingSet, true);

        NetworkConfig config = (NetworkConfig)LocalUtil.get("NETWORK");

        //TODO 比较weights
        List<double[]> actualWeights = config.getWeights();

        List<double[]> expectedWeights = new ArrayList<>();
        expectedWeights.add(new double[]{-0.719949,0.339981,0.780202,-0.429694});
        expectedWeights.add(new double[]{0.320001,0.919999,0.4900016,-0.0999991});
        expectedWeights.add(new double[]{0.38,0.3,3.06,0.07});
        expectedWeights.add(new double[]{0.7303259922,1.3904232276,0.1300423461,-2.5308247602});
        expectedWeights.add(new double[]{0.7702526450,0.3100731517,1.6704215895,-3.8309676303});
        expectedWeights.add(new double[]{-0.0097535865,-2.8806628404,0.9602363733,0.5901453840});
        expectedWeights.add(new double[]{1.1515216283,1.8406209764,0.8203795116,0.7903657413});
        expectedWeights.add(new double[]{-2.5401720681,-1.5600702210,-0.4700429158,-0.7900413586});

        compareList(expectedWeights,actualWeights);
    }

}