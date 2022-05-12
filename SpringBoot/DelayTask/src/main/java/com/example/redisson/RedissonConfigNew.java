package com.example.redisson;

import cn.hutool.core.util.StrUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RedissonConfig配置
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/8/14 16:16
 */
// @Configuration
public class RedissonConfigNew {

    private static final Logger logger = LoggerFactory.getLogger(RedissonConfig.class);

    @Value("${spring.redis.hostName}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private int database;

    /**
     * RedissonClient单机配置
     *
     * @param
     * @return org.redisson.api.RedissonClient
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/8/14 16:25
     */
    @Bean
    @ConditionalOnProperty(name = {"spring.redis.cluster"}, havingValue = "false")
    public RedissonClient redissonClientSingle() {
        RedissonClient redissonClient = null;
        Config config = new Config();
        String url = "redis://" + host + ":" + port;
        // 单节点配置
        config.useSingleServer().setAddress(url).setDatabase(database);
        if (StrUtil.isNotBlank(password)) {
            config.useSingleServer().setPassword(password);
        }
        try {
            redissonClient = Redisson.create(config);
            return redissonClient;
        } catch (Exception e) {
            logger.error("RedissonClient Init Url:[{}], Exception: {}", url, e);
            return null;
        }
    }

    @Value("${spring.redis.cluster.nodes}")
    private String cluster;

    /**
     * RedissonClient集群配置
     *
     * @param
     * @return org.redisson.api.RedissonClient
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/8/14 16:25
     */
    @Bean
    @ConditionalOnProperty(name = {"spring.redis.cluster"}, havingValue = "true")
    public RedissonClient redissonClientCluster() {
        RedissonClient redissonClient = null;
        Config config = new Config();
        // 集群状态扫描间隔时间，单位是毫秒
        config.useClusterServers().setScanInterval(2000);
        if (StrUtil.isNotBlank(password)) {
            config.useClusterServers().setPassword(password);
        }
        String[] serverArray = cluster.split(",");
        for (String ipPort : serverArray) {
            String[] ipAndPort = ipPort.split(":");
            String url = "redis://" + ipAndPort[0].trim() + ":" + ipAndPort[1].trim();
            config.useClusterServers().addNodeAddress(url);
        }
        try {
            redissonClient = Redisson.create(config);
            return redissonClient;
        } catch (Exception e) {
            logger.error("RedissonClient init redis url:[{}], Exception:", cluster, e);
            return null;
        }
    }
}
