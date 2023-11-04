package com.wang.study.ai.network;

import java.util.ArrayList;
import java.util.List;

public class WorkMemory {
    private List<Double> _oldDiffs = new ArrayList<Double>();
    double _lastDiff;
    long _adjustCount;

    boolean canAbort(){
        if(_oldDiffs.size() >= 1000 || _adjustCount >= 2000000){
            System.out.println("oldDiff size = "+_oldDiffs.size()+": adjustCount = "+_adjustCount);
            return true;
        }
        return false;
    }

    void addAdjustCount(){
        _adjustCount++;
    }

    void addDiff(double diff){
        _lastDiff = diff;
        double last = 0d;
        if(_oldDiffs.size()>0){
            last = _oldDiffs.get(_oldDiffs.size()-1);
        }
        if(diff < last){
            _oldDiffs.remove(last);
        }
        _oldDiffs.add(diff);
    }

    void clearDiff(){
        _oldDiffs.clear();
    }

    void clear(){
        clearDiff();
        _adjustCount = 0;
    }
}
