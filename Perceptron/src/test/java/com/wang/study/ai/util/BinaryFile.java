package com.wang.study.ai.util;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.Arrays;

public class BinaryFile {
    private String _file ;
    private boolean isInit = true;
    private FileInputStream _fis;

    public BinaryFile(String file){
        _file = file;
    }

    public byte[] readNext(int size) throws Exception{
        if(isInit){
            init();
        }
        byte[] bts  = new byte[size];
        int n = _fis.read(bts);
        if(n <= 0){
            _fis.close();
            return null;
        }

        bts = Arrays.copyOf(bts, n);

        return bts;
    }

    private void init() throws Exception{
        _fis = new FileInputStream(_file);
        isInit = false;
    }



}
