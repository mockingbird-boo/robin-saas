package cn.com.mockingbird.robin.iam.support.security.redirect;

import cn.com.mockingbird.robin.iam.support.autoconfigure.IamProperties;
import com.google.common.collect.Lists;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.IdGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

/**
 * 跳转 Controller
 *
 * @author zhaopeng
 * @date 2023/12/6 16:20
 **/
@Controller
@RequestMapping("/api/v1/jump")
public class JumpController {

    private final RedirectCache redirectCache = new HttpSessionRedirectCache();

    private final IamProperties iamProperties;

    public static final String JUMP_PATH = "/api/v1/jump";

    public JumpController(IamProperties iamProperties) {
        this.iamProperties = iamProperties;
    }

    @GetMapping
    public ModelAndView jump(HttpServletRequest request, HttpServletResponse response) {
        Redirect redirect = redirectCache.getRedirect(request, response);
        if (Objects.isNull(redirect)) {
            String defaultRedirectUrl = iamProperties.getSecurity().getJump().getDefaultRedirectUrl();
            String redirectUrl = Objects.toString(defaultRedirectUrl, this.getServerUrl(request));
            redirect = new Redirect();
            redirect.setAction(redirectUrl);
            redirect.setMethod(HttpMethod.GET.name());
            redirect.setParameters(Lists.newArrayList());
        }

        request.getSession(false).removeAttribute(RedirectCache.WEB_SECURITY_SAVED_REDIRECT);
        IdGenerator idGenerator = new AlternativeJdkIdGenerator();
        ModelAndView view = new ModelAndView("jump/jump_get");
        view.addObject("redirect", redirect);
        view.addObject("nonce", idGenerator.generateId());
        if (HttpMethod.GET.matches(redirect.getMethod())) {
            return view;
        } else if (HttpMethod.POST.matches(redirect.getMethod())) {
            view.setViewName("jump/jump_post");
            return view;
        } else {
            return view;
        }
    }

    private String getServerUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        String url;
        if ((!"http".equals(scheme) || serverPort != 80) && (!"https".equals(scheme) || serverPort != 443)) {
            url = scheme + "://" + serverName + ":" + serverPort + contextPath;
        } else {
            url = scheme + "://" + serverName + contextPath;
        }
        return url;
    }

}
