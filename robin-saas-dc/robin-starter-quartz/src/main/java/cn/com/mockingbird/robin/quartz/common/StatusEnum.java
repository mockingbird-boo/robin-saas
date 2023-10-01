package cn.com.mockingbird.robin.quartz.common;

import lombok.Getter;

/**
 * 任务状态枚举
 *
 * @author zhaopeng
 * @date 2023/10/1 19:34
 **/
@Getter
public enum StatusEnum {

    RUN(1, "运行"),
    STOP(2, "暂停"),
    DELETED(3, "删除")
    ;

    private final int status;

    private final String description;

    StatusEnum(int status, String description) {
        this.status = status;
        this.description = description;
    }

}
