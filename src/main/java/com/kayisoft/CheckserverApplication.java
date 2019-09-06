package com.kayisoft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan({"com.kayisoft"})
@MapperScan(basePackages = {"com.kayisoft.mapper"})
public class CheckserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckserverApplication.class, args);
    }

}
