package cn.com.mockingbird.robin.quartz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 定时任务应用入口
 *
 * @author zhaopeng
 * @date 2023/9/30 11:50
 **/
@SpringBootApplication
@MapperScan("cn.com.mockingbird.robin.quartz.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
