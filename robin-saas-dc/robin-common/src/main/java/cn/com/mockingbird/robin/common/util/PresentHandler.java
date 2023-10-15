package cn.com.mockingbird.robin.common.util;

import java.util.function.Consumer;

/**
 * 存在时函数式处理接口
 *
 * @author zhaopeng
 * @date 2023/10/15 19:53
 **/
@FunctionalInterface
public interface PresentHandler<T> {

    /**
     * 存在时即处理接口
     * @param consumer 处理逻辑
     */
    void presentHandle(Consumer<? super T> consumer);

}
