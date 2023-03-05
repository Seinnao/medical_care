package com.whz.exception;

import lombok.Getter;

/**
 * 自定义异常
 */
@Getter
public class ServiceException extends RuntimeException {

    private int code = 500;

    public ServiceException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public ServiceException(String msg){
        super(msg);
    }

}
