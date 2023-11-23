package com.wang.study.ai.network.result;

import com.wang.study.ai.network.fc.Network;
import com.wang.study.ai.network.fc.NetworkUtil;

public class EpochResult {
    public int _epochNo;
    public double _diff;
    public long _time;
    public long _adjustCount;
    public String _networkJson;

    long _point;

    public EpochResult(int epochNo){
        _epochNo = epochNo;

    }

    public void addResult(long adjustCount,double diff, long time, Network network){
        _adjustCount = adjustCount;
        _diff = diff;
        _time = time;
        _networkJson = NetworkUtil.network2Json(network);
        calcPoint();
    }

    private void calcPoint(){
        //diff越小分数越高
        double point = 100000/Math.abs(_diff)+(_time/1000*60*60);

        _point = Math.round(point);
    }

    long getPoint(){
        return _point;
    }

    @Override
    public String toString() {
        return "EpochResult{" +
                "_epochNo=" + _epochNo +
                ", _diff=" + _diff +
                ", _time=" + _time +
                ", _adjustCount=" + _adjustCount +
                ", _networkJson='" + _networkJson + '\'' +
                ", _point=" + _point +
                '}';
    }
}
