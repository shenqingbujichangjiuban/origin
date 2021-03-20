package com.example.demo.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.annotation.PassToken;
import com.example.demo.annotation.UserLoginToken;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: ChangCheng
 * @time: 2021/1/2 15:35
 */

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token =request.getHeader("token");

        if (!(handler instanceof HandlerMethod)){
            return true;
        }

        // 父类对象 handler 转化为子类对象 handlerMethod，为了调用子类扩展的方法
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(PassToken.class)){
            PassToken annotation = method.getAnnotation(PassToken.class);
            if (annotation.required()){
                return true;
            }
        }

        if(method.isAnnotationPresent(UserLoginToken.class)){
            UserLoginToken annotation = method.getAnnotation(UserLoginToken.class);
            if (annotation.required()){
                if(token==null){
                    throw new RuntimeException("没有token，请重新登录");
                }
                String id;
                try {
                    id = JWT.decode(token).getAudience().get(0);
                }catch (JWTDecodeException e) {
                    throw new RuntimeException("401");
                }
                User user = userService.findUserById(id);
                if(user == null){
                    throw new RuntimeException("用户信息不存在，请重新登录");
                }
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                }catch (JWTVerificationException e) {
                    throw new RuntimeException("401");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("方法执行后......");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("最后......");
    }
}
