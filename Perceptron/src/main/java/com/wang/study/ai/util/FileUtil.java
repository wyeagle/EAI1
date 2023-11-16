package com.wang.study.ai.util;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class FileUtil {
    public static Map<String, Object> readConfig(String file) {

        Map<String, Object> configMap = new HashMap<String, Object>();
        ResourceBundle resources = ResourceBundle.getBundle(file);
        Enumeration<String> enuKey = resources.getKeys();

        while (enuKey.hasMoreElements()) {
            String strKey = (String) enuKey.nextElement();
            String strValue = resources.getString(strKey);
            int index = strValue.indexOf("#");
            if (index != -1) {
                strValue = strValue.substring(0, index).trim();
            }
            configMap.put(strKey, strValue);
        }
        return configMap;

    }

    public static String file2Str(String file) throws Exception{
        StringBuffer str = new StringBuffer();
        BufferedReader br = null;
        InputStreamReader isr;
        isr = new InputStreamReader(new FileInputStream(file),"utf-8");
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

    public static void str2File(String file,String str) throws Exception{
        BufferedWriter bw = null;
        OutputStreamWriter osw;
        osw = new OutputStreamWriter(new FileOutputStream(file),"utf-8");

        bw = new BufferedWriter(osw);

        bw.write(str);

        bw.close();
        osw.close();
    }

    public static void main(String[] args) throws Exception{
        FileUtil.str2File("1234",System.currentTimeMillis()+".txt");
    }


}
