package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.annotation.UserLoginToken;
import com.example.demo.pojo.User;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @description:
 * @author: ChangCheng
 * @time: 2021/1/2 21:22
 */

@RestController
@RequestMapping("/index")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @PostMapping("login")
    public Object login(User user) throws UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        User userByUserName = userService.findUserByUserName(user.getUsername());
        if(userByUserName == null){
            jsonObject.put("message","登录失败，用户不存在");
            return jsonObject;
        }else {
            if(!userByUserName.getPassword().equals(user.getPassword())){
                jsonObject.put("message","登录失败，密码不正确");
                return jsonObject;
            }else{
                String token = tokenService.getToken(userByUserName);
                jsonObject.put("token",token);
                jsonObject.put("user",userByUserName);
                return jsonObject;
            }
        }
    }

    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "通过验证";
    }

}
