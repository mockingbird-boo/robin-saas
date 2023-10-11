package cn.com.mockingbird.robin.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * 分页数据模型
 *
 * @author zhaopeng
 * @date 2023/10/6 10:04
 **/
@SuppressWarnings("unused")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageData<T> {

    /**
     * 分页数据
     */
    private List<T> data;

    /**
     * 总数据量
     */
    private Long total;

    /**
     * 分页数
     */
    private Long pages;

    /**
     * 获取一个空页实例
     * @return 空页实例
     * @param <T> 分页数据泛型
     */
    public static <T> PageData<T> empty() {
        PageData<T> pageData = new PageData<>();
        pageData.setData(Collections.emptyList());
        pageData.setTotal(0L);
        pageData.setPages(0L);
        return pageData;
    }

}
