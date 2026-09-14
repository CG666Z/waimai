package com.waimai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 启动类。
 * @SpringBootApplication 会告诉 Spring：
 *   1. 这是启动入口；
 *   2. 自动扫描 com.waimai 包下的所有组件（@Service / @Repository / @RestController）。
 */
@SpringBootApplication
public class WaimaiApplication {

    public static void main(String[] args) {
        // 启动内嵌 Tomcat，监听 8080 端口
        SpringApplication.run(WaimaiApplication.class, args);
    }
}
