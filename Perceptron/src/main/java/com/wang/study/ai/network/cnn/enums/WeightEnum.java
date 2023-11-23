package com.wang.study.ai.network.cnn.enums;

public enum WeightEnum {
    HE("1");

    private final String code;

    private WeightEnum(String code) {
        this.code = code;
    }
    public String getCode() {
        return this.code;
    }
}
