package com.watson.service.redis;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class RedisCacheDB2Service {
    @Resource(name = "redisTemplateDB2")
    private ListOperations<String, String> messageList;
    @Resource(name = "redisTemplateDB2")
    private RedisOperations<String, String> latestMessageExpiration;
    @Resource(name = "redisTemplateDB2")
    private ValueOperations<String, String> kvstore;
    @Resource(name = "redisTemplateDB2")
    private ValueOperations<String, Object> keyValueStore;
    @Resource(name = "redisTemplateDB2")
    private ZSetOperations<String, String> zSetOperations;

    public void putObject(String key, Object value) {
        keyValueStore.set(key, value);
    }

    public Object getObject(String key) {
        return keyValueStore.get(key);
    }

    public void del(String key){
        latestMessageExpiration.delete(key);
    }

    public void put(String key, String value) {
        kvstore.set(key, value);
    }

    public String getAndSet(String key, String value,long liveTime){
        String a= kvstore.getAndSet(key,value);
        latestMessageExpiration.expire(key,liveTime, TimeUnit.SECONDS);
        return a;

    }

    public void put(String key, String value, long expire) {
        kvstore.set(key, value, expire, TimeUnit.SECONDS);
    }

    public void put(String key, String value, long expire, TimeUnit timeUnit) {
        kvstore.set(key, value, expire, timeUnit);
    }

    public String get(String key) {
        return kvstore.get(key);
    }
    public boolean exists(String key){
        return latestMessageExpiration.hasKey(key);
    }


    public Set<String> keys(String pattern) {
        return latestMessageExpiration.keys(pattern);
    }

    public void addMessage(String user, String message) {
        messageList.leftPush(user, message);
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        Date date = Date.from(zonedDateTime.plus(3, ChronoUnit.MINUTES).toInstant());
        latestMessageExpiration.expireAt(user, date);
    }

    public List<String> listMessages(String user) {
        return messageList.range(user, 0, -1);
    }

    public boolean setIfAbsent(String key, String value,long expireTime) {
        boolean result=keyValueStore.setIfAbsent(key,value);
        if(result)
            latestMessageExpiration.expire(key,expireTime,TimeUnit.SECONDS);
        return result;
    }

    public Long ttl(String key){
        return latestMessageExpiration.getExpire(key);
    }

    public String getCacheValue(String key, long liveTime, RedisCacheInterface redisCacheInterface) {
        String strLockKey = key + "_lock";

        String value = get(key);
        if (StringUtils.isEmpty(value)){
            value = redisCacheInterface.getCacheData();
            if (setIfAbsent(strLockKey, "1", 1)){
                put(key, value, liveTime);
                del(strLockKey);
            }
        }else {
            Long restTime = ttl(key);
            if (restTime > 0 && (liveTime/2) > restTime && setIfAbsent(strLockKey, "1", 1)){
                put(key, value, liveTime);
                del(strLockKey);
            }
        }

        return value;
    }

    public Boolean zadd(String key, String member, double score) {
        return zSetOperations.add(key, member, score);
    }

    public Long zadd(String key, Set<ZSetOperations.TypedTuple<String>> tuples) {
        return zSetOperations.add(key, tuples);
    }

    public Long zrevrank(String key, String member) {
        return zSetOperations.reverseRank(key, member);
    }

    public Long zrank(String key, String member) {
        return zSetOperations.rank(key, member);
    }

    public Set<ZSetOperations.TypedTuple<String>> zrevrange(String key, long start, long end) {
        return zSetOperations.reverseRangeWithScores(key, start, end);
    }

    public Double zIncrBy(String key, String member, double score) {
        return zSetOperations.incrementScore(key, member, score);
    }
}
