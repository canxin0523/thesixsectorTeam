package cc.mrbird.febs.common.function;

import cc.mrbird.febs.common.exception.RedisConnectException;

@FunctionalInterface
public interface JedisExecutor<T, R> {
    R excute(T t) throws RedisConnectException;
}
