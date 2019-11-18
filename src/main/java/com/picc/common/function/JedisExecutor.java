package com.picc.common.function;


import com.picc.common.exception.RedisConnectException;

/**
 * @author lhx
 */
@FunctionalInterface
public interface JedisExecutor<T, R> {
    R excute(T t) throws RedisConnectException;
}
