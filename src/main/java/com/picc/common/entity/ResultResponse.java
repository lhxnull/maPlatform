package com.picc.common.entity;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @author lhx
 */
public class ResultResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;
    private static final String SUCCESS="0";

    private static final String ERROR="1";

    private static final String  PARAMEXCEPTION ="参数异常";

    private static final String SERVICEEXCEPTION="服务异常";

    private static final String SYSTEMEXCEPTION="系统异常";

    public ResultResponse code(HttpStatus status) {
        this.put("code", status.value());
        return this;
    }

    public ResultResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public ResultResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    public ResultResponse success() {
        this.code(HttpStatus.OK);
        return this;
    }
    public ResultResponse error() {
        this.code(HttpStatus.BAD_REQUEST);
        return this;
    }

    public ResultResponse fail() {
        this.code(HttpStatus.INTERNAL_SERVER_ERROR);
        return this;
    }

    @Override
    public ResultResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
