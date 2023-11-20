package cn.com.mockingbird.robin.common.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * IpUtils 测试
 *
 * @author zhaopeng
 * @date 2023/11/20 22:07
 **/
public class IpUtilsTest {

    @BeforeEach
    void setUp() {
        System.out.println("-------------- 测试 Start ---------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("-------------- 测试 End ---------------");
    }

    @Test
    void testIp2Long() {
        String ip = "39.156.66.10";
        System.out.println(IpUtils.ip2Long(ip)); // 664551946
    }

    @Test
    void testLong2Ip() {
        long ip = 664551946L;
        System.out.println(IpUtils.long2Ip(ip));
    }

}
