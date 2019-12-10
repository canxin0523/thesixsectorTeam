package city.sixrealms.wheelworld.common.function;

@FunctionalInterface
public interface CacheSelector<T> {
    T select() throws Exception;
}
