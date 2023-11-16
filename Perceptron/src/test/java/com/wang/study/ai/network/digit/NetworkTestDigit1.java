package com.wang.study.ai.network.digit;

import com.wang.study.ai.common.CommonTest;
import com.wang.study.ai.common.TestParam;
import com.wang.study.ai.data.ptype.Digit1PreType;
import com.wang.study.ai.function.activation.ReluFunction;
import com.wang.study.ai.function.activation.SigmoidFunction;
import com.wang.study.ai.function.cost.BCECostFunction;
import com.wang.study.ai.function.cost.CostFunction;
import com.wang.study.ai.network.Network;
import com.wang.study.ai.network.NetworkUtil;
import com.wang.study.ai.util.FileUtil;
import org.junit.Test;

import java.util.ArrayList;

public class NetworkTestDigit1 extends CommonTest {

    @Test
    public void testTrain_01() throws Exception{
        CostFunction cf = new BCECostFunction();

        TestParam tp = new TestParam();

        tp.delta = 0.01d;
        tp.rate = 0.05d;
        tp.batchSize = 500;
        tp.epoch = 1;
        tp.maxAdjustCount = 5000;

        tp.trainingFile = "Network/digit/nts_digit01.dg";
        tp.testFile = "Network/digit/nts_digit09.dg";
        tp.compareDelta = 0.05d;
        tp.logType = 1;

        tp.xNumber = 784;
        tp.cf = cf;
        tp.preType = new Digit1PreType();
        tp.neuronNumOfLayers = new int[]{16,16,16,1};
        tp.afs = new ArrayList<>(tp.neuronNumOfLayers.length);

        for(int i=0;i<tp.neuronNumOfLayers.length;i++){
            if(i != tp.neuronNumOfLayers.length-1){
                tp.afs.add(new ReluFunction());
            }else{
                tp.afs.add(new SigmoidFunction());
            }
        }

        testTrain(tp);
    }

    @Test
    public void continueTrainning() throws Exception{

        //String file = "1699964811326.nw";
        String file = "1700105234241_60.nw";

        Network network = NetworkUtil.json2Network(FileUtil.file2Str(file));

        TestParam tp = new TestParam();

        tp.preType = new Digit1PreType();
        tp.trainingFile = "Network/digit/nts_digit01.dg";
        tp.testFile = "Network/digit/nts_digit09.dg";
        tp.compareDelta = 0.1d;
        tp.logType = 1;


        testTrain(network,tp);

    }

    @Test
    /**
     1700102639004_120.nw 第一次(500/500) 比Digit9差很多，按softmax可以达到90%
     total count/errorCount/successCount/success rate = 10000/5331/4669/46.69%
     total error = 858.383487: avgError = 0.085838
     1700105234241_60.nw.nw 第2次(1000/500)
     total count/errorCount/successCount/success rate = 10000/4827/5173/51.73%
     total error = 770.187107: avgError = 0.077019
     */
    public void testTrained() throws Exception{

        //String file = "1700102639004_120.nw";
        String file = "1700115742471_5.nw";

        Network network = NetworkUtil.json2Network(FileUtil.file2Str(file));

        String testFile = "Network/digit/nts_digit09.dg";

        try {
            compareTrainingSet(network, testFile,0.049999999,new Digit1PreType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}