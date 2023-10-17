package cn.com.mockingbird.robin.common.util;

/**
 * IF-ELSE 分支函数式处理接口
 *
 * @author zhaopeng
 * @date 2023/10/5 20:28
 **/
@FunctionalInterface
public interface TrueOrFalseHandler {
    /**
     * true or false 处理接口
     *
     * @param trueHandler true 执行的处理器
     * @param falseHandler false 执行的处理器
     */
    void trueOrFalseHandle(Runnable trueHandler, Runnable falseHandler);
}
