package com.picc.common.exception;

/**
 * 系统内部异常
 *
 * @author lhx
 */
public class MapPlatformException extends RuntimeException  {

    private static final long serialVersionUID = -994962710559017255L;

    public MapPlatformException(String message) {
        super(message);
    }
}
