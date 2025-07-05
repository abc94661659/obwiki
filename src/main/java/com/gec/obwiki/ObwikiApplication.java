package com.gec.obwiki;

import com.gec.obwiki.config.WebSocketConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.gec.obwiki.mapper")
@ServletComponentScan
@EnableScheduling // <-------------启动定时任务
@EnableAsync
public class ObwikiApplication {

    //使用SLF4j框架创建日志记录器  获取一个与ObwikiApplication类相关联的日志记录器实例
    private static final Logger LOG = LoggerFactory.getLogger(ObwikiApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ObwikiApplication.class);
        ConfigurableEnvironment env = app.run(args).getEnvironment();
        LOG.info("项目启动成功!");
        LOG.info("地址：\thttp://localhost:{}",env.getProperty("server.port"));
    }

}
