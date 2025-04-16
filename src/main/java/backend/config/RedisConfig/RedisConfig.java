package backend.config.RedisConfig;

import backend.utils.redis.CustomCacheErrorHandler;
import backend.utils.redis.CustomRedisSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class RedisConfig implements CachingConfigurer {
    private final ConnectionPoolProperties connectionPoolProperties;
    private final RedisProperties redisProperties;

    @Value("${spring.redis.host}")
    protected String redisHost;

    @Value("${spring.redis.port}")
    protected int redisPort;

    @Value("${spring.redis.timeout}")
    protected int redisTimeOut;

    @Override
    public CacheErrorHandler errorHandler() {
        return new CustomCacheErrorHandler();
    }

    @Bean
    public LettucePoolingClientConfiguration poolingClientConfiguration() {
        return LettucePoolingClientConfiguration.builder()
                .poolConfig(connectionPoolProperties.genericObjectPoolConfig())
                .build();
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(LettucePoolingClientConfiguration lettucePoolingClientConfiguration){
        return new LettuceConnectionFactory(redisStandaloneConfiguration(), lettucePoolingClientConfiguration);
    }

    @Override
    @Bean
    public RedisCacheManager cacheManager(){
        return RedisCacheManager
                .builder(this.redisConnectionFactory(poolingClientConfiguration()))
                .cacheDefaults(this.cacheConfiguration())
                .build();
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(redisTimeOut))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(new CustomRedisSerializer()));
    }

    private RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
        if (StringUtils.hasText(redisProperties.getPassword())) {
            redisStandaloneConfiguration.setPassword(redisProperties.getPassword());
        }
        return redisStandaloneConfiguration;
    }

    @Bean(name = "customRedisTemplate")
    public RedisTemplate<Object, Object> customRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setDefaultSerializer(new CustomRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new CustomRedisSerializer());
        redisTemplate.setHashValueSerializer(new CustomRedisSerializer());
        return redisTemplate;
    }

}
