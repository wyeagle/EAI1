package com.wang.study.ai.network.cnn;

import com.wang.study.ai.common.EAIException;
import com.wang.study.ai.function.activation.ActivationEnum;
import com.wang.study.ai.function.cost.CostEnum;
import com.wang.study.ai.network.cnn.enums.PaddingEnum;
import com.wang.study.ai.network.cnn.enums.WeightEnum;
import com.wang.study.ai.tensor.Tensor;
import com.wang.study.ai.tensor.Tensors;
import com.wang.study.ai.util.NumUtil;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class NetworkTest {

    @Test
    public void testRun1() {
        NetworkBuilder builder = new NetworkBuilder();
        Network network = builder.buildInputLayer(1,28,28)
                .buildConvolutionLayer(1,3,3,1, PaddingEnum.VALID,10, ActivationEnum.Relu)
                .buildSubSamplingLayer(2,2,2)
                .buildFCLayer(16,ActivationEnum.Relu)
                .buildOutputLayer(10,ActivationEnum.Softmax, CostEnum.BCE)
                .build(WeightEnum.HE)
                .createNetwork();

        //[1,28,28]
        Tensor input = Tensors.create(getTestData(0,1));
        Tensor output = network.run(input);
        System.out.println(output);
    }

    private double[][][] getTestData(double min, double max){
        double[][][] ds = new double[1][28][28];
        for(int i=0;i<ds.length;i++){
            for(int j=0;j<ds[0].length;j++){
                for(int k=0;k<ds[0][0].length;k++){
                    ds[i][j][k] = NumUtil.random(min,max);
                }
            }
        }
        return ds;
    }

    @Test
    public void testCreate1() {
        NetworkBuilder builder = null;

        try {
            builder = new NetworkBuilder();
            Network network = builder.buildInputLayer(1, 28, 28)
                    .buildConvolutionLayer(1,3,3,1, PaddingEnum.VALID,10, ActivationEnum.Relu)
                    .build(WeightEnum.HE)
                    .createNetwork();
            Assert.assertTrue(false);
        }catch(EAIException e){
            System.out.println(e.getMessage());
            Assert.assertTrue(true);
        }

        try {
            builder = new NetworkBuilder();
            Network network = builder.buildInputLayer(1, 28, 28)
                    .buildOutputLayer(10, ActivationEnum.Softmax, CostEnum.BCE)
                    .buildSubSamplingLayer(2,2,2)
                    .build(WeightEnum.HE)
                    .createNetwork();
            Assert.assertTrue(false);
        }catch(EAIException e){
            System.out.println(e.getMessage());
            Assert.assertTrue(true);
        }


    }
}