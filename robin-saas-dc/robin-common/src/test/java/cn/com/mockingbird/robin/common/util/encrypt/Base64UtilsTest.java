package cn.com.mockingbird.robin.common.util.encrypt;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Base64 工具类
 *
 * @author zhaopeng
 * @date 2023/10/27 21:39
 **/
class Base64UtilsTest {

    @BeforeEach
    void setUp() {
        System.out.println("-------------- 测试 Start ---------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("-------------- 测试 End ---------------");
    }

    @Test
    void testBase64() {
        System.out.println(Base64Utils.encode("Ready Go，123，我的纸飞机啊，飞呀飞呀。。。"));
        System.out.println(Base64Utils.encode("Ready Go，123，我的纸飞机啊，飞呀飞呀。。。".getBytes()));
        System.out.println(Base64Utils.decode("UmVhZHkgR2/vvIwxMjPvvIzmiJHnmoTnurjpo57mnLrllYrvvIzpo57lkYDpo57lkYDjgILjgILjgII="));
        System.out.println(Base64Utils.decode("UmVhZHkgR2/vvIwxMjPvvIzmiJHnmoTnurjpo57mnLrllYrvvIzpo57lkYDpo57lkYDjgILjgILjgII=".getBytes()));
    }
}