package cn.com.mockingbird.robin.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 安全的响应数据
 *
 * @author zhaopeng
 * @date 2023/11/20 18:08
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SafeResponseData {

    private String key;

    private String data;

}
