package cn.com.mockingbird.robin.web.config;

import org.slf4j.TtlMDCAdapter;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * TtlMDCAdapter Initializer
 *
 * @author zhaopeng
 * @date 2023/10/15 22:37
 **/
public class TtlMDCAdapterInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @SuppressWarnings("all")
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // 加载 TtlMDCAdapter
        TtlMDCAdapter.getInstance();
    }

}
