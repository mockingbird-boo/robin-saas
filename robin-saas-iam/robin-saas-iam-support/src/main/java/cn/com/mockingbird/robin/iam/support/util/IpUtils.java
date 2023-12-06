package cn.com.mockingbird.robin.iam.support.util;

import com.google.common.net.InetAddresses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.SubnetUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * IP 工具
 *
 * @author zhaopeng
 * @date 2023/12/5 20:02
 **/
@SuppressWarnings("all")
@UtilityClass
public class IpUtils {

    private static final String UNKNOWN = "Unknown";
    public static final String IPV4 = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    public static final String IPV6 = "^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|(::[fF]{4}(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$";
    public static final String IPV4_CIDR = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)/(?:[12]?[0-9]|3[0-2])$";
    public static final String IPV6_CIDR = "^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|(::[fF]{4}(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))/([0-9]|[1-9][0-9]|1[0-1][0-9]|12[0-8])$";

    /**
     * 判断是否是内部 IP
     * @param ip 参数 IP
     * @return true - 是
     */
    public boolean isInternalIp(String ip) {
        InetAddress address = InetAddresses.forString(ip);
        return isInternalIp(address.getAddress()) || ip.startsWith("127");
    }

    /**
     * 判断是否是内部 IP
     * @param addr InetAddress 对象的原始 IP 地址
     * @return true - 是
     */
    public boolean isInternalIp(byte[] addr) {
        byte b0 = addr[0];
        byte b1 = addr[1];
        switch (b0) {
            case -84 :
                if (b1 >= 16 && b1 <= 31) {
                    return true;
                }
            case -64 :
                return b1 == -88;
            case 10 :
                return true;
            default:
                return false;
        }
    }

    /**
     * 根据请求获取 IP 地址
     * @param request 请求实例
     * @return IP 地址
     */
    public String getIpAddr(HttpServletRequest request) {
        try {
            if (request == null) {
                return UNKNOWN;
            } else {
                String ip = request.getHeader("x-original-forwarded-for");
                if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("x-forwarded-for");
                }

                if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                }

                if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("X-Forwarded-For");
                }

                if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                }

                if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("X-Real-IP");
                }

                if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                    String local = "127.0.0.1";
                    String noAddressSpecified = "0:0:0:0:0:0:0:1";
                    if (StringUtils.equalsAny(ip, local, noAddressSpecified)) {
                        ip = InetAddress.getLocalHost().getHostAddress();
                        ip = Objects.toString(ip, local);
                    }
                }

                return StringUtils.substringBefore(ip, ",");
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回 HostName
     * @return HostName
     */
    public String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException var1) {
            return UNKNOWN;
        }
    }

    /**
     * 返回 HostIp
     * @return Host IP
     */
    public String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    /**
     * 判断指定 IP 是否在指定范围
     * @param ip IP 地址
     * @param startIp 起始 IP
     * @param endIp 截止 IP
     * @return true - 是
     */
    public boolean isInRange(String ip, String startIp, String endIp) {
        try {
            InetAddress addr = InetAddress.getByName(ip);
            InetAddress startAddr = InetAddress.getByName(startIp);
            InetAddress endAddr = InetAddress.getByName(endIp);
            byte[] addrBytes = addr.getAddress();
            byte[] startBytes = startAddr.getAddress();
            byte[] endBytes = endAddr.getAddress();
            boolean greaterThanOrEqualStart = true;
            boolean lessThanOrEqualEnd = true;

            for(int i = 0; i < addrBytes.length; ++i) {
                int addrByte = addrBytes[i] & 255;
                int startByte = startBytes[i] & 255;
                int endByte = endBytes[i] & 255;
                if (addrByte < startByte) {
                    greaterThanOrEqualStart = false;
                    break;
                }

                if (addrByte > endByte) {
                    lessThanOrEqualEnd = false;
                    break;
                }
            }

            return greaterThanOrEqualStart && lessThanOrEqualEnd;
        } catch (UnknownHostException e) {
            return false;
        }
    }

    /**
     * 判断指定 IP 是否在指定 IP 集合范围中
     * @param ip IP
     * @param ipScopes IP 集合范围
     * @return true - 是
     */
    public boolean isInRange(String ip, List<String> ipScopes) {
        Iterator<String> iterator = ipScopes.iterator();
        String ipRange;
        do {
            if (!iterator.hasNext()) {
                return false;
            }

            ipRange = iterator.next();
        } while (!isInRange(ip, ipRange));

        return true;
    }

    /**
     * 判断指定 IP 是否在此子网的可用端点地址范围内
     * @param ip IP
     * @param ipRange 指定的可用端点
     * @return true - 是
     */
    public boolean isInRange(String ip, String ipRange) {
        String[] range = ipRange.split("/");
        if (range.length == 1) {
            return ip.equals(ipRange);
        } else {
            SubnetUtils subnetUtils = new SubnetUtils(ipRange);
            SubnetUtils.SubnetInfo subnetInfo = subnetUtils.getInfo();
            return subnetInfo.isInRange(ip);
        }
    }

    /**
     * 判断集合中的元素是否都是 IP 或 CIDR
     * @param ipSet ip 集合
     * @return true - 是
     */
    public boolean isIpOrCidr(Set<String> ipSet) {
        Iterator<String> iterator = ipSet.iterator();
        String ip;
        do {
            if (!iterator.hasNext()) {
                return true;
            }
            ip = iterator.next();
            String[] range = ip.split("/");
            if (range.length == 1 && !ip.matches(IPV4) && !ip.matches(IPV6)) {
                return false;
            }
        } while (ip.matches(IPV4_CIDR) || !ip.matches(IPV6_CIDR));

        return false;
    }
}
