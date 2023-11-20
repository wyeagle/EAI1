package com.wang.study.ai.network.digit;

import com.wang.study.ai.common.CommonTest;
import com.wang.study.ai.common.TestParam;
import com.wang.study.ai.data.ptype.DigitPreType;
import com.wang.study.ai.function.activation.ReluFunction;
import com.wang.study.ai.function.activation.SigmoidFunction;
import com.wang.study.ai.function.activation.SoftmaxFunction;
import com.wang.study.ai.function.cost.BCECostFunction;
import com.wang.study.ai.function.cost.CostFunction;
import com.wang.study.ai.function.cost.MSECostFunction;
import com.wang.study.ai.util.BinaryFile;
import com.wang.study.ai.util.NumUtil;
import com.wang.study.ai.util.PubUtil;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NetworkTestDigit extends CommonTest {
    
    @Test
    public void testTrain_01() throws Exception{
        CostFunction cf = new BCECostFunction();

        TestParam tp = new TestParam();

        tp.delta = 0.001d;
        tp.rate = 0.05d;
        tp.batchSize = 500;
        tp.epoch = 1;
        tp.maxAdjustCount = 501;

        tp.trainingFile = "Network/digit/nts_digit_tst.txt";
        tp.testFile = "Network/digit/nts_digit_tst.txt";
        tp.compareDelta = 0.1d;
        tp.logType = 1;

        tp.xNumber = 28*28;
        tp.cf = cf;
        tp.preType = new DigitPreType();
        tp.neuronNumOfLayers = new int[]{16,16,10};
        tp.afs = new ArrayList<>(tp.neuronNumOfLayers.length);

        for(int i=0;i<tp.neuronNumOfLayers.length;i++){
            if(i != tp.neuronNumOfLayers.length-1){
                tp.afs.add(new ReluFunction());
            }else{
                tp.afs.add(new SoftmaxFunction());
            }
        }

        testTrain(tp);
    }

    public void preTrain(){
        int num = 786;


        for(int i=0;i<500;i++){
            String s = "";
            for(int j=0;j<num;j++) {
                //s += NumUtil.random(0,1);
                s += PubUtil.randomInt(256);
                if(j!=num-1){
                    s+=",";
                }
            }
            s += ":1,0,0,0,0,0,0,0,0,0";
            System.out.println(s);
        }

    }

    public static void main(String[] args) throws Exception{
        String fileName = "C:\\WorkFolder\\workspace\\EAI\\Perceptron\\src\\test\\resources\\Network\\digit\\";

        //String[] files = new String[] {"train-images.idx3-ubyte","train-labels.idx1-ubyte"};
        String[] files = new String[] {"t10k-images.idx3-ubyte","t10k-labels.idx1-ubyte"};


        String outputFile = "nts_digit_tst.txt";
        BinaryFile tFile = new BinaryFile(fileName+files[0]);
        BinaryFile lFile = new BinaryFile(fileName+files[1]);
        tFile.readNext(16);
        lFile.readNext(8);

        int count = 0;
        BufferedWriter bw = newFile(fileName+outputFile);

        while(true){

            byte[] ts = tFile.readNext(28*28);
            byte[] ls = lFile.readNext(1);
            if(ts == null && ls == null){
                break;
            }
            count ++;
            writeLine(bw,toString(ts,ls));
            if(count > 200){
                break;
            }
            break;
        }
        bw.close();

    }

    private static String toString(byte[] ts, byte[] ls){
        String s = "";
        for(int i=0;i<ts.length;i++){
            s += Byte.toUnsignedInt(ts[i]);
            if(i != ts.length-1)
                s+=",";
        }
        s += ":"+ls[0];
        return s;
    }

    private static void writeLine(BufferedWriter bw, String s) throws  Exception{
        bw.write(s);
        bw.newLine();
    }

    private static BufferedWriter newFile(String file) throws Exception{
        BufferedWriter bw = null;
        OutputStreamWriter osw;
        osw = new OutputStreamWriter(new FileOutputStream(file),"utf-8");

        bw = new BufferedWriter(osw);
        return bw;
    }
}