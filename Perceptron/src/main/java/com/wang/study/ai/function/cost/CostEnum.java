package com.wang.study.ai.function.cost;

public enum CostEnum {
    BCE("1"),
    MSE("2");

    private final String code;

    private CostEnum(String code) {
        this.code = code;
    }
    public String getCode() {
        return this.code;
    }
}
