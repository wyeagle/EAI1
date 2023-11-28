package com.wang.study.ai.network.cnn;

import com.wang.study.ai.common.EAIException;
import com.wang.study.ai.data.TrainingData;
import com.wang.study.ai.data.TrainingSet;
import com.wang.study.ai.data.ptype.DefaultPreType;
import com.wang.study.ai.function.activation.ActivationEnum;
import com.wang.study.ai.function.activation.SigmoidFunction;
import com.wang.study.ai.function.cost.CostEnum;
import com.wang.study.ai.network.cnn.enums.PaddingEnum;
import com.wang.study.ai.network.cnn.enums.WeightEnum;
import com.wang.study.ai.network.fc.Neuron;
import com.wang.study.ai.tensor.Tensor;
import com.wang.study.ai.tensor.Tensors;
import com.wang.study.ai.util.NumUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class NetworkFCTest {

    @Test
    public void testRun1() {
        NetworkBuilder builder = new NetworkBuilder();
        Network network = builder.buildInputLayer(0,2,1)
                .buildFCLayer(2,ActivationEnum.Sigmoid)
                .buildOutputLayer(1,ActivationEnum.Sigmoid, CostEnum.BCE)
                .build(WeightEnum.HE)
                .createNetwork();

        //[2,1]
        Tensor input = Tensors.create(getTestData(0,1));
        Tensor output = network.run(input);
        System.out.println(output);
    }

    private double[][] getTestData(double min, double max){
        double[][] ds = new double[2][1];
        for(int i=0;i<ds.length;i++){
            for(int j=0;j<ds[0].length;j++){
                ds[i][j] = NumUtil.random(min,max);
            }
        }
        return ds;
    }
}