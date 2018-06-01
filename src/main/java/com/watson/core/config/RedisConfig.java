package com.watson.core.config;

import io.lettuce.core.ClientOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public CacheManager cacheManager(@Qualifier("connectionFactoryDB2") RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.create(redisConnectionFactory);
    }

    @Bean(name = "redisTemplateDB2")
    public StringRedisTemplate redisTemplateDB1(@Qualifier("connectionFactoryDB2") RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean(name = "connectionFactoryDB2")
    public RedisConnectionFactory connectionFactoryDB2(@Value("${spring.redis.host}") String host,
                                                       @Value("${spring.redis.port}") int port,
                                                       @Value("${spring.redis.password}") String password,
                                                       @Value("${spring.redis.database}") int database){
        return connectionFactory(host, port, database, password);
    }

    public RedisConnectionFactory connectionFactory(String hostName, int port, int database, String password) {
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                // .useSsl().and()
                .commandTimeout(Duration.ofSeconds(2))
                .shutdownTimeout(Duration.ZERO)
                .clientOptions(ClientOptions.builder().pingBeforeActivateConnection(true).build())
                .build();

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(hostName, port);
        redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
    }

}
