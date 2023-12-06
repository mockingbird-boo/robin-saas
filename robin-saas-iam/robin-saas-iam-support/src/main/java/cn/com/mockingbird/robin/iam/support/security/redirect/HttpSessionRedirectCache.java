package cn.com.mockingbird.robin.iam.support.security.redirect;

import cn.com.mockingbird.robin.iam.support.web.servlet.ServletContextHelp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.UrlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpSession 重定向缓存
 *
 * @author zhaopeng
 * @date 2023/12/6 2:08
 **/
@Slf4j
public class HttpSessionRedirectCache implements RedirectCache {

    private final Logger logger = LoggerFactory.getLogger(HttpSessionRedirectCache.class);

    @Override
    public void saveRedirect(HttpServletRequest request, HttpServletResponse response, RedirectType type) {
        switch (type) {
            case PARAMETER -> {
                String redirectUri = request.getParameter("redirect_uri");
                if (StringUtils.isNotBlank(redirectUri)) {
                    Redirect redirect = new Redirect();
                    int index = redirectUri.indexOf("?");
                    if (index > -1) {
                        redirect.setAction(redirectUri.substring(0, index));
                    } else {
                        redirect.setAction(redirectUri);
                    }
                    redirect.setParameters(getParameters(ServletContextHelp.getParameterForUrl(redirectUri)));
                    redirect.setMethod(HttpMethod.GET.name());
                    HttpSession session = request.getSession();
                    session.setAttribute(WEB_SECURITY_SAVED_REDIRECT, redirect);
                    logger.debug("Set session attribute [redirect_uri] for parameter");
                }
            }
            case REQUEST -> {
                Redirect redirect = new Redirect();
                redirect.setParameters(getParametersByArray(request.getParameterMap()));
                redirect.setMethod(redirect.getMethod());
                redirect.setAction(UrlUtils.buildFullRequestUrl(
                        request.getScheme(),
                        request.getServerName(),
                        request.getServerPort(),
                        request.getRequestURI(),
                        null));
                request.getSession().setAttribute(WEB_SECURITY_SAVED_REDIRECT, redirect);
                logger.debug("Set session attribute [redirect_uri] for request");
            }
        }
    }

    @Override
    public Redirect getRedirect(HttpServletRequest request, HttpServletResponse response) {
        return (Redirect) request.getSession().getAttribute(WEB_SECURITY_SAVED_REDIRECT);
    }

    @Override
    public void removeRedirect(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute(WEB_SECURITY_SAVED_REDIRECT);
        logger.debug("Remove session attribute [redirect_uri]");
    }

    public static List<Parameter> getParameters(Map<String, String> map) {
        List<Parameter> parameters = new ArrayList<>();
        for (String key : map.keySet()) {
            Parameter parameter = new Parameter();
            parameter.setKey(key);
            parameter.setValue(map.get(key));
            parameters.add(parameter);
        }
        return parameters;
    }

    public List<Parameter> getParametersByArray(Map<String, String[]> map) {
        List<Parameter> parameters = new ArrayList<>();
        for (String key : map.keySet()) {
            Parameter parameter = new Parameter();
            String[] paramValues = map.get(key);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (!paramValue.isEmpty()) {
                    parameter.setKey(key);
                    parameter.setValue(paramValue);
                    parameters.add(parameter);
                }
            }
        }
        return parameters;
    }
}
