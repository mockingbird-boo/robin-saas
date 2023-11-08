package cn.com.mockingbird.robin.influxdb.influxql;

import lombok.NoArgsConstructor;

/**
 * Influx Delete 组成因子
 *
 * @author zhaopeng
 * @date 2023/11/9 0:34
 **/
@NoArgsConstructor
public class DeleteFactor extends Factor {

    public DeleteFactor(String measurement) {
        super();
        super.setMeasurement(measurement);
    }

}
