package com.zoran.gulimallmember.exception;


public class PhoneExistException extends RuntimeException {
    public PhoneExistException() {
        super("手机号已注册");
    }
}
