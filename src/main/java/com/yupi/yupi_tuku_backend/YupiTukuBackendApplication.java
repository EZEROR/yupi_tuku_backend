package com.yupi.yupi_tuku_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.yupi.yupi_tuku_backend.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)

public class YupiTukuBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(YupiTukuBackendApplication.class, args);
    }

}
