package com.wang.util;

import cn.hutool.core.exceptions.ExceptionUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * OkHttpUtil
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/8/23 10:08
 */
public class OkHttpUtil {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);

    /**
     * MEDIA_TYPE_JSON
     */
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * MEDIA_TYPE_TEXT
     */
    private static final MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/html;charset=utf-8");

    /**
     * Get请求
     *
     * @param url
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/8/23 10:18
     */
    public static String get(String url) {
        String result = null;
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).build();
        logger.info("请求地址:{}", url);
        try (Response response = client.newCall(request).execute()) {
            result = response.body().string();
            logger.info("请求地址:{}，请求结果:{}", url, result);
        } catch (Exception e) {
            logger.error("请求地址:{}，请求异常:{}", url, ExceptionUtil.stacktraceToOneLineString(e));
        }
        return result;
    }

    /**
     * postText请求
     *
     * @param url
	 * @param data 提交的参数为key=value&key1=value1的形式
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/8/23 10:18
     */
    public static String postText(String url, String data) {
        String result = null;
        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_TEXT, data);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        logger.info("请求地址:{}，请求参数:{}", url, data);
        try (Response response = httpClient.newCall(request).execute()) {
            result = response.body().string();
            logger.info("请求地址:{}，请求参数:{}，请求结果:{}", url, data, result);
        } catch (IOException e) {
            logger.error("请求地址:{}，请求参数:{}，请求异常:{}", url, data, ExceptionUtil.stacktraceToOneLineString(e));
        }
        return result;
    }

    /**
     * postJson请求
     *
     * @param url
	 * @param json json串
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/8/23 10:19
     */
    public static String postJson(String url, String json) {
        String result = null;
        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        logger.info("请求地址:{}，请求参数:{}", url, json);
        try {
            Response response = httpClient.newCall(request).execute();
            result = response.body().string();
            logger.info("请求地址:{}，请求参数:{}，请求结果:{}", url, json, result);
        } catch (IOException e) {
            logger.error("请求地址:{}，请求参数:{}，请求异常:{}", url, json, ExceptionUtil.stacktraceToOneLineString(e));
        }
        return result;
    }

}
