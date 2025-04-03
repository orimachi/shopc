package backend.config.RedisConfig;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class ConnectionPoolProperties {
    @Value("${maxIdle:64}")
    private Integer maxIdle;

    @Value("${maxTotal:64}")
    private Integer maxTotal;

    @Value("${minIdle:8}")
    private Integer minIdle;

    @Bean
    public GenericObjectPoolConfig<Object> genericObjectPoolConfig() {
        GenericObjectPoolConfig<Object> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMinIdle(minIdle);
        return poolConfig;
    }
}
