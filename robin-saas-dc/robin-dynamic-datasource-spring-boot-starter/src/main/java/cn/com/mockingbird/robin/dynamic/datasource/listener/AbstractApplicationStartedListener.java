package cn.com.mockingbird.robin.dynamic.datasource.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;

/**
 * 应用启动器抽象模板
 *
 * @author zhaopeng
 * @date 2023/11/12 22:20
 **/
@SuppressWarnings("unused")
@Slf4j
public abstract class AbstractApplicationStartedListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(@NonNull ApplicationStartedEvent event) {
        log.info("The application has been launched.");
        if (beforeInitDataSources()) {
            initDataSources();
            afterInitDataSources();
        }
    }

    /**
     * 钩子方法
     * @return true - 可以执行数据源加载操作了
     */
    public boolean beforeInitDataSources() {
        return true;
    }

    /**
     * 模板方法
     */
    public abstract void initDataSources();

    public void afterInitDataSources() {}

}
