package com.wang.study.ai.util;

import com.wang.study.ai.data.TrainingData;
import com.wang.study.ai.data.TrainingSet;
import com.wang.study.ai.data.ptype.PreType;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static String file2Str(String file) throws Exception{
        StringBuffer str = new StringBuffer();
        BufferedReader br = null;
        InputStreamReader isr;
        isr = new InputStreamReader(new FileInputStream(convertFile(file)),"utf-8");
        String b = null;
        br = new BufferedReader(isr);

        while ((b = br.readLine()) != null) {
            str.append(b);
            str.append("\n");
        }
        br.close();
        isr.close();
        return str.toString();
    }

    /**
     * 0,2,1,2:1
     * 1,3,1,3:1
     * @param file
     * @return
     * @throws Exception
     */
    public static TrainingSet file2Ts(PreType preType,String file) throws Exception{

        BufferedReader br = null;
        InputStreamReader isr;
        isr = new InputStreamReader(new FileInputStream(convertFile(file)),"utf-8");
        String b = null;
        br = new BufferedReader(isr);

        TrainingSet trainingSet = new TrainingSet(preType);
        TrainingData data = null;

        boolean noteFlag = false;
        while ((b = br.readLine()) != null) {

            b=b.trim();
            if(PubUtil.isEmpty(b)){
                continue;
            }
            if(b.indexOf("*/") >= 0){
                noteFlag = false;
                continue;
            }
            if(b.indexOf("#") == 0 || noteFlag){
                continue;
            }
            if(b.indexOf("/**") >= 0){
                noteFlag = true;
                continue;
            }

            //b的格式 0,2,1,2:1
            List<String> strs = StringUtil.split(b,":");
            if(strs.size() != 2){
                System.err.println("format error = "+b);
                continue;
            }
            data = new TrainingData();
            try {
                data.x = NumUtil.array2double(StringUtil.split(strs.get(0), ","));
                data.expectedValues = NumUtil.array2double(StringUtil.split(strs.get(1), ","));
            }catch (Exception e){
                System.err.println("parse file error = "+b);
                continue;
            }
            trainingSet.addData(data);

        }
        br.close();
        isr.close();



        return trainingSet;
    }

    private static String convertFile(String file){
        String basePath = Paths.get(System.getProperty("user.dir")).toAbsolutePath().toString();
        String str = basePath+"\\src\\test\\resources\\";
        return str+file;
    }

    public static void main(String[] args){
        Path basePath = Paths.get(System.getProperty("user.dir"));
        System.out.println(basePath.toAbsolutePath());

    }
}
