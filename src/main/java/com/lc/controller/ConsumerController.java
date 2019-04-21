package com.lc.controller;

import com.lc.config.UserCommand;
import com.lc.dao.ProviderDao;
import com.lc.entity.Book;
import com.lc.entity.User;
import com.lc.service.HelloService;
import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;


@Controller
public class ConsumerController {


    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ProviderDao providerDao;

    @Autowired
    private HelloService helloService;


    @RequestMapping(value = "/ribbon-consumer",method = RequestMethod.GET)
    @ResponseBody
    public String helloController() {
        //还可以定义uri对象
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://spring-cloud-provider/hello")
                .build()
                .expand("dodo")
                .encode();
        // 转化uri
        // URI uri = uriComponents.toUri();
        // String responseEntity=restTemplate.getForEntity(uri,String.class).getBody();

        //-----------------------------------------------------------------------------------------//
        //getForObject函数
        //RestTemplate restTemplate = new RestTemplate();
        //String result = restTemplate.getForObject(uri,String.class);

        //当body是一个User对象时，可以直接这样实现
        //RestTemplate restTemplate = new RestTemplate();
        //User result = restTemplate.getForObject(uri,User.class);

        //return restTemplate.getForEntity("http://spring-cloud-provider/hello", String.class).getBody();
        return helloService.hello();
    }

    @ResponseBody
    @RequestMapping("/testYCDY")
    public String testYCDY(){
      return providerDao.hello();
    }


    @RequestMapping("/postForProvider")
    @ResponseBody
    public Object postForProvider(){
        Map<String,Object> paramMap =new HashMap<String, Object>();
        paramMap.put("name","张三");
//        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
//        paramMap.add("name", "20180416");

//        Book book = new Book();
//        book.setName("老人yu海");
        //ResponseEntity<String> responseEntity=restTemplate.postForEntity("http://spring-cloud-provider/hello2","name",String.class);
        Object responseEntity=restTemplate.postForEntity("http://spring-cloud-provider/hello2",paramMap,String.class);
        return responseEntity;
    }


    @RequestMapping("/getForProvider/{id}")
    @ResponseBody
    public Object getForProvider(@PathVariable("id")int id){
        HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey("commandKey");
        HystrixRequestContext.initializeContext();
        UserCommand uc1=new UserCommand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("")).andCommandKey(commandKey), restTemplate, 1l);
        User c1 = uc1.execute();

        UserCommand uc2=new UserCommand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("")).andCommandKey(commandKey), restTemplate, 1l);
        User c2 = uc2.execute();

        UserCommand uc3=new UserCommand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("")).andCommandKey(commandKey), restTemplate, 1l);
        User c3 = uc3.execute();

        System.out.println("e1:" + c1);
        System.out.println("e2:" + c2);
        System.out.println("e3:" + c3);
        //return restTemplate.getForEntity("http://spring-cloud-provider/hello3?id="+id, String.class).getBody();


        return c1;
    }




}
