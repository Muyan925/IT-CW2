package com.example.easystudy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Component;
@SpringBootApplication
public class EasyStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyStudyApplication.class, args);
    }

}
