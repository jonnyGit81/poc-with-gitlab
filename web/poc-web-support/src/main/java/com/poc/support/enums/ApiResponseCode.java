package com.poc.support.enums;


import lombok.Getter;

@Getter
public enum ApiResponseCode {
    OK("S", "SUCCESS", "process has been successful"),
    ERROR("U", "SYSTEM_ERROR", "We are sorry. System was offline, please try again in a few moment"),
    FAILED("F", "FAILED","Failed to process, please retry.");

    private String resultCodeId;
    private String resultCode;
    private String remarks;

    ApiResponseCode(String resultCodeId, String resultCode, String message) {
        this.resultCodeId = resultCodeId;
        this.resultCode = resultCode;
        this.remarks = message;
    }
}
