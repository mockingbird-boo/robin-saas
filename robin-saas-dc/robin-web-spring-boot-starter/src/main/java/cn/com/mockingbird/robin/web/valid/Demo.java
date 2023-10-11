package cn.com.mockingbird.robin.web.valid;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * JSR-303 校验 DEMO
 * <p>
 * 例如在新增接口中使用 {@link Validated} 注解时，可以如下编写代码：
 * “ create(@Validated(value = ValidGroup.Crud.Create.class) Demo demo) ”
 *
 * @author zhaopeng
 * @date 2023/10/12 2:13
 **/
@Data
public class Demo {

    @Null(groups = ValidGroup.Crud.Create.class)
    @NotNull(groups = ValidGroup.Crud.Update.class, message = "更新操作，id 不能为空")
    @NotNull(groups = ValidGroup.Crud.Delete.class, message = "删除操作，id 不能为空")
    private Long id;

    @Null(groups = ValidGroup.Crud.Create.class)
    @NotNull(groups = ValidGroup.Crud.Update.class, message = "更新操作，name 不能为空")
    private String name;

    @Email(message = "邮箱地址有误，请检查")
    private String email;

    @EnumString(value = {"F", "M"}, groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class},
    message = "性别编码只允许为 F 或者 M")
    @NotNull(groups = ValidGroup.Crud.Create.class, message = "新增操作，性别编码不能为空")
    private String gender;

}
