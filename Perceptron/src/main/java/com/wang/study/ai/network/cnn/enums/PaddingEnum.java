package com.wang.study.ai.network.cnn.enums;

public enum PaddingEnum {
    VALID("1"),
    SAME("2");

    private final String code;

    private PaddingEnum(String code) {
        this.code = code;
    }
    public String getCode() {
        return this.code;
    }
}
