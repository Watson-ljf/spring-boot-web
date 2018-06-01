package com.watson.system.controller;

import javax.servlet.http.HttpServletRequest;

import com.watson.core.utils.ResultMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wf.etp.authz.annotation.RequiresPermissions;

/**
 * 测试RESTful接口
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    @RequiresPermissions("system")
    @GetMapping
    public ResultMap list(HttpServletRequest request) {
        return ResultMap.ok();
    }

    /**
     * 根据id获取资源
     */
    @RequiresPermissions("test")
    @GetMapping("{id}")
    public ResultMap getById(@PathVariable("id") String id) {
        return ResultMap.ok().put("id", id);
    }
}
