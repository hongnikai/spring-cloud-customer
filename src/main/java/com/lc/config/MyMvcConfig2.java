package com.lc.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//使用WebMvcConfigurerAdapter 可以拓展springMVC的功能
//@EnableWebMvc   //使springboot默认配置的mvc 失效
@Configuration
public class MyMvcConfig2 extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        //浏览器发送/lc  请求即可直接访问success页面
        registry.addViewController("/lc").setViewName("success");
        registry.addViewController("/").setViewName("index");  //第二种跳转主页方法
        registry.addViewController("/index.html").setViewName("index");
    }



}
