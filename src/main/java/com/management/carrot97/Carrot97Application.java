package com.management.carrot97;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@MapperScan("com.management.carrot97.mapper")
@SpringBootApplication
public class Carrot97Application {
    public static void main(String[] args) {
        SpringApplication.run(Carrot97Application.class, args);
    }

}
