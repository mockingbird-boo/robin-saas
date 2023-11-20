package cn.com.mockingbird.robin.common.util;

import cn.com.mockingbird.robin.common.constant.Standard;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * Ip 工具
 * 支持字符串 IP 和 Long 数据类型互转等。（为了节约空间和提高查询效率，数据库对 IP 地址的存储推荐使用无符号整数类型）
 *
 * @author zhaopeng
 * @date 2023/11/20 21:52
 **/
@UtilityClass
public class IpUtils {

    /**
     * IP 转 long
     * @param ip IP 地址
     * @return 转换后的 long 值
     */
    public long ip2Long(String ip) {
        String[] ips = StringUtils.split(ip, Standard.Str.POINT);
        return (Long.parseLong(ips[0]) << 24) + (Long.parseLong(ips[1]) << 16) + (Long.parseLong(ips[2]) << 8) + Long.parseLong(ips[3]);
    }

    public String long2Ip(long ip) {
        return (ip >>> 24) + Standard.Str.POINT +
                ((ip >>> 16) & 0xFF) + Standard.Str.POINT +
                ((ip >>> 8) & 0xFF) + Standard.Str.POINT +
                (ip & 0xFF);
    }

}
