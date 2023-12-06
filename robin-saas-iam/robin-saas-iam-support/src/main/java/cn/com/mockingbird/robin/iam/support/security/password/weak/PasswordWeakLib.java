package cn.com.mockingbird.robin.iam.support.security.password.weak;

import java.util.List;

/**
 * 弱密码库
 *
 * @author zhaopeng
 * @date 2023/12/6 19:04
 **/
public interface PasswordWeakLib {

    /**
     * 判断指定字符是否存在
     * @param word 字符
     * @return true - 是
     */
    Boolean wordExists(String word);

    /**
     * 查询字符串集合
     * @return 字符串集合
     */
    List<String> getWordList();

}
