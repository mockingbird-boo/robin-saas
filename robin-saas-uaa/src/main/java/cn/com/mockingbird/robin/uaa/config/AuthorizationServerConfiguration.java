//package cn.com.mockingbird.robin.uaa.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
//import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
//import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
//import org.springframework.security.oauth2.server.authorization.web.authentication.DelegatingAuthenticationConverter;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationConverter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import java.util.Arrays;
//
///**
// * 授权服务配置
// *
// * @author zhaopeng
// * @date 2023/11/25 20:49
// **/
//@Configuration
//@RequiredArgsConstructor
//public class AuthorizationServerConfiguration {
//
//    private final OAuth2AuthorizationService auth2AuthorizationService;
//
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
//        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
//        http.apply(
//                // 个性化认证授权端点
//                authorizationServerConfigurer.tokenEndpoint((tokenEndpoint) -> {
//                    // 注入自定义的授权认证Converter
//                    tokenEndpoint.accessTokenRequestConverter(accessTokenRequestConverter())
//                            // 登录成功处理器
//                            .accessTokenResponseHandler(new PigAuthenticationSuccessEventHandler())
//                            .errorResponseHandler(new PigAuthenticationFailureEventHandler());// 登录失败处理器
//                }).clientAuthentication(oAuth2ClientAuthenticationConfigurer -> // 个性化客户端认证
//                        oAuth2ClientAuthenticationConfigurer.errorResponseHandler(new PigAuthenticationFailureEventHandler()))// 处理客户端认证异常
//                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint// 授权码端点个性化confirm页面
//                        .consentPage(SecurityConstants.CUSTOM_CONSENT_PAGE_URI)));
//
//        AntPathRequestMatcher[] requestMatchers = new AntPathRequestMatcher[] {
//                AntPathRequestMatcher.antMatcher("/token/**"),
//                AntPathRequestMatcher.antMatcher("/actuator/**"),
//                AntPathRequestMatcher.antMatcher("/css/**"),
//                AntPathRequestMatcher.antMatcher("/error") };
//
//        http.authorizeHttpRequests(authorizeRequests -> {
//                    // 自定义接口、端点暴露
//                    authorizeRequests.requestMatchers(requestMatchers).permitAll();
//                    authorizeRequests.anyRequest().authenticated();
//                })
//                .apply(authorizationServerConfigurer.authorizationService(authorizationService)// redis存储token的实现
//                        .authorizationServerSettings(
//                                AuthorizationServerSettings.builder().issuer(SecurityConstants.PROJECT_LICENSE).build()));
//        http.apply(new FormIdentityLoginConfigurer());
//        DefaultSecurityFilterChain securityFilterChain = http.build();
//
//        // 注入自定义授权模式实现
//        addCustomOAuth2GrantAuthenticationProvider(http);
//        return securityFilterChain;
//    }
//
//    /**
//     * 令牌生成规则实现 </br>
//     * client:username:uuid
//     * @return OAuth2TokenGenerator
//     */
//    @Bean
//    public OAuth2TokenGenerator oAuth2TokenGenerator() {
//        CustomeOAuth2AccessTokenGenerator accessTokenGenerator = new CustomeOAuth2AccessTokenGenerator();
//        // 注入Token 增加关联用户信息
//        accessTokenGenerator.setAccessTokenCustomizer(new CustomeOAuth2TokenCustomizer());
//        return new DelegatingOAuth2TokenGenerator(accessTokenGenerator, new OAuth2RefreshTokenGenerator());
//    }
//
//    /**
//     * request -> xToken 注入请求转换器
//     * @return DelegatingAuthenticationConverter
//     */
//    private AuthenticationConverter accessTokenRequestConverter() {
//        return new DelegatingAuthenticationConverter(Arrays.asList(
//                new OAuth2ResourceOwnerPasswordAuthenticationConverter(),
//                new OAuth2ResourceOwnerSmsAuthenticationConverter(), new OAuth2RefreshTokenAuthenticationConverter(),
//                new OAuth2ClientCredentialsAuthenticationConverter(),
//                new OAuth2AuthorizationCodeAuthenticationConverter(),
//                new OAuth2AuthorizationCodeRequestAuthenticationConverter()));
//    }
//
//    /**
//     * 注入授权模式实现提供方
//     *
//     * 1. 密码模式 </br>
//     * 2. 短信登录 </br>
//     *
//     */
//    @SuppressWarnings("unchecked")
//    private void addCustomOAuth2GrantAuthenticationProvider(HttpSecurity http) {
//        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
//        OAuth2AuthorizationService authorizationService = http.getSharedObject(OAuth2AuthorizationService.class);
//
//        OAuth2ResourceOwnerPasswordAuthenticationProvider resourceOwnerPasswordAuthenticationProvider = new OAuth2ResourceOwnerPasswordAuthenticationProvider(
//                authenticationManager, authorizationService, oAuth2TokenGenerator());
//
//        OAuth2ResourceOwnerSmsAuthenticationProvider resourceOwnerSmsAuthenticationProvider = new OAuth2ResourceOwnerSmsAuthenticationProvider(
//                authenticationManager, authorizationService, oAuth2TokenGenerator());
//
//        // 处理 UsernamePasswordAuthenticationToken
//        http.authenticationProvider(new PigDaoAuthenticationProvider());
//        // 处理 OAuth2ResourceOwnerPasswordAuthenticationToken
//        http.authenticationProvider(resourceOwnerPasswordAuthenticationProvider);
//        // 处理 OAuth2ResourceOwnerSmsAuthenticationToken
//        http.authenticationProvider(resourceOwnerSmsAuthenticationProvider);
//    }
//
//}
