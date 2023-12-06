package cn.com.mockingbird.robin.iam.support.geo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 地理位置 Provider
 *
 * @author zhaopeng
 * @date 2023/12/6 20:16
 **/
@Data
public class GeoLocationProvider implements Serializable {

    @Serial
    private static final long serialVersionUID = 1865367453372639107L;

    private String provider;
    private String name;

    public static GeoLocationProvider NONE = new GeoLocationProvider("none", "none");

    public GeoLocationProvider() {
    }

    public GeoLocationProvider(String provider, String name) {
        this.provider = provider;
        this.name = name;
    }
}
