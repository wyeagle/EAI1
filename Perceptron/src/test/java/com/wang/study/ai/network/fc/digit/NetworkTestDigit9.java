package com.wang.study.ai.network.fc.digit;

import com.wang.study.ai.common.CommonTest;
import com.wang.study.ai.common.TestParam;
import com.wang.study.ai.data.ptype.DigitPreType;
import com.wang.study.ai.function.activation.LeakyReluFunction;
import com.wang.study.ai.function.activation.SoftmaxFunction;
import com.wang.study.ai.function.cost.BCECostFunction;
import com.wang.study.ai.function.cost.CostFunction;
import com.wang.study.ai.network.fc.Network;
import com.wang.study.ai.network.fc.NetworkUtil;
import com.wang.study.ai.util.FileUtil;
import org.junit.Test;

import java.util.ArrayList;

public class NetworkTestDigit9 extends CommonTest {

    @Test
    /**
     * 8*8*10反而不如8*10
     */
    public void testTrain_01() throws Exception{
        CostFunction cf = new BCECostFunction();

        TestParam tp = new TestParam();

        tp.delta = 0.0001d;
        tp.rate = 0.01d;
        tp.batchSize = 512;
        tp.epoch = 1;
        tp.maxAdjustCount = 200;

        tp.trainingFile = "Network/digit/nts_digit01.dg";
        tp.testFile = "Network/digit/nts_digit09.dg";
        tp.compareDelta = 0.1d;
        tp.logType = 1;

        tp.xNumber = 28*28;
        tp.cf = cf;
        tp.preType = new DigitPreType();
        tp.neuronNumOfLayers = new int[]{16,16,10};
        tp.afs = new ArrayList<>(tp.neuronNumOfLayers.length);

        for(int i=0;i<tp.neuronNumOfLayers.length;i++){
            if(i != tp.neuronNumOfLayers.length-1){
                tp.afs.add(new LeakyReluFunction());
            }else{
                tp.afs.add(new SoftmaxFunction());
            }
        }

        testTrain(tp);
    }

    @Test
    public void testTrain_02() throws Exception{
        CostFunction cf = new BCECostFunction();

        TestParam tp = new TestParam();

        tp.delta = 0.0001d;
        tp.rate = 0.05d;
        tp.batchSize = 500;
        tp.epoch = 1;
        tp.maxAdjustCount = 100000;

        tp.trainingFile = "Network/digit/nts_digit01.dg";
        tp.testFile = "Network/digit/nts_digit09.dg";
        tp.compareDelta = 0.1d;
        tp.logType = 1;

        tp.xNumber = 28*28;
        tp.cf = cf;
        tp.preType = new DigitPreType();
        tp.neuronNumOfLayers = new int[]{16,16,10};
        tp.afs = new ArrayList<>(tp.neuronNumOfLayers.length);

        for(int i=0;i<tp.neuronNumOfLayers.length;i++){
            if(i != tp.neuronNumOfLayers.length-1){
                tp.afs.add(new LeakyReluFunction());
            }else{
                tp.afs.add(new SoftmaxFunction());
            }
        }

        testTrain(tp);
    }



    @Test
    public void continueTrainning() throws Exception{

        Network network = NetworkUtil.json2Network(FileUtil.file2Str("1700135182469_12200.nw"));

        TestParam tp = new TestParam();

        tp.preType = new DigitPreType();
        tp.trainingFile = "Network/digit/nts_digit01.dg";
        tp.testFile = "Network/digit/nts_digit09.dg";
        tp.compareDelta = 0.1d;
        tp.logType = 1;


        testTrain(network,tp);

    }

    @Test
    public void continueTrainning2() throws Exception{
        //93.93%
        String file = "1700298820943_87.nw";

        Network network = NetworkUtil.json2Network(FileUtil.file2Str(file));

        TestParam tp = new TestParam();

        tp.preType = new DigitPreType();
        tp.trainingFile = "Network/digit/nts_digit01.dg";
        tp.testFile = "Network/digit/nts_digit09.dg";
        tp.compareDelta = 0.1d;
        tp.logType = 1;


        testTrain(network,tp);

    }

    @Test
    public void continueTrainning3() throws Exception{
        //93.93%
        String file = "1700306026403_120.nw";

        Network network = NetworkUtil.json2Network(FileUtil.file2Str(file));

        TestParam tp = new TestParam();

        tp.preType = new DigitPreType();
        tp.trainingFile = "Network/digit/nts_digit01.dg";
        tp.testFile = "Network/digit/nts_digit09.dg";
        tp.compareDelta = 0.1d;
        tp.logType = 1;


        testTrain(network,tp);

    }

    @Test
    /**
     *
     1699972226128.nw(随机值，没有训练)
     total count/errorCount/successCount/success rate = 60000/51983/8017/13.36%
     total count/errorCount/successCount/success rate = 10000/8625/1375/13.75%
     1699964811326.nw(第一次训练，每批500，每批跑501)
     total count/errorCount/successCount/success rate = 60000/5924/54076/90.13%
     total count/errorCount/successCount/success rate = 10000/978/9022/90.22%
     1699970672837.nw(第二次训练，每批500，每批跑201)
     total count/errorCount/successCount/success rate = 60000/5635/54365/90.61%
     total count/errorCount/successCount/success rate = 10000/972/9028/90.28%
     60_1700017620724.nw(第三次，每批1000，每批跑2000)
     total count/errorCount/successCount/success rate = 10000/886/9114/91.14%
     total error = 1027.100376: avgError = 0.102710
     12_1700028859018.nw(第4次，每批5000,10000)
     total count/errorCount/successCount/success rate = 10000/791/9209/92.09%
     total error = 875.581503: avgError = 0.087558

     200_1700017667553.nw
     total count/errorCount/successCount/success rate = 10000/2663/7337/73.37%
     total error = 3804.532212: avgError = 0.380453
     500_1700017667553.nw
     total count/errorCount/successCount/success rate = 10000/2360/7640/76.40%
     total error = 3400.869822: avgError = 0.340087
     1500_1700017667553.nw
     total count/errorCount/successCount/success rate = 10000/1699/8301/83.01%
     total error = 2550.468766: avgError = 0.255047
     2500_1700017667553.nw
     total count/errorCount/successCount/success rate = 10000/1449/8551/85.51%
     total error = 2152.867431: avgError = 0.215287
     3500_1700017667553.nw
     total count/errorCount/successCount/success rate = 10000/1264/8736/87.36%
     total error = 1905.200988: avgError = 0.190520
     10000
     total count/errorCount/successCount/success rate = 10000/893/9107/91.07%
     total error = 1329.501840: avgError = 0.132950
     13000
     total count/errorCount/successCount/success rate = 10000/809/9191/91.91%
     total error = 1214.433445: avgError = 0.121443
     16400
     total count/errorCount/successCount/success rate = 10000/743/9257/92.57%
     total error = 1117.491416: avgError = 0.111749
     1700107723818_1800.nw
     total count/errorCount/successCount/success rate = 10000/713/9287/92.87%
     total error = 1065.938639: avgError = 0.106594
     1700124931534_2400.nw
     total count/errorCount/successCount/success rate = 10000/696/9304/93.04%
     total error = 1023.460361: avgError = 0.102346
     1700135182469_12200.nw
     total count/errorCount/successCount/success rate = 10000/620/9380/93.80%
     total error = 911.570452: avgError = 0.091157
     */
    public void testTrained() throws Exception{

        String[] prefixs = new String[]{"1700535972800_"};

        String testFile = "Network/digit/nts_digit09.dg";

        for(int i=60;i<=120;i=i+2) {
            for(int j=0;j<prefixs.length;j++) {
                String file = prefixs[j] + i + ".nw";
                System.out.println(file + " begin");
                Network network = NetworkUtil.json2Network(FileUtil.file2Str(file));
                try {
                    compareMultiType(network, new DigitPreType(), testFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testTrained2() throws Exception{

        String file = "1700534447796_10.nw";

        String testFile = "Network/digit/nts_digit09.dg";


        Network network = NetworkUtil.json2Network(FileUtil.file2Str(file));
        try {
            compareMultiType(network, new DigitPreType(), testFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}