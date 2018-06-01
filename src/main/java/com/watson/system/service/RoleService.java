package com.watson.system.service;


import com.watson.core.exception.BusinessException;
import com.watson.core.exception.ParameterException;
import com.watson.core.utils.PageResult;
import com.watson.system.model.Role;

/**
 * 角色操作相关的service
 */
public interface RoleService {

    /**
     * 查询所有角色
     */
    PageResult<Role> getRoles(int pageNum, int pageSize, String searchKey, String searchValue, Integer isDelete);

    /**
     * 根据id查询角色
     */
    Role getRoleById(String roleId);

    /**
     * 添加角色
     */
    boolean addRole(Role role);

    /**
     * 修改角色
     */
    boolean updateRole(Role role);

    /**
     * 修改角色状态
     */
    boolean updateRoleStatus(String roleId, int isDelete) throws ParameterException;

    /**
     * 删除角色
     */
    boolean deleteRole(String roleId) throws BusinessException;

}
