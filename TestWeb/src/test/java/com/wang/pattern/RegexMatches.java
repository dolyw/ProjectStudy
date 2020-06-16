package com.wang.pattern;

/**
 * TestWeb
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/3/18 16:15
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches {
    public static void main(String args[]) {

        // 按指定模式在字符串查找
        String line = "ALTER TABLE 12 ADD dsad NULL";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile("(ALTER TABLE[\\s\\S]*ADD).*?(?=NULL)");
        // 现在创建 matcher 对象
        Matcher matcher = r.matcher(line);
        if (matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(0).replace(matcher.group(1), ""));
        } else {
            System.out.println("NO MATCH");
        }
    }
}
