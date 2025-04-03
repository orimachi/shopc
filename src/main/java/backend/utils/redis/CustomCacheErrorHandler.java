package backend.utils.redis;

import backend.enums.EMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

public class CustomCacheErrorHandler implements CacheErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomCacheErrorHandler.class);

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        logger.info(EMessage.GET_CACHE_FAILURE.toString() + cache.getName() + "exception:" + exception.toString());
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        logger.info(EMessage.PUT_CACHE_FAILURE.toString() + cache.getName() + "exception:" + exception.toString());
    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        logger.info(EMessage.EVICT_CACHE_FAILURE.toString() + cache.getName() + "exception:" + exception.toString());
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        logger.info(EMessage.CLEAR_CACHE_FAILURE.toString() + cache.getName() + "exception:" + exception.toString());
    }
}
