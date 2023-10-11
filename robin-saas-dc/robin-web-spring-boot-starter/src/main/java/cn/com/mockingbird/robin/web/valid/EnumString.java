package cn.com.mockingbird.robin.web.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 枚举值自定义注解
 *
 * @author zhaopeng
 * @date 2023/10/12 0:09
 **/
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(EnumString.List.class)
@Constraint(validatedBy = EnumStringValidator.class)
@Documented
public @interface EnumString {

    String message() default "value not in enum values.";

    Class<?>[] groups() default {};

    String[] value();

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@link EnumString} annotations on the same element.
     * @see EnumString
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        EnumString[] value();
    }

}
