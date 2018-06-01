package com.watson.system.controller;

import com.watson.core.utils.PageResult;
import com.watson.core.utils.StringUtil;
import com.watson.system.model.LoginRecord;
import com.watson.system.service.LoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录日志
 */
@RestController
@RequestMapping("/api/loginRecord")
public class LoginRecordController {
    @Autowired
    private LoginRecordService loginRecordService;

    /**
     * 查询所有登录日志
     *
     * @return
     */
    @GetMapping()
    public PageResult<LoginRecord> list(Integer page, Integer limit, String startDate, String endDate, String account) {
        if (StringUtil.isBlank(startDate)) {
            startDate = null;
        } else {
            startDate += " 00:00:00";
        }
        if (StringUtil.isBlank(endDate)) {
            endDate = null;
        } else {
            endDate += " 23:59:59";
        }
        if (StringUtil.isBlank(account)) {
            account = null;
        }
        return loginRecordService.getLoginRecords(page, limit, startDate, endDate, account);
    }

}
