package cn.com.mockingbird.robin.web.model;

import lombok.Data;

/**
 * 分页请求参数模型
 *
 * @author zhaopeng
 * @date 2023/10/6 10:17
 **/
@SuppressWarnings("unused")
@Data
public class PageParams {

    private static final int DEFAULT_PAGE_NO = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;

    private Integer pageNo = DEFAULT_PAGE_NO;

    private Integer pageSize = DEFAULT_PAGE_SIZE;

    public PageParams() {

    }

    public PageParams(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

}
