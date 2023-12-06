package cn.com.mockingbird.robin.iam.support.security.redirect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 参数模型
 *
 * @author zhaopeng
 * @date 2023/12/6 1:38
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parameter implements Serializable {

    @Serial
    private static final long serialVersionUID = 6917414955314375052L;

    private String key;

    private String value;

}
