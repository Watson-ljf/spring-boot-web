package com.watson.system.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import com.watson.core.controller.BaseController;
import com.watson.core.exception.BusinessException;
import com.watson.core.exception.ParameterException;
import com.watson.core.utils.PageResult;
import com.watson.core.utils.ResultMap;
import com.watson.system.model.User;
import com.watson.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wangfan.endecrypt.utils.EndecryptUtils;
import com.wf.etp.authz.SubjectUtil;
import com.wf.etp.authz.annotation.RequiresRoles;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    /**
     * 查询所有用户
     */
    @GetMapping()
    public PageResult<User> list(Integer page, Integer limit, Integer status, String searchKey, String searchValue) throws UnsupportedEncodingException {
        if (searchValue != null) {
            searchValue = new String(searchValue.getBytes("ISO-8859-1"), "UTF-8");
        }
        if (page == null) {
            page = 0;
            limit = 10;
        }
        return userService.getUsers(page, limit, status, searchKey, searchValue);
    }

    /**
     * 添加用户
     */
    @RequiresRoles("admin")
    @PostMapping()
    public ResultMap add(User user) throws BusinessException {
        user.setUserPassword("123456");
        if (userService.addUser(user)) {
            return ResultMap.ok("添加成功");
        } else {
            return ResultMap.error("添加失败，请重试");
        }
    }

    /**
     * 修改用户
     */
    @RequiresRoles("admin")
    @PutMapping()
    public ResultMap update(User user) {
        if (userService.updateUser(user)) {
            SubjectUtil.getInstance().updateCacheRoles(user.getUserId());
            return ResultMap.ok("修改成功");
        } else {
            return ResultMap.error("修改失败");
        }
    }

    /**
     * 修改用户状态
     */
    @RequiresRoles("admin")
    @PutMapping("status")
    public ResultMap updateStatus(String userId, int status) throws ParameterException {
        if (userService.updateUserStatus(userId, status)) {
            SubjectUtil.getInstance().expireToken(userId);
            return ResultMap.ok();
        } else {
            return ResultMap.error();
        }
    }

    /**
     * 修改自己密码
     */
    @PutMapping("psw")
    public ResultMap updatePsw(String oldPsw, String newPsw, HttpServletRequest request) {
        if (true) {
            return ResultMap.error("演示系统关闭该功能");
        }
        String userId = getUserId(request);
        String encryPsw = EndecryptUtils.encrytMd5(oldPsw, userId, 3);
        User tempUser = userService.getUserById(userId);
        if (tempUser == null || !encryPsw.equals(tempUser.getUserPassword())) {
            return ResultMap.error("旧密码输入不正确");
        }
        if (userService.updateUserPsw(userId, newPsw)) {
            SubjectUtil.getInstance().expireToken(userId);
            return ResultMap.ok();
        } else {
            return ResultMap.error();
        }
    }

    /**
     * 删除用户
     */
    @RequiresRoles("admin")
    @DeleteMapping("/{userId}")
    public ResultMap delete(@PathVariable("userId") String userId) throws BusinessException {
        if (userService.deleteUser(userId)) {
            SubjectUtil.getInstance().expireToken(userId);
            return ResultMap.ok("删除成功");
        } else {
            return ResultMap.error("删除失败");
        }
    }

    /**
     * 重置密码
     */
    @RequiresRoles("admin")
    @PutMapping("psw/{userId}")
    public ResultMap resetPsw(@PathVariable("userId") String userId, HttpServletRequest request) {
        if (userService.updateUserPsw(userId, "123456")) {
            SubjectUtil.getInstance().expireToken(userId);
            return ResultMap.ok();
        } else {
            return ResultMap.error();
        }
    }
}