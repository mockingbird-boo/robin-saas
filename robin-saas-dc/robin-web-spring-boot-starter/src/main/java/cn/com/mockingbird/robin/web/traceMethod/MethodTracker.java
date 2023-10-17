package cn.com.mockingbird.robin.web.traceMethod;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.Objects;

/**
 * 方法追踪器
 * <p>
 * 该追踪器中定义了两个函数式接口：
 * Supplier<T, E extends Throwable> 用于追踪无参有返回值方法的执行时长和返回值结果
 * Function<E extends Throwable> 用于追踪无参无返回值方法的执行时长
 * 该追踪器中还定义了一个内部类：TrackingData<T>，用于封装追踪数据
 *
 * @author zhaopeng
 * @date 2023/10/3 0:17
 **/
@SuppressWarnings("unused")
public class MethodTracker {

    /**
     * 无参有返回值方法追踪
     * @param supplier 无参有返回值接口
     * @param startMillis 开始时间戳
     * @return 追踪结果
     * @param <T> 结果泛型
     * @param <E> 异常泛型
     */
    public static <T, E extends Throwable> TrackingData<T> run(Supplier<T, E> supplier, long startMillis) {
        TrackingData<T> trackingData = new TrackingData<>();
        trackingData.start(startMillis);
        try {
            trackingData.record(supplier.get());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            trackingData.end();
        }
        return trackingData;
    }

    /**
     * 执行方法
     * @param supplier 无参有返回值接口
     * @return 追踪结果
     * @param <T> 结果泛型
     * @param <E> 异常泛型
     */
    public static <T, E extends Throwable> TrackingData<T> run(Supplier<T, E> supplier) {
        TrackingData<T> trackingData = new TrackingData<>();
        trackingData.start();
        try {
            trackingData.record(supplier.get());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            trackingData.end();
        }
        return trackingData;
    }

    /**
     * 执行方法
     * @param function 无参无返回值接口
     * @param startMillis 开始时间戳
     * @return 追踪结果
     * @param <E> 异常泛型
     */
    public static <E extends Throwable> TrackingData<Void> run(Function<E> function, long startMillis) {
        TrackingData<Void> trackingData = new TrackingData<>();
        trackingData.start(startMillis);
        try {
            function.exec();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            trackingData.end();
        }
        return trackingData;
    }

    /**
     * 执行方法
     * @param function 无参无返回值接口
     * @return 追踪结果
     * @param <E> 异常泛型
     */
    public  static <E extends Throwable> TrackingData<Void> run(Function<E> function) {
        TrackingData<Void> trackingData = new TrackingData<>();
        trackingData.start();
        try {
            function.exec();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            trackingData.end();
        }
        return trackingData;
    }


    /**
     * 无参有返回值接口
     * @param <T>
     * @param <E>
     */
    @FunctionalInterface
    public interface Supplier<T, E extends Throwable> {
        T get() throws E;
    }

    /**
     * 无参无返回值接口
     */
    @FunctionalInterface
    public interface Function<E extends Throwable> {
        void exec() throws E;
    }

    /**
     * 追踪数据类
     * @param <T> 执行结果泛型
     */
    @Getter
    @ToString
    public static class TrackingData<T> {
        /**
         * 开始时间戳
         */
        private long startMillis;
        /**
         * 结束时间戳
         */
        private long endMillis;
        /**
         * 时长
         */
        private long duration;
        /**
         * 执行结果
         */
        private T result;

        private void start() {
            this.startMillis = Instant.now().toEpochMilli();
        }

        private void start(Long startMillis) {
            this.startMillis = Objects.requireNonNullElseGet(startMillis, () -> Instant.now().toEpochMilli());
        }

        private void end() {
            this.endMillis = Instant.now().toEpochMilli();
            this.duration = this.endMillis - this.startMillis;
        }

        private void record(T result) {
            this.result = result;
        }
    }

}
