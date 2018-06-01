package com.watson.system.service;

import com.watson.system.model.MenuTree;

import java.util.List;


/**
 * 权限操作相关的service
 */
public interface AuthService {

    /**
     * 根据角色id查询角色的选中和未选中权限
     *
     * @param roleId
     * @return
     */
    List<MenuTree> getPermissionTree(String roleId);

    /**
     * 修改角色的菜单权限
     *
     * @param roleId
     * @param permissionIds
     * @return
     */
    boolean updateRolePermission(String roleId, List<String> permissionIds);

}
