package cn.com.mockingbird.robin.quartz.common;

import lombok.Getter;

/**
 * 执行结果枚举
 *
 * @author zhaopeng
 * @date 2023/10/1 19:34
 **/
@Getter
public enum ResultEnum {

    SUCCESS(1, "成功"),
    FAILURE(2, "失败")
    ;

    private final int result;

    private final String description;

    ResultEnum(int result, String description) {
        this.result = result;
        this.description = description;
    }

}
