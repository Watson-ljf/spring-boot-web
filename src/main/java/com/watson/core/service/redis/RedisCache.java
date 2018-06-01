package com.watson.core.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * author: Watson
 * description: Redis缓存实现
 * date: 2018/5/31 11:07.
 */
@Service
public class RedisCache {
    @Autowired
    private RedisService redisService;

    /**
     * Redis缓存
     *
     * @param key
     * @param liveTime
     * @param redisCacheInterface 具体需要缓冲的数据源
     * @return
     */
    public String getCacheValue(String key, long liveTime, RedisCacheInterface redisCacheInterface) {
        String strLockKey = key + "_lock";

        String value = redisService.get(key);
        if (StringUtils.isEmpty(value)) {
            value = redisCacheInterface.getCacheData();
            if (redisService.setIfAbsent(strLockKey, "1", 1)) {
                redisService.set(key, value, liveTime);
                redisService.delete(strLockKey);
            }
        } else {
            Long restTime = redisService.getExpire(key);
            if (restTime > 0 && (liveTime / 2) > restTime && redisService.setIfAbsent(strLockKey, "1", 1)) {
                redisService.set(key, value, liveTime);
                redisService.delete(strLockKey);
            }
        }

        return value;
    }
}
