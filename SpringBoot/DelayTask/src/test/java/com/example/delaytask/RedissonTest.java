package com.example.delaytask;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * DelayTask
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/2/22 15:53
 */
public class RedissonTest {

    public static final String REDISSON_LOCK = "redisson";
    public static void main(String[] args) throws InterruptedException{
        RedissonClient redissonClient = redissonClient();
        RLock lock = redissonClient.getLock(REDISSON_LOCK);
        lock.lock();

        System.out.println(LocalDateTime.now() + "=> add my-test-lock");
        // newAThread(redissonClient);
        TimeUnit.SECONDS.sleep(41);
        lock.unlock();
        System.out.println(LocalDateTime.now() + "=> delete my-test-lock");
    }

    public static void newAThread(RedissonClient redissonClient){
        new Thread(() ->{
            while (true){

                try {
                    RLock anotherLock = redissonClient.getLock(REDISSON_LOCK);
                    boolean lockSuccess = anotherLock.tryLock(1, -1, TimeUnit.SECONDS);
                    if(!lockSuccess){
                        System.out.println(LocalDateTime.now() + "-> try lock failed");
                    }else{
                        System.out.println(LocalDateTime.now() + "-> try lock success");
                        anotherLock.unlock();
                        System.out.println(LocalDateTime.now() + "-> delete lock success");
                        return;
                    }
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }).start();


    }


    private static RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        config.setLockWatchdogTimeout(20*1000);
        return Redisson.create(config);
    }
}
