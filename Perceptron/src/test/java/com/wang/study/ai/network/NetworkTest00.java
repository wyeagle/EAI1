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

        NetworkConfig orginConfig = NetworkUtil.network2config(network);

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
        expectedWeights.add(new double[]{-0.7199751188095310,0.3401244059523420,0.7801492871428110,-0.4298258316667210});
        expectedWeights.add(new double[]{0.3200005789762100,0.9200028948810520,0.4900034738572620,-0.0999959471665271});
        expectedWeights.add(new double[]{0.3799999999973340,0.2999999999866720,3.0599999999840100,0.0699999999813412});
        expectedWeights.add(new double[]{0.7307411425382260,1.3906922352762400,0.1307405670772070,-2.5292588574625100});
        expectedWeights.add(new double[]{0.7702487224685690,0.3102323095192300,1.6702485293477100,-3.8297512775316800});
        expectedWeights.add(new double[]{-0.0097556935782184,-2.8797718151170000,0.9602441167297650,0.5902443064215390});
        expectedWeights.add(new double[]{1.1515216282916700,1.8406209764323000,0.8203795116402450,0.7903657412964130});
        expectedWeights.add(new double[]{-2.5401720681031500,-1.5600702209845800,-0.4700429157688640,-0.7900413585968820});


        compareList(orginConfig.getWeights(),expectedWeights,actualWeights);
    }

}