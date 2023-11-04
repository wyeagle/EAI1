package com.wang.study.ai.perceptron;

import com.wang.study.ai.common.TrainStatus;
import com.wang.study.ai.data.TrainingSet;
import org.junit.Assert;
import org.junit.Test;

public class PerceptronTest extends PBaseTest {

    @Test
    public void testTrain_01() throws Exception {
        int max = 2;

        int count = 0;
        while(count < max) {
            count++;
            //线性方程w1x1+w2x2+...+wnxn+b=0, b=w0*x0(x0==1), 因此会在每个训练数据上增加w0x0(用addBias)
            TrainingSet trainingSet = super.prepareTrainingSet("Perceptron/pts01_1.txt");

            Perceptron ptron = new Perceptron(trainingSet.get(0).x.length,0.1,0,200);

            Assert.assertEquals(TrainStatus.TRAIN_WAITING,ptron.getStatus());

            ptron.initWeight();
            ptron.train(trainingSet,true);
            Assert.assertEquals(TrainStatus.TRAIN_SUCCESS, ptron.getStatus());
            compareTrainingSet(ptron,"Perceptron/pts01_9.txt");
        }
    }
}
