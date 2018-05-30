package com.watson.service.core;

import com.watson.service.core.base.BaseService;
import com.watson.service.core.domain.SUser;
import com.watson.service.core.mapper.SUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: Watson
 * description: 用户服务
 * date: 2018/5/30 13:48.
 */
@Service
public class UserService {
    @Autowired
    private SUserMapper sUserMapper;

    public int saveUser(SUser sUser) {
        return sUserMapper.insert(sUser);
    }
}
