package com.watson.system.dao;

import com.watson.system.model.Permission;
import com.watson.system.model.PermissionExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper {
    int countByExample(PermissionExample example);

    int deleteByExample(PermissionExample example);

    int deleteByPrimaryKey(String permissionId);

    int insert(Permission record);

    int insertSelective(Permission record);

    List<Permission> selectByExample(PermissionExample example);

    Permission selectByPrimaryKey(String permissionId);

    int updateByExampleSelective(@Param("record") Permission record, @Param("example") PermissionExample example);

    int updateByExample(@Param("record") Permission record, @Param("example") PermissionExample example);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    //根据角色id查询所有权限
    List<Permission> selectPermissionByRoleId(@Param("roleId") String roleId);

    List<Permission> selectPermissions(@Param("searchKey") String searchKey, @Param("searchValue") String searchValue);

    List<Permission> selectPermissionsByType(Integer permissionType);

    List<Permission> selectPermissionByUserId(String userId);

    List<Permission> selectButtonParent();

}