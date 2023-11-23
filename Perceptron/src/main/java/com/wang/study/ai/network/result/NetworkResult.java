package com.wang.study.ai.network.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NetworkResult {
    private List<EpochResult> _eporchResults = new ArrayList<>();

    public double _avgTime;
    public double _aveDiff;
    public long _totalTime;
    public double _totalDiff;
    public long _totalAdjustCount;


    public void add(EpochResult result){
        _eporchResults.add(result);
        _totalTime += result._time;
        _totalDiff += result._diff;
        _totalAdjustCount += result._adjustCount;
    }

    public EpochResult bestResult(){
        Collections.sort(_eporchResults, Comparator.comparingLong(EpochResult::getPoint).reversed());
        return _eporchResults.get(0);
    }

    public List<EpochResult> getEporchResults(){
        return _eporchResults;
    }
}
