package com.wang.study.ai.common;

public enum TrainStatus {

    TRAIN_WAITING("1"),
    TRAIN_RUNNING("5"),
    TRAIN_BATCH_SUCCESS("90"),
    TRAIN_SUCCESS("99"),
    TRAIN_BATCH_FAILED("-90"),
    TRAIN_FAILED("-99");

    private final String code;

    private TrainStatus(String code) {
        this.code = code;
    }
    public String getCode() {
        return this.code;
    }
}
