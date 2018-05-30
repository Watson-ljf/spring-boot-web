package com.watson.controller;

import com.alibaba.fastjson.JSON;
import com.watson.service.core.UserService;
import com.watson.service.core.base.BaseService;
import com.watson.service.core.domain.SUser;
import com.watson.service.redis.RedisCacheDB2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

/**
 * author: Watson
 * description:
 * date: 2018/5/30 9:19.
 */
@RestController
@RequestMapping("/test")
public class IndexController {
    @Autowired
    private RedisCacheDB2Service redisCacheDB2Service;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/index")
    public String index() {
        SUser sUser = new SUser();
        sUser.setName("李健峰");
        sUser.setSex(1);
        Date now = Calendar.getInstance().getTime();
        sUser.setAddTime(now);
        sUser.setModifyTime(now);
        sUser.setIsDelete(0);
        userService.saveUser(sUser);
        return JSON.toJSONString(sUser);
//        return redisCacheDB2Service.get("ljf");

    }
}
