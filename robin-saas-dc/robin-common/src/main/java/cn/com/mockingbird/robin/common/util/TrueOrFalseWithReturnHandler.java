package cn.com.mockingbird.robin.common.util;

import java.util.function.Supplier;

/**
 * IF-ELSE 带返回值分支函数式处理接口
 *
 * @author zhaopeng
 * @date 2023/10/17 22:41
 **/
@FunctionalInterface
public interface TrueOrFalseWithReturnHandler<T> {
    /**
     * true or false 处理接口
     *
     * @param trueHandler true 执行的处理器
     * @param falseHandler false 执行的处理器
     * @return 处理结果
     */
    T trueOrFalseHandle(Supplier<T> trueHandler, Supplier<T> falseHandler);

}
