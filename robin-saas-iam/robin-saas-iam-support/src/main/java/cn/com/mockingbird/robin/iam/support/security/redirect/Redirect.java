package cn.com.mockingbird.robin.iam.support.security.redirect;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 重定向模型
 *
 * @author zhaopeng
 * @date 2023/12/6 1:20
 **/
@Getter
@Setter
public class Redirect implements Serializable {

    @Serial
    private static final long serialVersionUID = -5115686842027116610L;

    private String action;

    private String method;

    private List<Parameter> parameters;

    public Redirect() {
        this.method = HttpMethod.GET.name();
        this.parameters = new ArrayList<>();
    }


}
