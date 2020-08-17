package com.example.netty;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * RunHashedWheelTimer运行
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/8/14 11:54
 */
public class RunHashedWheelTimer {

    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static void main(String[] args) throws Exception {

        ThreadFactory factory = r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("HashedWheelTimerWorker");
            return thread;
        };

        /**
         * @param tickDuration 每tick一次的时间间隔
         * @param unit tickDuration的时间单位
         * @param ticksPerWheel 时间轮中的槽数
         * @param leakDetection 检查内存溢出
         */
        Timer timer = new HashedWheelTimer(factory, 1,
                TimeUnit.SECONDS, 100, true);
        System.out.println(String.format("开始任务时间:%s", LocalDateTime.now().format(F)));

        // 任务ID-6，5秒后执行
        TimerTask timerTask = new CustomTimerTask(6);
        timer.newTimeout(timerTask, 5, TimeUnit.SECONDS);

        Thread.sleep(Integer.MAX_VALUE);
    }

}
