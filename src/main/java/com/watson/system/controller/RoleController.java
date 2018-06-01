package com.watson.system.controller;

import java.io.UnsupportedEncodingException;

import com.watson.core.exception.BusinessException;
import com.watson.core.exception.ParameterException;
import com.watson.core.utils.PageResult;
import com.watson.core.utils.ResultMap;
import com.watson.system.model.Role;
import com.watson.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wf.etp.authz.annotation.RequiresPermissions;

/**
 * 角色管理
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 查询所有角色
     */
    @GetMapping()
    public PageResult<Role> list(Integer page, Integer limit, String searchKey, String searchValue, Integer isDelete) throws UnsupportedEncodingException {
        if (searchValue != null) {
            searchValue = new String(searchValue.getBytes("ISO-8859-1"), "UTF-8");
        }
        if (page == null) {
            page = 0;
            limit = 10;
        }
        return roleService.getRoles(page, limit, searchKey, searchValue, isDelete);
    }

    /**
     * 添加角色
     */
    @RequiresPermissions("system/role")
    @PostMapping()
    public ResultMap add(Role role) {
        if (roleService.addRole(role)) {
            return ResultMap.ok("添加成功！");
        } else {
            return ResultMap.error("添加失败！");
        }
    }

    /**
     * 修改角色
     */
    @RequiresPermissions("system/role")
    @PutMapping()
    public ResultMap update(Role role) {
        if (roleService.updateRole(role)) {
            return ResultMap.ok("修改成功！");
        } else {
            return ResultMap.error("修改失败！");
        }
    }

    /**
     * 修改状态
     */
    @RequiresPermissions("system/role")
    @PutMapping("/status")
    public ResultMap updateStatus(String roleId, int status)
            throws ParameterException {
        if (roleService.updateRoleStatus(roleId, status)) {
            return ResultMap.ok();
        } else {
            return ResultMap.error();
        }
    }

    /**
     * 删除角色
     */
    @RequiresPermissions("system/role")
    @DeleteMapping("/{id}")
    public ResultMap delete(@PathVariable("id") String roleId) throws BusinessException {
        if (roleService.deleteRole(roleId)) {
            return ResultMap.ok("删除成功");
        }
        return ResultMap.error("删除失败");
    }
}