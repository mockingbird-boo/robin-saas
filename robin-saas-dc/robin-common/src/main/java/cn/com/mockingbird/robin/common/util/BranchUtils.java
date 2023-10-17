package cn.com.mockingbird.robin.common.util;

import java.util.Objects;

/**
 * 分支处理工具类
 *
 * @author zhaopeng
 * @date 2023/10/5 20:31
 **/
public final class BranchUtils {

    /**
     * if or else 处理
     * @param value 布尔结果
     * @return if or else 处理逻辑
     */
    public static TrueOrFalseHandler isTureOrFalse(boolean value) {
        return ((trueHandler, falseHandler) -> {
            if (value) {
                trueHandler.run();
            } else {
                falseHandler.run();
            }
        });
    }

    /**
     * 存在（非空）or 不存在（空）处理
     * @param object 判断主体
     * @return {@link PresentOrElseHandler} 处理逻辑
     */
    @SuppressWarnings("unused")
    public static PresentOrElseHandler<?> nonNullOrElse(Object object) {
        return ((consumer, nullAction) ->
                isTureOrFalse(Objects.nonNull(object)).trueOrFalseHandle(
                        () -> consumer.accept(object), nullAction));
    }

    /**
     * 存在（非空）处理
     * @param object 判断主体
     * @return {@link PresentHandler} 处理逻辑
     */
    public static PresentHandler<?> nonNull(Object object) {
        return consumer -> {
            if (Objects.nonNull(object)) {
                consumer.accept(object);
            }
        };
    }

    /**
     * if or else 处理，带返回结果
     * @param value 判断主体
     * @return 处理逻辑的结果值
     * @param <T> 结果泛型
     */
    public static <T> TrueOrFalseWithReturnHandler<T> isTureOrFalseWithReturn(boolean value) {
        return ((trueHandler, falseHandler) -> {
            if (value) {
                return trueHandler.get();
            } else {
                return falseHandler.get();
            }
        });
    }

}
