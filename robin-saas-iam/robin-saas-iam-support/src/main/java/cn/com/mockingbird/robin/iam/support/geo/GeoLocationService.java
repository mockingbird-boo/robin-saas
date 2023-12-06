package cn.com.mockingbird.robin.iam.support.geo;

/**
 * 地理位置 Service 接口
 *
 * @author zhaopeng
 * @date 2023/12/6 20:14
 **/
public interface GeoLocationService {

    /**
     * 根据远程 IP 返回地理位置
     * @param remote 远程 IP
     * @return GeoLocation 实例
     */
    GeoLocation getGeoLocation(String remote);

}
