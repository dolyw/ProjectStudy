package com.wang.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Desc HttpClient测试
 * @Author Wang926454
 * @Date 2018/5/10 15:11
 */
public class TestHttpClient {

    @Test
    public void test() {
        String url = "http://localhost:8081/rateCode";
        // 1.使用默认的配置的httpclient
        CloseableHttpClient client = HttpClients.createDefault();
        // 2.使用get方法
        HttpGet httpGet = new HttpGet(url);
        InputStream inputStream = null;
        CloseableHttpResponse response = null;
        try {
            // 3.执行请求，获取响应
            response = client.execute(httpGet);
            // 看请求是否成功，这儿打印的是http状态码
            System.out.println(response.getStatusLine().getStatusCode());
            // 4.获取响应的实体内容，就是我们所要抓取得网页内容
            HttpEntity entity = response.getEntity();
            // 5.将其打印到控制台上面
            // 方法一：使用EntityUtils
            if (entity != null) {
                System.out.println(EntityUtils.toString(entity, "utf-8"));
            }
            EntityUtils.consume(entity);

            // 方法二  :使用inputStream
            /*if (entity != null) {
                inputStream = entity.getContent();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);

                }
            }*/

        } catch (UnsupportedOperationException | IOException e) {
            e.getMessage();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.getMessage();
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.getMessage();
                }
            }

        }
    }
}
