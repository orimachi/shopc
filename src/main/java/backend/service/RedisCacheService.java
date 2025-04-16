package backend.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheService {
    private final RedisTemplate<Object,Object> redisTemplate;

    public RedisCacheService(@Qualifier("customRedisTemplate") RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T> T getSimple(Object key){
        return (T) redisTemplate.opsForValue().get(key);
    }

    public void putSimple(Object key, Object value){
        redisTemplate.opsForValue().set(key,value);
    }
    public void deleteSimple(Object key){
        redisTemplate.delete(key);
    }

    public void putSimpleWithTTL(Object key, Object value, Long TTL){
        redisTemplate.opsForValue().set(key,value);
        redisTemplate.expire(key,TTL, TimeUnit.SECONDS);
    }

    public <T> T getHashKey(Object key,Object hashKey){
        return (T) redisTemplate.opsForHash().get(key,hashKey);
    }

    public void putHashKey(Object key, Object value, Object hashKey){
        redisTemplate.opsForHash().put(key,hashKey,value);
    }

    public void deleteHashKey(Object key, Object hashKey){
        redisTemplate.opsForHash().delete(key,hashKey);
    }

    public void putHashKeyWithTTL(Object key, Object value, Object hashKey,Long TTL){
        redisTemplate.opsForHash().put(key,hashKey,value);
        redisTemplate.expire(hashKey,TTL,TimeUnit.SECONDS);
    }

}
