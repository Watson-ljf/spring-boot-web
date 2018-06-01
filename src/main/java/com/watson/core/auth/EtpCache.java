package com.watson.core.auth;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.watson.core.service.redis.RedisService;
import com.wf.etp.authz.IEtpCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 权限缓存的实现
 */
@Service
public class EtpCache extends IEtpCache {
    @Autowired
    private RedisService redisService;

    @Override
    public List<String> getSet(String key) {
        return redisService.lRange(key, 0, -1);
    }

    @Override
    public boolean putSet(String key, Set<String> values) {
        return redisService.lLeftPushAll(key, values) > 0;
    }

    @Override
    public boolean removeSet(String key, String value) {
        return redisService.lRemove(key, 0, value) > 0;
    }

    @Override
    public boolean delete(String key) {
        redisService.delete(key);
        return true;
    }

    @Override
    public boolean delete(Collection<String> keys) {
        redisService.delete(keys);
        return true;
    }

    @Override
    public Set<String> keys(String pattern) {
        return redisService.keys(pattern);
    }

}
