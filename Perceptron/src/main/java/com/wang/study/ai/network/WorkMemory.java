package com.wang.study.ai.network;

import com.wang.study.ai.util.NumUtil;


public class WorkMemory {
    public double _lastDiff = 0;
    public long _adjustCount;
    public double _delta;

    public long _maxAdjustCount = 10000l;
    private static int NUM = 100;

    public boolean canAbort(){
        if(_adjustCount >= _maxAdjustCount){
            return true;
        }
        if(_keep >= 100){
            System.out.println("_keep = "+_keep);
            return true;
        }
        return false;
    }

    public void addAdjustCount(){
        _adjustCount++;
    }

    int _keep = 0;
    public void addDiff(double diff,int count){

        if(NumUtil.isEqual(_lastDiff,diff,0.00000001)) {
            //System.out.println(_lastDiff+":"+diff+":"+_delta);
            //_keep ++;
        }else{
            _keep = 0;
        }
        _lastDiff = diff;
    }


    public void clear(){
        _adjustCount = 0;
    }
}
