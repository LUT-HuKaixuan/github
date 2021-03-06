package cn.lut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StarterPic {
    public static void main(String[] args) {
        SpringApplication.run(StarterPic.class,args);
    }
}
