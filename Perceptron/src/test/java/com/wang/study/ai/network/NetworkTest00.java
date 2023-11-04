package com.wang.study.ai.network;

import com.wang.study.ai.common.CommonTest;
import com.wang.study.ai.common.TrainStatus;
import com.wang.study.ai.data.ptype.DefaultPreType;
import com.wang.study.ai.function.activation.SigmoidFunction;
import com.wang.study.ai.function.cost.BCECostFunction;
import com.wang.study.ai.function.cost.CostFunction;
import com.wang.study.ai.function.cost.MSECostFunction;
import com.wang.study.ai.data.TrainingData;
import com.wang.study.ai.data.TrainingSet;
import com.wang.study.ai.network.result.EpochResult;
import com.wang.study.ai.network.result.NetworkResult;
import com.wang.study.ai.util.NumUtil;
import com.wang.study.ai.util.PubUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NetworkTest00 extends CommonTest {

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

}