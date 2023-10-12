package cn.com.mockingbird.robin.mybatis.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 查询条件封装类
 *
 * @author zhaopeng
 * @date 2023/10/12 22:09
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueryCondition {

    private String column;

    private Object value;

}
