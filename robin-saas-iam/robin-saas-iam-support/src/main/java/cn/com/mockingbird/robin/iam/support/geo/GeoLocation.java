package cn.com.mockingbird.robin.iam.support.geo;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 地理位置模型
 *
 * @author zhaopeng
 * @date 2023/12/6 20:15
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeoLocation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1106678584294421907L;

    private String ip;
    private String continentCode;
    private String continentName;
    private String countryCode;
    private String countryName;
    private String cityCode;
    private String cityName;
    private String provinceCode;
    private String provinceName;
    private Double latitude;
    private Double longitude;

    private GeoLocationProvider provider;

}
