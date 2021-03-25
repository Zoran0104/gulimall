package com.zoran.gulimallproduct.exception;

/**
 * @author ：zoran
 * @date ：Created in 2021/3/25 00:23
 * @description：
 * @modified By：
 * @version: 1.0
 */
public enum ConstantErrorCode {
    VALID_ERROR(505, "valid wrong"),
    NOT_KNOWN_ERROR(777, "unknown error");

    private int code;
    private String message;
    ConstantErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
