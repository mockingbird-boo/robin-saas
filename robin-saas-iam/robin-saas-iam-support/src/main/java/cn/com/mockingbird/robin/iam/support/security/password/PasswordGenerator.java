package cn.com.mockingbird.robin.iam.support.security.password;

/**
 * 密码生成器接口
 *
 * @author zhaopeng
 * @date 2023/12/6 18:21
 **/
public interface PasswordGenerator {

    /**
     * 生成密码
     * @return 密码
     */
    String generatePassword();

}
