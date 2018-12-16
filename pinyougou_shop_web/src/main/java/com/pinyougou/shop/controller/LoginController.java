package com.pinyougou.shop.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZJE on 2018/12/9
 */


@RestController
@RequestMapping("/login")
public class LoginController {

    //基于安全框架获取用户信息
    @RequestMapping("/getUserMessage")
    public Map<String,String> getUserMessage(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String,String> map = new HashMap<>();
        map.put("loginName",name);
        return map;
    }




}
