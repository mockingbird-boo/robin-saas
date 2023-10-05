package cn.com.mockingbird.robin.common.util;

import java.util.function.Consumer;

/**
 * 存在或不存在函数式处理接口
 *
 * @author zhaopeng
 * @date 2023/10/5 20:32
 **/
@FunctionalInterface
public interface PresentOrElseHandler<T> {

    /**
     * 存在或不存在处理接口
     * @param consumer 值不为空时执行消费操作
     * @param nullAction 值为空时执行其他的操作
     */
    void presentOrElseHandle(Consumer<? super T> consumer, Runnable nullAction);

}
