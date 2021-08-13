package com.gzhu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Hello world!
 *
 */
@EnableRedisHttpSession(redisFlushMode = RedisFlushMode.IMMEDIATE)
@SpringBootApplication
@MapperScan("com.gzhu.mapper")
public class SecKillApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(SecKillApplication.class,args);
    }
}
