package com.watson.system.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.watson.core.exception.BusinessException;
import com.watson.core.exception.ParameterException;
import com.watson.core.utils.JSONUtil;
import com.watson.core.utils.PageResult;
import com.watson.core.utils.ResultMap;
import com.watson.system.model.MenuTree;
import com.watson.system.model.Permission;
import com.watson.system.service.AuthService;
import com.watson.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wf.etp.authz.SubjectUtil;
import com.wf.etp.authz.annotation.RequiresPermissions;
import com.wf.etp.authz.annotation.RequiresRoles;

/**
 * 菜单管理
 */
@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    @Autowired
    private PermissionService menuService;
    @Autowired
    private AuthService authService;

    /**
     * 查询所有菜单
     */
    @GetMapping()
    public PageResult<Permission> list(Integer page, Integer limit, String searchKey, String searchValue) throws UnsupportedEncodingException {
        if (searchValue != null) {
            searchValue = new String(searchValue.getBytes("ISO-8859-1"), "UTF-8");
        }
        if (page == null) {
            page = 0;
            limit = 0;
        }
        return menuService.getPermissions(page, limit, searchKey, searchValue);
    }

    /**
     * 角色权限菜单树
     */
    @GetMapping("/tree/{roleId}")
    public List<MenuTree> listPermTree(@PathVariable("roleId") String roleId) {
        return authService.getPermissionTree(roleId);
    }

    /**
     * 修改角色权限
     */
    @RequiresRoles("admin")
    @PutMapping("/tree")
    public ResultMap updatePermTree(String roleId, String permIds) {
        List<String> permissionIds = JSONUtil.parseArray(permIds);
        if (authService.updateRolePermission(roleId, permissionIds)) {
            SubjectUtil.getInstance().updateCachePermission();
            return ResultMap.ok("修改成功");
        } else {
            return ResultMap.error("修改失败");
        }
    }

    /**
     * 查询所有的父菜单
     */
    @GetMapping("/parent/{type}")
    public List<Permission> listParent(@PathVariable("type") int type) {
        return menuService.getParentPermissions(type);
    }

    /**
     * 添加菜单
     */
    @RequiresRoles("admin")
    @PostMapping()
    public ResultMap add(Permission permission) {
        if (menuService.addPermission(permission)) {
            return ResultMap.ok("添加成功！");
        } else {
            return ResultMap.error("添加失败！");
        }
    }

    /**
     * 修改菜单
     */
    @RequiresRoles("admin")
    @PutMapping()
    public ResultMap update(Permission permission) {
        if (menuService.updatePermission(permission)) {
            return ResultMap.ok("修改成功！");
        } else {
            return ResultMap.error("修改失败！");
        }
    }

    /**
     * 修改状态
     */
    @RequiresRoles("admin")
    @PutMapping("status")
    public ResultMap updateStatus(String permissionId, int status) throws ParameterException {
        if (menuService.updatePermissionStatus(permissionId, status)) {
            return ResultMap.ok();
        }
        return ResultMap.error();
    }

    /**
     * 删除
     */
    @RequiresPermissions("system/permission")
    @DeleteMapping("/{permissionId}")
    public ResultMap delete(@PathVariable("permissionId") String permissionId) throws BusinessException {
        if (menuService.deletePermission(permissionId)) {
            return ResultMap.ok("删除成功");
        }
        return ResultMap.error("删除失败");
    }

}
