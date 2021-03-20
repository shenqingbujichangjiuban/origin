package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @description:
 * @author: ChangCheng
 * @time: 2021/1/2 20:56
 */

@ControllerAdvice
public class GloablExceptionController {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object controllerException(Exception e) {
        String msg = e.getMessage();
        if(msg == null || msg.equals("")){
            msg = "服务器出错";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", msg);
        return jsonObject;
    }
}
