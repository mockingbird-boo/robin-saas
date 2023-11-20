package cn.com.mockingbird.robin.web.desensitize;

import cn.com.mockingbird.robin.common.util.BranchUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * 脱敏序列化器
 *
 * @author zhaopeng
 * @date 2023/11/21 0:50
 **/
@NoArgsConstructor
@AllArgsConstructor
public class DesensitizeSerializer extends JsonSerializer<String> implements ContextualSerializer {

    /**
     * 脱敏类型
     */
    private DesensitizeType type;

    /**
     * 前置保留长度
     */
    private int prefix;

    /**
     * 后置保留长度
     */
    private int suffix;

    /**
     * 打码符号，默认：“*”
     */
    private String mark;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) {
        if (StringUtils.isNotBlank(value) && null != type) {
            BranchUtils.isTureOrFalse(type == DesensitizeType.CUSTOMIZED).trueOrFalseHandle(
                () -> {
                    try {
                        gen.writeString(DesensitizeType.desensitize(value, prefix, suffix, mark));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                },() -> {
                    try {
                        gen.writeString(type.getDesensitizer().apply(value));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            );
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (property == null) {
            return prov.findNullValueSerializer(null);
        }
        if (Objects.equals(property.getType().getRawClass(), String.class)) {
            Desensitize desensitize = property.getAnnotation(Desensitize.class);
            if (desensitize == null) {
                desensitize = property.getContextAnnotation(Desensitize.class);
            }
            if (desensitize != null) {
                return new DesensitizeSerializer(desensitize.type(), desensitize.prefix(), desensitize.suffix(), desensitize.mark());
            }
        }
        return prov.findValueSerializer(property.getType(), property);
    }
}
