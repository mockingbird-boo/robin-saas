package cn.com.mockingbird.robin.mybatis.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 范围值封装类
 *
 * @author zhaopeng
 * @date 2023/10/12 11:00
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Range<T> {

    /**
     * 上限
     */
    private T upper;

    /**
     * 下限
     */
    private T lower;

}
