package com.example.config;

import cn.hutool.core.util.RandomUtil;
import com.example.snow.generator.IdWorkerUpdate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 雪花算法配置
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/9/2 16:43
 */
@Configuration
public class DistributedIdConfig {

    @Value("${workId:0}")
    private long workerId;

    /**
     * 启动创建
     *
     * @param
     * @return com.pcic.app.generator.IdWorker
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/9/2 16:44
     */
    @Bean
    public IdWorkerUpdate idWorkerUpdate() {
        // 启动随机生成工作机器ID
        // return new IdWorkerUpdate(RandomUtil.randomLong(0, 31), 0L);
        // 启动读取配置工作机器ID
        return new IdWorkerUpdate(workerId, 0L);
    }

}
