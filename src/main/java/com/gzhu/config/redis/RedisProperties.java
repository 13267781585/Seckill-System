package com.gzhu.config.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class RedisProperties {
    private int expireSeconds;
    private String nodes;
    private int commandTimeout;

    public int getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public int getCommandTimeout() {
        return commandTimeout;
    }

    public void setCommandTimeout(int commandTimeout) {
        this.commandTimeout = commandTimeout;
    }

    @Override
    public String toString() {
        return "RedisProperties{" +
                "expireSeconds=" + expireSeconds +
                ", nodes='" + nodes + '\'' +
                ", commandTimeout=" + commandTimeout +
                '}';
    }
}
