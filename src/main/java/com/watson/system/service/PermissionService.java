package com.watson.system.service;

import com.watson.core.exception.BusinessException;
import com.watson.core.exception.ParameterException;
import com.watson.core.utils.PageResult;
import com.watson.system.model.Permission;

import java.util.List;

/**
 * 菜单Menu操作相关的service
 */
public interface PermissionService {

    /**
     * 获取用户的菜单导航
     */
    List<Permission> getMenusByUser(String userId);

    /**
     * 根据角色id查询权限
     */
    List<Permission> getPermissionsByRole(String roleId);

    /**
     * 查询所有权限
     */
    PageResult<Permission> getPermissions(Integer page, Integer limit, String searchKey, String searchValue);

    /**
     * 查询权限或按钮的父级列表
     */
    List<Permission> getParentPermissions(int type);

    /**
     * 添加权限
     */
    boolean addPermission(Permission permission);

    /**
     * 修改权限
     */
    boolean updatePermission(Permission permission);

    /**
     * 修改权限状态
     */
    boolean updatePermissionStatus(String permissionId, int isDelete) throws ParameterException;

    /**
     * 删除权限
     */
    boolean deletePermission(String permissionId) throws BusinessException;

}
