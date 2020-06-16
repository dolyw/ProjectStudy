package com.wang.util.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Random工具
 *
 * @author Wang926454
 * @date 2018/9/4 14:56
 */
public class RandomUtil {
    /**
     * 随机类
     */
    private static Random RAND = new Random();

    /**
     * 获取日期加上3位随机数的随机数
     *
     * @return
     */
    public static String getRandom() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateNowStr = sdf.format(date);
        int random = RAND.nextInt(900) + 100;
        return dateNowStr + random;
    }

    /**
     * 获取4位随机数
     *
     * @return
     */
    public static String getCode() {
        int random = RAND.nextInt(9000) + 1000;
        return random + "";
    }

    /**
     * 获取日期加8位UUID随机名称
     *
     * @return
     */
    public static String getName() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateNowStr = sdf.format(date);
        String random = getUUID();
        return dateNowStr + random.substring(random.length() - 8);
    }

    /**
     * 生成UUID随机ID
     *
     * @return
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr = str.replace("-", "");
        return uuidStr;
    }
}
