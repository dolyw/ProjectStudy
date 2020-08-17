package com.example.netty;

import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * HashedWheelTimer实现
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/8/14 11:50
 */
public class CustomTimerTask implements TimerTask {

    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * 任务ID
     */
    private final long taskId;

    /**
     * 创建时间戳
     */
    private final long timestamp;

    public CustomTimerTask(long taskId) {
        this.taskId = taskId;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public void run(Timeout timeout) {
        // 异步处理任务
        System.out.println(
                String.format("任务执行时间:%s，任务创建时间:%s，任务ID:%s",
                        LocalDateTime.now().format(F),
                        LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()).format(F),
                        taskId
                )
        );
    }

}
