package com.whz.exception;

import com.whz.utils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 如果抛出的的是ServiceException，则调用该方法
     * @param e 业务异常
     * @return R
     */
    @ExceptionHandler(ServiceException.class)
    public R handle(ServiceException e){
        R r = new R();
        r.put("code",e.getCode());
        r.put("msg",e.getMessage());
        return r;
    }

    @ExceptionHandler(Exception.class)
    public R handle(Exception e){
        e.printStackTrace();
        R r = new R();
        r.put("code",500);
        r.put("msg","服务器内部出错");
        return r;
    }

}
