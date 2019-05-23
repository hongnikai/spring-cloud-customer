package com.lc.controller;

import com.lc.entity.User;
import com.lc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    private StringRedisTemplate redisTemplate;

     //@GetMapping  ==  @RequestMapping(value = "/user/login",method = RequestMethod.GET)
     //@PostMapping ==  @RequestMapping(value = "/user/login",method = RequestMethod.POST)
     @PostMapping(value = "/user/login")
     public String login(@RequestParam("username")String username, @RequestParam("password")String password,
                         Map<String,Object> map, HttpSession session
                         ){
        if (!StringUtils.isEmpty(username)&& "123456".equals(password)){
            session.setAttribute("loginUser",username);
            //登陆成功，防止表单重复提交，重定向到主页
            return "redirect:/main.html";
        }else{
            map.put("msg","用户名密码错误");
            return "index";
        }

     }

//     @GetMapping("login")
//     public String login2(){
//         return "index";
//     }
    @RequestMapping("/login")
    public Object login(HttpServletRequest request, String username, String password){
        //从数据库查询是否有这个账号密码的用户
        User user = userService.findUserByAccountAndPassword(username, password);
        if (user != null){
            HttpSession session = request.getSession();
            session.setAttribute("loginUserId", user.getId());
            redisTemplate.opsForValue().set("loginUser:" + user.getId(), session.getId());
            return user;
        }
        else{
            return "登录失败";
        }
    }

    @RequestMapping(value = "/getUserInfo")
    public Object get(@RequestParam("id") String userId){
        User user = userService.findUserByUserId(userId);
        if (user != null){
            return user;  //查询成功返回user对象
        }
        else{
            return "查询失败";
        }
    }

    @RequestMapping("/getSessionTimeOut")
    public Object getSessionTimeOut(HttpServletRequest request){
        HttpSession session = request.getSession();
        int interval = session.getMaxInactiveInterval();
        return interval;
    }


     @RequestMapping("toIndex")
    public String toIndex(){

         return "index";
     }
}
