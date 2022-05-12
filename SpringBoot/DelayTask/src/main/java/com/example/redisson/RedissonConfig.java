package com.example.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RedissonConfig配置
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/8/14 16:16
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedissonConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedissonConfig.class);

    private String host;

    private int port;

    private String password;

    private int database;

    /**
     * RedissonClient配置
     *
     * @param
     * @return org.redisson.api.RedissonClient
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/8/14 16:25
     */
    @Bean
    public RedissonClient redissonClient() {
        RedissonClient redissonClient = null;
        Config config = new Config();
        String url = "redis://" + host + ":" + port;
        // 单节点配置
        config.useSingleServer().setAddress(url).setDatabase(database);
        // 主从配置
        /*config.useMasterSlaveServers()
                // 设置redis主节点
                .setMasterAddress("redis://192.168.1.120:6379")
                // 设置redis从节点
                .addSlaveAddress("redis://192.168.1.130:6379", "redis://192.168.1.140:6379");*/
        // 哨兵部署方式，sentinel是采用Paxos拜占庭协议，一般sentinel至少3个节点
        /*config.useSentinelServers()
                .setMasterName("my-sentinel-name")
                .addSentinelAddress("redis://192.168.1.120:6379")
                .addSentinelAddress("redis://192.168.1.130:6379")
                .addSentinelAddress("redis://192.168.1.140:6379");*/
        // 集群部署方式，cluster方式至少6个节点，3主3从，3主做sharding，3从用来保证主宕机后可以高可用
        /*config.useClusterServers()
                // 集群状态扫描间隔时间，单位是毫秒
                .setScanInterval(2000)
                .addNodeAddress("redis://192.168.1.120:6379")
                .addNodeAddress("redis://192.168.1.130:6379")
                .addNodeAddress("redis://192.168.1.140:6379")
                .addNodeAddress("redis://192.168.1.150:6379")
                .addNodeAddress("redis://192.168.1.160:6379")
                .addNodeAddress("redis://192.168.1.170:6379");*/
        // 云托管部署方式，这种方式主要解决redis提供商为云服务的提供商的redis连接，比如亚马逊云、微软云
        /*config.useReplicatedServers()
                // 主节点变化扫描间隔时间
                .setScanInterval(2000)
                .addNodeAddress("redis://192.168.1.120:6379")
                .addNodeAddress("redis://192.168.1.130:6379")
                .addNodeAddress("redis://192.168.1.140:6379");*/
        try {
            redissonClient = Redisson.create(config);
            return redissonClient;
        } catch (Exception e) {
            logger.error("RedissonClient Init Url:[{}], Exception: {}", url, e);
            return null;
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }
}
