package cn.com.mockingbird.robin.web.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 枚举值校验器
 *
 * @author zhaopeng
 * @date 2023/10/12 0:22
 **/
public class EnumStringValidator implements ConstraintValidator<EnumString, String> {
    
    private List<String> enumValues;

    @Override
    public void initialize(EnumString constraintAnnotation) {
        this.enumValues = Stream.of(constraintAnnotation.value()).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return enumValues.contains(value);
    }
}
