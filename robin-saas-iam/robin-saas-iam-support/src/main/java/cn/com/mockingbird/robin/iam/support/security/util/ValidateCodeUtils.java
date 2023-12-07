package cn.com.mockingbird.robin.iam.support.security.util;

import lombok.experimental.UtilityClass;

import java.util.Random;

/**
 * 验证码工具类
 *
 * @author zhaopeng
 * @date 2023/12/6 0:38
 **/
@UtilityClass
public class ValidateCodeUtils {

    private final Integer MIX_LENGTH = 4;
    private final Integer MIX_CODE = 1000;
    private final Integer MAX_LENGTH = 4;
    private final Integer MAX_CODE = 100000;

    /**
     * 生成指定长度的验证码
     * @param length 指定长度
     * @return 指定长度的验证码 （Integer）
     */
    public Integer generateValidateCode(int length) {
        int code;
        if (length == MIX_LENGTH) {
            code = (new Random()).nextInt(9999);
            if (code < MIX_CODE) {
                code += 1000;
            }
        } else {
            if (length != MAX_LENGTH) {
                throw new RuntimeException("只能生成4位或6位数字验证码");
            }

            code = (new Random()).nextInt(999999);
            if (code < MAX_CODE) {
                code += 100000;
            }
        }

        return code;
    }

    /**
     * 获取指定长度的验证码
     * @param length 指定长度
     * @return 指定长度的验证码 （字符串）
     */
    public String generateValidateCode4String(int length) {
        Random rdm = new Random();
        String hash = Integer.toHexString(rdm.nextInt());
        return hash.substring(0, length);
    }

}
