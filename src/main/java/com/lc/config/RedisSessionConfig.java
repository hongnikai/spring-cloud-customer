package com.lc.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 *
 * redis session 单点登录  session配置
 * @author lc
 */

@EnableCaching
@EnableRedisHttpSession
@Configuration
public class RedisSessionConfig {

    @Bean
    public static ConfigureRedisAction configureRedisAction()
    {
        //让springSession不再执行config命令
        return ConfigureRedisAction.NO_OP;
    }
}
