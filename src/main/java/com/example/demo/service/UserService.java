package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: ChangCheng
 * @time: 2021/1/2 16:41
 */

@Service("UserService")
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User findUserByUserName(String username){
        User user = userMapper.findUserByUserName(username);
        return user;
    }

    public User findUserById(String id){
        User user = userMapper.findUserById(id);
        return user;
    }

}
