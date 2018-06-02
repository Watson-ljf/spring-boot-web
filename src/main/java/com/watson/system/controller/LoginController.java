package com.watson.system.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.wangfan.endecrypt.utils.EndecryptUtils;
import com.watson.core.controller.BaseController;
import com.watson.core.utils.DateUtil;
import com.watson.core.utils.ResultMap;
import com.watson.core.utils.StringUtil;
import com.watson.core.utils.UserAgentGetter;
import com.watson.system.model.LoginRecord;
import com.watson.system.model.User;
import com.watson.system.service.LoginRecordService;
import com.watson.system.service.PermissionService;
import com.watson.system.service.UserService;
import com.wf.captcha.utils.CaptchaUtil;
import com.wf.etp.authz.SubjectUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 */
@RestController
@RequestMapping("/api")
public class LoginController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private LoginRecordService loginRecordService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 登录
     */
    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "用户名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vercode", value = "验证码（用户输入）", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "verkey", value = "验证码对应的key", dataType = "String", paramType = "query")
    })
    @PostMapping("/login")
    public ResultMap login(String account, String password, String vercode, String verkey, HttpServletRequest request) {
        if (StringUtil.isBlank(account, password, vercode, verkey)) {
            return ResultMap.error("请输入完整信息");
        }
        if (!CaptchaUtil.isVerified(verkey, vercode, request)) {
            return ResultMap.error("验证码错误");
        }
        User loginUser = userService.getUserByAccount(account);
        if (loginUser == null) {
            return ResultMap.error("账号不存在");
        }
        if (loginUser.getUserStatus() != 0) {
            return ResultMap.error("账号被锁定");
        }
        String encryPsw = EndecryptUtils.encrytMd5(password, loginUser.getUserId(), 3);
        if (!loginUser.getUserPassword().equals(encryPsw)) {
            return ResultMap.error("密码错误");
        }
        // 添加到登录日志
        addLoginRecord(request, loginUser.getUserId());
        String token = SubjectUtil.getInstance().createToken(loginUser.getUserId(), DateUtil.getAppointDate(new Date(), 30));
        loginUser.setUserPassword(null);
        return ResultMap.ok("登录成功").put("user", loginUser).put("token", token);
    }

    /**
     * 获取用户的菜单
     */
    @GetMapping("/menu")
    public ResultMap navMenu(HttpServletRequest request) {
        return ResultMap.ok().put("menus", permissionService.getMenusByUser(getUserId(request)));
    }

    /**
     * 退出登录
     */
    @DeleteMapping("/login")
    public ResultMap loginOut(HttpServletRequest request) {
        SubjectUtil.getInstance().expireToken(getUserId(request), getToken(request));
        return ResultMap.ok();
    }

    /**
     * 添加登录日志
     */
    private void addLoginRecord(HttpServletRequest request, String userId) {
        UserAgentGetter agentGetter = new UserAgentGetter(request);
        // 添加到登录日志
        LoginRecord loginRecord = new LoginRecord();
        loginRecord.setUserId(userId);
        loginRecord.setIpAddress(agentGetter.getIpAddr());
        loginRecord.setDevice(agentGetter.getDevice());
        loginRecord.setBrowserType(agentGetter.getBrowser());
        loginRecord.setOsName(agentGetter.getOS());
        loginRecordService.addLoginRecord(loginRecord);
    }

}