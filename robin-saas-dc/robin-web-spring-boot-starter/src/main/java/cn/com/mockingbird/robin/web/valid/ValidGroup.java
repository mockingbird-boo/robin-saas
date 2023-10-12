package cn.com.mockingbird.robin.web.valid;

import jakarta.validation.groups.Default;
import org.springframework.validation.annotation.Validated;

/**
 * 分组校验接口
 * <p>
 * 继承 {@link Default} 的目的是使校验注解上没有通过 group 属性指定校验分组的字段
 * 也能在 {@link Validated} 注解指定了校验分组的情况下进行校验
 * @see Demo 例子
 *
 * @author zhaopeng
 * @date 2023/10/11 23:56
 **/
@SuppressWarnings("unused")
public interface ValidGroup extends Default {

    interface Crud extends ValidGroup {

        interface Create extends Crud {

        }

        interface Read extends Crud {

        }

        interface Update extends Crud {

        }

        interface Delete extends Crud {

        }

    }

}
