package com.wang.util.common;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取IP地址工具
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/9/9 19:19
 */
public class AddrUtil {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(AddrUtil.class);

    /**
     * unknown
     */
    private final static String UNKNOWN_STR = "unknown";

    /**
     * 127.0.0.1
     */
    private final static String LOCALHOST_STR = "127.0.0.1";

    /**
     * 0:0:0:0:0:0:0:1
     */
    private final static String LOCALHOST_IP_STR = "0:0:0:0:0:0:0:1";

    /**
     * 获取客户端IP地址
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (isEmptyIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            if (isEmptyIp(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (isEmptyIp(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                    if (isEmptyIp(ip)) {
                        ip = request.getHeader("X-Real-IP");
                        if (isEmptyIp(ip)) {
                            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                            if (isEmptyIp(ip)) {
                                ip = request.getRemoteAddr();
                                if (LOCALHOST_STR.equals(ip) || LOCALHOST_IP_STR.equals(ip)) {
                                    // 根据网卡取本机配置的IP
                                    ip = getLocalAddr();
                                }
                            }
                        }
                    }
                }
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = ips[index];
                if (!isEmptyIp(ip)) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * IP是否为空
     *
     * @param ip
     * @return
     */
    private static boolean isEmptyIp(String ip) {
        if (StrUtil.isEmpty(ip) || UNKNOWN_STR.equalsIgnoreCase(ip)) {
            return true;
        }
        return false;
    }

    /**
     * 获取本机的IP地址
     */
    public static String getLocalAddr() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("InetAddress.getLocalHost()-error", e);
        }
        return "";
    }
}
