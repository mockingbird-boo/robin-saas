package cn.com.mockingbird.robin.common.trace;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.Objects;

/**
 * 时长追踪类
 * 该类中定义了两个函数式接口
 *
 * @author zhaopeng
 * @date 2023/10/3 0:17
 **/
public class TraceWatch {

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
     * 时长监控类
     * @param <T>
     */
    @Getter
    @ToString
    public static class Watch<T> {
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
