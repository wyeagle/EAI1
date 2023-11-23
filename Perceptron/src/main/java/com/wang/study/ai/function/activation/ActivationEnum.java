package com.wang.study.ai.function.activation;

public enum ActivationEnum {
    LeakyRelu("1"),
    Relu("2"),
    Sigmoid("3"),
    Sign("4"),
    Softmax("5");

    private final String code;

    private ActivationEnum(String code) {
        this.code = code;
    }
    public String getCode() {
        return this.code;
    }
}
