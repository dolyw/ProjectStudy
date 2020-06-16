package com.wang.redis;

import com.wang.model.Item;
import com.wang.util.JedisUtil;
import com.wang.util.common.JsonConvertUtil;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @Desc Redis测试
 * @Author wang926454
 * @Date 2018/5/29 15:32
 */
public class TestRedis {
    @Test
    public void start() {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: " + jedis.ping());
    }

    @Test
    public void TestString() {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        //设置 redis 字符串数据
        jedis.set("runoobkey", "www.runoob.com");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: " + jedis.get("runoobkey"));
        System.out.println("redis 存储的字符串为: " + jedis.get("name"));
    }

    @Test
    public void TestList() {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        //存储数据到列表中
        jedis.lpush("site-list", "Runoob");
        jedis.lpush("site-list", "Google");
        jedis.lpush("site-list", "Taobao");
        // 获取存储的数据并输出
        List<String> list = jedis.lrange("site-list", 0, 2);
        for (int i = 0; i < list.size(); i++) {
            System.out.println("列表项为: " + list.get(i));
        }
        List<String> list2 = jedis.lrange("list", 0, 2);
        for (int i = 0; i < list2.size(); i++) {
            System.out.println("列表项为: " + list2.get(i));
        }
    }

    @Test
    public void TestSet() {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");

        // 获取数据并输出
        Set<String> keys = jedis.keys("*");
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String key = it.next();
            System.out.println(key);
        }
    }

    @Test
    public void TestJedisUtil01() {
        System.out.println(JedisUtil.exists("item"));
        System.out.println(JedisUtil.setObject("wang", new Item(2, "hah"), 300));
        System.out.println(JedisUtil.getObject("wang"));
        System.out.println(JedisUtil.ttl("wang"));
    }

    @Test
    public void TestJedisUtil02() {
        // System.out.println(JedisUtil.setObject("item1", new Item(2, "hah")));
        // System.out.println(JedisUtil.getObject("item1").toString());
        // System.out.println(JedisUtil.delKey("item"));
        System.out.println(JedisUtil.ttl("shiro:refresh_token:wang"));
    }

    @Test
    public void TestJedisUtil03() {
        Item item = new Item(2, "hah");
        System.out.println(JedisUtil.setJson("item", JsonConvertUtil.objectToJson(item), JedisUtil.EXRP_MINUTE));
        System.out.println(JedisUtil.getJson("item"));
        System.out.println(JedisUtil.exists("item"));
    }

    @Test
    public void TestJedisUtil04() {
        Item item = new Item(2, "hah");
        // System.out.println(JedisUtil.setJson("item", JsonConvertUtil.objectToJson(item), JedisUtil.EXRP_MINUTE));
        System.out.println(JedisUtil.exists("item"));
        System.out.println(JedisUtil.delKey("item"));
    }

    @Test
    public void TestJedisUtil05() {
        Set<String> keys = JedisUtil.keysS("shiro:test:*");
        List<String> list = new ArrayList<String>();
        for (String s : keys) {
            if (JedisUtil.exists(s)) {
                list.add(JedisUtil.getJson(s).toString());
            }
        }
        System.out.println(keys);
        System.out.println(list);
    }
}
