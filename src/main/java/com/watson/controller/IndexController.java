package com.watson.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: Watson
 * description:
 * date: 2018/5/30 9:19.
 */
@RestController
@RequestMapping("/test")
public class IndexController {

    @RequestMapping(value = "/index")
    public String index() {
        return "Hello World!";
    }
}
