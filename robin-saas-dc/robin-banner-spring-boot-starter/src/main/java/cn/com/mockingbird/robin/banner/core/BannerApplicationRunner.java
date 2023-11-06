package cn.com.mockingbird.robin.banner.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.util.StringUtils;

/**
 * 应用程序启动扩展
 * <p>
 * 通过实现 ApplicationRunner 接口，可以在项目启动时对程序进行扩展
 *
 * @author zhaopeng
 * @date 2023/10/4 23:22
 **/
@Slf4j
public class BannerApplicationRunner implements ApplicationRunner {

    private static final String URL_PREFIX = "http://127.0.0.1:";

    @Value("${spring.application.name}")
    private String name;

    @Autowired
    private ServerProperties serverProperties;

    @Override
    public void run(ApplicationArguments args) {
        StringBuilder runLog = new StringBuilder(URL_PREFIX + serverProperties.getPort());
        String contextPath = serverProperties.getServlet().getContextPath();
        if (StringUtils.hasText(contextPath)) {
            if (!contextPath.startsWith("/")) {
                runLog.append("/");
            }
            runLog.append(contextPath);
        }
        runLog.append("/swagger-ui.html");
        new Thread(() -> {
            try {
                // 线程休眠1s，确保输出到结尾
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("""

                            ----------------------------------------------------------
                            \t(♥◠‿◠)ﾉﾞ  {} 启动成功   ಠಿ_ಠﾞ
                            \t接口文档:  {}\s
                            ----------------------------------------------------------""",
                    name, runLog);
        }).start();
    }

}
