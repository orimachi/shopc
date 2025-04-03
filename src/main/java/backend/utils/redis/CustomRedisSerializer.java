package backend.utils.redis;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.text.MessageFormat;

@RequiredArgsConstructor
public class CustomRedisSerializer implements RedisSerializer<Object> {
    private static final Logger logger = LoggerFactory.getLogger(CustomRedisSerializer.class);
    private final ObjectMapper mapper;

    public CustomRedisSerializer() {
        this.mapper = new ObjectMapper();
        this.mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(),ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
    }

    @Override
    public byte[] serialize(Object value) throws SerializationException {
        if (value == null) {
            return new byte[0];
        }
        try {
            return mapper.writeValueAsBytes(value);
        } catch (JsonProcessingException e) {
            logger.error("JsonProcessingException while compressing value to redis {}",
                    e.getMessage(), e);
        }
        return new byte[0];
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if(bytes == null || bytes.length == 0) {
            return null;
        }

        try {
            return mapper.readValue(bytes, Object.class);
        } catch (Exception ex) {
            throw new SerializationException(MessageFormat.format("Could not read JSON: {0}", ex.getMessage()), ex);
        }
    }
}
