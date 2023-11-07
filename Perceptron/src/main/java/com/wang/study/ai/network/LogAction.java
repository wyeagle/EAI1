package com.wang.study.ai.network;


import com.wang.study.ai.util.LocalUtil;

public class LogAction {
    public static void logFirstAdjustW(Network network){
        if(network._memory._adjustCount != 1){
            return;
        }

        boolean isUT = LocalUtil.isUT();
        if(!isUT){
            return;
        }

        NetworkConfig config = NetworkUtil.network2config(network);
        LocalUtil.add("NETWORK",config);
    }

    public static void logIncreaseW(Network network){

    }
}
