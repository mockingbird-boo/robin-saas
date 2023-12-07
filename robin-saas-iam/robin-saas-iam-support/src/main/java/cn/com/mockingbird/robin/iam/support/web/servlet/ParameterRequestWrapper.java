package cn.com.mockingbird.robin.iam.support.web.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.*;

/**
 * 请求参数包装工具
 *
 * @author zhaopeng
 * @date 2023/12/7 19:38
 **/
public class ParameterRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String[]> params;

    public ParameterRequestWrapper(HttpServletRequest request) {
        super(request);
        this.params = new HashMap<>(16);
        this.params.putAll(request.getParameterMap());
    }

    public ParameterRequestWrapper(HttpServletRequest request, Map<String, Object> extendParams) {
        this(request);
        this.addAllParameters(extendParams);
    }

    public Enumeration<String> getParameterNames() {
        return (new Vector(this.params.keySet())).elements();
    }

    public String getParameter(String name) {
        String[] values = this.params.get(name);
        return values != null && values.length != 0 ? values[0] : null;
    }

    public String[] getParameterValues(String name) {
        String[] values = this.params.get(name);
        return values != null && values.length != 0 ? values : null;
    }

    public void addAllParameters(Map<String, Object> otherParams) {
        for (Map.Entry<String, Object> stringObjectEntry : otherParams.entrySet()) {
            this.addParameter(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        }
    }
    public void addParameter(String name, Object value) {
        if (value != null) {
            if (value instanceof String[]) {
                this.params.put(name, (String[])value);
            } else if (value instanceof String) {
                this.params.put(name, new String[]{(String)value});
            } else {
                this.params.put(name, new String[]{String.valueOf(value)});
            }
        }
    }

}
