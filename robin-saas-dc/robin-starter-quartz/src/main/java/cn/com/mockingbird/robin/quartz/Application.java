package cn.com.mockingbird.robin.quartz;

import cn.com.mockingbird.robin.banner.autoconfigure.BannerAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * 定时任务应用入口
 *
 * @author zhaopeng
 * @date 2023/9/30 11:50
 **/
@SpringBootApplication
//@ComponentScan(value = {"cn.com.mockingbird.robin.quartz", "cn.com.mockingbird.robin.banner"})
//@Import(BannerAutoConfiguration.class)
@MapperScan("cn.com.mockingbird.robin.quartz.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
