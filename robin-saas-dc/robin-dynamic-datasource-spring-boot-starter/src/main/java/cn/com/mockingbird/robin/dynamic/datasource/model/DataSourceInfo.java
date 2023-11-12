package cn.com.mockingbird.robin.dynamic.datasource.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * 数据源
 *
 * @author zhaopeng
 * @date 2023/11/7 22:51
 **/
@Getter
@Setter
@Builder
public class DataSourceInfo {

    /**
     * 数据源唯一标识
     */
    private String key;

    /**
     * 数据库驱动
     */
    private String driverClassName;

    /**
     * 数据库地址
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DataSourceInfo that) {
            return Objects.equals(this.key, that.key) &&
                   Objects.equals(this.driverClassName, that.driverClassName) &&
                   Objects.equals(this.url, that.url) &&
                   Objects.equals(this.username, that.username) &&
                   Objects.equals(this.password, that.password);
        } else {
            return false;
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

}
