package cn.com.mockingbird.robin.api.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * API 接口安全增强配置类
 *
 * @author zhaopeng
 * @date 2023/11/2 23:34
 **/
@Data
@ConfigurationProperties("spring.web.enhance.api.security")
public class ApiSecurityProperties {
    /**
     * 是否开启 API 接口安全增强
     */
    private Boolean enable = true;
    /**
     * 签名过期时间
     */
    private Integer signatureExpiredTime = 30;
    /**
     * RSA 私钥
     */
    private String rsaPrivateKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCH0Yl804LjPZ" +
            "+ugTrvhBWOhzjzRzrnX16O56hfrR1iMIY4Dc7RpefDBqwr" +
            "CIuZ4hrI8CwS95FiSZkIdqisCrchVHPF1tX0pTtyWFeiTqweuj05kObB5" +
            "AV/jAw4lMBJ0pMZbIrSzCKTk8L4CwmLshnipZoG7HmpN/lQcQ9vyAcMaQIDAQAB";
    /**
     * RSA 公钥
     */
    private String rsaPublicKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwgg" +
            "JcAgEAAoGBAIfRiXzTguM9n66BOu+EFY6HOPNHOudfXo7nq" +
            "F+tHWIwhjgNztGl58MGrCsIi5niGsjwLBL3kWJJmQh2qKwK" +
            "tyFUc8XW1fSlO3JYV6JOrB66PTmQ5sHkBX+MDDiUwEnSkxl" +
            "sitLMIpOTwvgLCYuyGeKlmgbseak3+VBxD2/IBwxpAgMBAA" +
            "ECgYAB1BtdbTI87E54Y1BaJdVtzCTtzuQHIMHoJfb0BXj4zR" +
            "3G0WtwZ829UsYB9Pp+1uF0xRmteQchuKCb0Y/RySR6rt6tmF" +
            "FkJ8yudocMHZqX6YVZzpf1sqX/Ne51UKHBCGBl3Ebm80isIM" +
            "gpmaYkZXGOF3PtcpXR9CXW6O2Q39CZAQJBAPITAx7Fno4HSeX" +
            "8V82l/JhGxcHQV6AMcPvHN3F1R+U/axAzmQAxOnPgNOtuskDTByQI" +
            "R0Hf7HkmW0/OAzR0CMkCQQCPoba6Ss6jAG/bbry6Eo5rGSyMIghonno" +
            "552lFmy71IebWwHsGLUmdh1qhkMcv6an8lUJe5KFihdw+zvkWSlahAkEAl8JvjFUc" +
            "B7JuE67VZOmQp+6Gf/EbXg0ur8m+6nQ6NHBDsI6IduRl3stKoeZt/dHOgjpmYdf+K" +
            "h1v1WI0BChScQJAbwOQ1kBHolkOG85ldHQvO7wDYHRVh3cmBWdFSJV/H9yKLOYssEO" +
            "EqIsbk9DxA6lYx28eE3Ym/p1z89/JSl8+wQJAKlw/pCl9pzuJiFr72cu+aw0xZnD10" +
            "c4UT4ATCBcxYU7hhPxOtGYx7/Luh2NwU3HYOcztbAH0aOw5rzAEzq/UfA==";
}
