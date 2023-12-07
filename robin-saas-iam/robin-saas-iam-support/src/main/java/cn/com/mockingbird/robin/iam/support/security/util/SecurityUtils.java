package cn.com.mockingbird.robin.iam.support.security.util;

import cn.com.mockingbird.robin.iam.support.security.userdetails.UserDetails;
import cn.com.mockingbird.robin.iam.support.security.userdetails.UserType;
import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

/**
 * Security 工具类
 *
 * @author zhaopeng
 * @date 2023/12/6 0:36
 **/
@UtilityClass
public class SecurityUtils {

    /**
     * 匿名用户
     */
    public final String ANONYMOUS_USER = "anonymousUser";

    private final AuthenticationTrustResolver TRUST_RESOLVER = new AuthenticationTrustResolverImpl();

    /**
     * 获取当前的 SecurityContext
     * @return 当前的 SecurityContext
     */
    public SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }

    /**
     * 获取当前的用户详情
     * @return 当前的用户详情
     */
    public static UserDetails getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Optional<UserDetails> optional = Optional.ofNullable(securityContext.getAuthentication())
                .map((authentication) -> authentication.getPrincipal() instanceof UserDetails ?
                        (UserDetails)authentication.getPrincipal() : null);
        return optional.orElse(null);
    }

    /**
     * 获取当前的用户 ID
     * @return 当前的用户 ID
     */
    public String getCurrentUserId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Optional<String> optional = Optional.ofNullable(securityContext.getAuthentication())
                .map((authentication) ->
                        authentication.getPrincipal() instanceof UserDetails ?
                                ((UserDetails)authentication.getPrincipal()).getId() : null);
        return optional.orElse(ANONYMOUS_USER);
    }

    /**
     * 获取当前的用户名
     * @return 当前的用户名
     */
    public String getCurrentUserName() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Optional<String> optional = Optional.ofNullable(securityContext.getAuthentication()).map((authentication) -> {
            if (authentication.getPrincipal() instanceof UserDetails) {
                return ((UserDetails)authentication.getPrincipal()).getUsername();
            } else if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails) {
                return ((org.springframework.security.core.userdetails.UserDetails)authentication.getPrincipal()).getUsername();
            } else {
                return authentication.getPrincipal() instanceof String ? (String)authentication.getPrincipal() : null;
            }
        });
        return optional.orElse(ANONYMOUS_USER);
    }

    /**
     * 获取当前的用户类型
     * @return 当前的用户类型
     */
    public UserType getCurrentUserType() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Optional<UserType> optional = Optional.ofNullable(securityContext.getAuthentication())
                .map((authentication) -> authentication.getPrincipal() instanceof UserDetails ?
                        ((UserDetails)authentication.getPrincipal()).getUserType() : null);
        return optional.orElse(UserType.UNKNOWN);
    }

    /**
     * 当前用户是否拥有指定权限
     * @param authority 指定权限
     * @return true - 是
     */
    public boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map((authentication) ->
                        authentication.getAuthorities().stream().anyMatch((grantedAuthority) ->
                                grantedAuthority.getAuthority().equals(authority))).orElse(false);
    }

    /**
     * 指定主体是否已经经过认证
     * @param principal 主体
     * @return true - 是
     */
    public boolean isPrincipalAuthenticated(Authentication principal) {
        return principal != null
                && !AnonymousAuthenticationToken.class.isAssignableFrom(principal.getClass())
                && principal.isAuthenticated();
    }

    /**
     * 当前的主体是否已经经过认证
     * @return true - 是
     */
    public boolean isAuthenticated() {
        Authentication authentication = getSecurityContext().getAuthentication();
        return !Objects.isNull(authentication)
                && !TRUST_RESOLVER.isAnonymous(authentication)
                && authentication.isAuthenticated();
    }

    /**
     * 身份认证失败时获取身份信息
     * @param event 由于某种原因身份验证失败的抽象应用程序事件
     * @return 身份认证失败时的身份信息
     */
    public String getPrincipal(AbstractAuthenticationFailureEvent event) {
        String principal = (String)event.getAuthentication().getPrincipal();
        if (event.getAuthentication().getPrincipal() instanceof String) {
            principal = (String)event.getAuthentication().getPrincipal();
        }

        if (event.getAuthentication().getPrincipal() instanceof UserDetails
                || event.getAuthentication().getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails) {
            principal = ((UserDetails)event.getAuthentication().getPrincipal()).getUsername();
        }
        return principal;
    }
}
