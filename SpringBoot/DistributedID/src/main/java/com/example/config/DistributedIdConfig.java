package com.example.config;

import cn.hutool.core.util.RandomUtil;
import com.example.snow.generator.IdWorkerUpdate;
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

    /**
     * 启动随机生成数据中心ID和WordID，每次重新启动应用尾号段进行刷新
     *
     * @param
     * @return com.pcic.app.generator.IdWorker
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/9/2 16:44
     */
    @Bean
    public IdWorkerUpdate idWorkerPatch() {
        return new IdWorkerUpdate(RandomUtil.randomLong(0, 15), RandomUtil.randomLong(0, 15), 0L);
    }

}
