package com.lc.service;

import com.lc.entity.Book;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {

    @Autowired
    private RestTemplate restTemplate;


    //熔断器 如果provider发生错误，执行  fallbackMethod 里面的方法
    @HystrixCommand(fallbackMethod = "error")
    public String hello() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://spring-cloud-provider/hello", String.class);
        return responseEntity.getBody();
    }

    public String error() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://spring-cloud-provider/hello", String.class);
        return responseEntity.getBody();
    }


    //服务降级函数加备胎：  error1方法失败后会申请到error2方法
    @HystrixCommand(fallbackMethod = "error1")
    public Book test2() {
        return restTemplate.getForObject("http://HELLO-SERVICE/getbook1", Book.class);
    }

    @HystrixCommand(fallbackMethod = "error2")
    public Book error1() {
        //发起某个网络请求（可能失败）
        return null;
    }
    public Book error2() {
        return new Book();
    }
}
