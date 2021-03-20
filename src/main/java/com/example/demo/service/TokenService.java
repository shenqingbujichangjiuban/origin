package com.example.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.pojo.User;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * @description:
 * @author: ChangCheng
 * @time: 2021/1/2 16:42
 */

@Service("TokenService")
public class TokenService {
    public String getToken(User user) throws UnsupportedEncodingException {
        String token = "";
        token = JWT.create().withAudience(user.getId()).sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
