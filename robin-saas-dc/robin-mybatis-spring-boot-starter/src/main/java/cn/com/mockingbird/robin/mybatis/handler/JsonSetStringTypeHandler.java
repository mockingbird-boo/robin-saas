package cn.com.mockingbird.robin.mybatis.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;

import java.util.Set;

/**
 * Set<String> 类型转换器
 *
 * @author zhaopeng
 * @date 2023/10/6 15:38
 **/
public class JsonSetStringTypeHandler extends FastjsonTypeHandler {

    public JsonSetStringTypeHandler(Class<?> type) {
        super(type);
    }

    /**
     * json 字符串转 Set<String>
     * 重写该方法的原因是因为顶层父类是无法获取到准确的待转换复杂返回类型数据
     *
     * @param json json 字符串
     * @return Set<String>
     */
    @Override
    protected Object parse(String json) {
        return JSON.parseObject(json, new TypeReference<Set<Long>>(){
        });
    }
}
