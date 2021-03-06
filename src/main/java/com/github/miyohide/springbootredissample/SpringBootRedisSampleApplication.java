package com.github.miyohide.springbootredissample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootRedisSampleApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRedisSampleApplication.class, args);
    }

    private final MyService myService;

    public SpringBootRedisSampleApplication(MyService myService) {
        this.myService = myService;
    }

    @Override
    public void run(String... args) {
        myService.setData();
        myService.getData();
    }
}
