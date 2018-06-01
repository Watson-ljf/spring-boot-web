package com.watson.system.service;


import com.watson.core.exception.BusinessException;
import com.watson.core.exception.ParameterException;
import com.watson.core.utils.PageResult;
import com.watson.system.model.User;

import java.util.List;

/**
 * UserService
 */
public interface UserService {

    /**
     * 查询所有用户
     */
    PageResult<User> getUsers(int pageNum, int pageSize, Integer status, String searchKey, String searchValue);

    /**
     * 根据账号查询用户
     */
    User getUserByAccount(String userAccount);

    /**
     * 根据id查询用户
     */
    User getUserById(String userId);

    /**
     * 添加用户
     */
    boolean addUser(User user) throws BusinessException;

    /**
     * 修改用户
     */
    boolean updateUser(User user);

    /**
     * 修改用户状态
     */
    boolean updateUserStatus(String userId, int status) throws ParameterException;

    /**
     * 修改密码
     */
    boolean updateUserPsw(String userId, String newPassword);

    /**
     * 删除用户
     */
    boolean deleteUser(String userId) throws BusinessException;

}
