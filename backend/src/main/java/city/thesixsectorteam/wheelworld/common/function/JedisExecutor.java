package city.thesixsectorteam.wheelworld.common.function;

import city.thesixsectorteam.wheelworld.common.exception.RedisConnectException;

@FunctionalInterface
public interface JedisExecutor<T, R> {
    R excute(T t) throws RedisConnectException;
}
