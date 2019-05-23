package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.RestTemplateCustomizer;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

//@SpringCloudApplication  = @SpringBootApplication +
//                           @EnableDiscoveryClient +
//                           @EnableCircuitBreaker


@EnableCircuitBreaker   //启用断路器 hystrix
@EnableFeignClients     //启用feign进行远程调用
@EnableDiscoveryClient  //启用服务注册与发现
@SpringBootApplication
@MapperScan("com.lc.dao")
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }





}

