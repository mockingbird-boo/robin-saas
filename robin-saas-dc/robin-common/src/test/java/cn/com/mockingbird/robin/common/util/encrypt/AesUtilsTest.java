package cn.com.mockingbird.robin.common.util.encrypt;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * AES 加解密测试类
 *
 * @author zhaopeng
 * @date 2023/11/2 22:47
 **/
class AesUtilsTest {

    String key = "vvIwxMjPOvIzMi";
    String data = "Hei，兄弟，29号凌晨2点动手！";

    @BeforeEach
    void setUp() {
        System.out.println("-------------- 测试 Start ---------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("-------------- 测试 End ---------------");
    }

    @Test
    void encrypt() {
        System.out.println(AesUtils.encrypt(data, key));
    }

    @Test
    void decrypt() {
        String secretData = "HlQC44QvSI44Wdzc6MfqJVhulUNaU1fq06od9R4gmShEqrn2afD7W6uNUGotre8c";
        System.out.println(AesUtils.decrypt(secretData, key));
    }
}