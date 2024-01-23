package com.develop.photoapp;

import feign.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class PhotoUserApiApplication {
    final Environment environment;

    @Autowired
    public PhotoUserApiApplication(Environment environment) {
        this.environment = environment;
    }

    public static void main(String[] args) {
        SpringApplication.run(PhotoUserApiApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Profile("production")
    Logger.Level feignLoggerLevel() {
        return Logger.Level.NONE;
    }

    @Bean
    @Profile("!production")
    Logger.Level feignDefaultLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    @Profile("production")
    public String createProductionBean() {
        System.out.printf("Production bean created. myapplication.environment = %s\n",
                environment.getProperty("myapplication.environment"));
        return "production bean";
    }

    @Bean
    @Profile("!production")
    public String createNotProductionBean() {
        System.out.printf("Not production bean created. myapplication.environment = %s\n",
                environment.getProperty("myapplication.environment"));
        return "not production bean";
    }

    @Bean
    @Profile("default")
    public String createDevelopmentBean() {
        System.out.printf("Development bean created. myapplication.environment = %s\n",
                environment.getProperty("myapplication.environment"));
        return "development bean";
    }
}
