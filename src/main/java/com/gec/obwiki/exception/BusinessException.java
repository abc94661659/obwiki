package com.gec.obwiki.exception;

import lombok.Data;

// 继承RuntimeException以实现异常抛出
@Data
public class BusinessException extends RuntimeException {
    //业务异常消息
    private BusinessExceptionCode code;

    public BusinessException (BusinessExceptionCode code) {
        // 调用父类构造函数传入错误信息
        super(code.getDesc());
        this.code = code;
    }

    /**
     * 不写入堆栈信息，提高性能
     */
    @Override
    public Throwable fillInStackTrace() {
        // 返回当前对象作为异常堆栈信息
        return this;
    }
}