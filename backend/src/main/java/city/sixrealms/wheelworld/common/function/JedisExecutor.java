package city.sixrealms.wheelworld.common.function;

import city.sixrealms.wheelworld.common.exception.RedisConnectException;

@FunctionalInterface
public interface JedisExecutor<T, R> {
    R excute(T t) throws RedisConnectException;
}
