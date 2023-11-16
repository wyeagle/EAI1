package com.wang.study.ai.network;

import com.wang.study.ai.util.NumUtil;

import java.util.ArrayList;
import java.util.List;

public class WorkMemory {
    double _lastDiff = 0;
    long _adjustCount;
    double _avgDiff = 0;
    double _delta;

    long _maxAdjustCount = 10000l;
    private static int NUM = 100;

    boolean canAbort(){
        if(_adjustCount >= _maxAdjustCount){
            System.out.println("adjustCount = "+_adjustCount);
            return true;
        }
        if(_keep >= 100){
            System.out.println("_keep = "+_keep);
            return true;
        }
        return false;
    }

    void addAdjustCount(){
        _adjustCount++;
    }

    int _keep = 0;
    void addDiff(double diff,int count){

        if(NumUtil.isEqual(_lastDiff,diff,0.00000001)) {
            //System.out.println(_lastDiff+":"+diff+":"+_delta);
            //_keep ++;
        }else{
            _keep = 0;
        }
        _lastDiff = diff;
    }


    void clear(){
        _adjustCount = 0;
    }
}
