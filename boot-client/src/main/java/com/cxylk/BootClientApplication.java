package com.cxylk;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class BootClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootClientApplication.class, args);
    }

}
