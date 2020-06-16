package com.wang.other;

import com.sun.mail.util.MailSSLSocketFactory;
import com.wang.util.JavaMailUtil;
import org.junit.jupiter.api.Test;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * JaveMail
 *
 * @author Wang926454
 * @date 2018/9/14 14:28
 */
public class TestJavaMail {

    /**
     * @param
     * @return void
     * @author Wang926454
     * @date 2018/9/14 14:28
     */
    @Test
    public void Test01() throws GeneralSecurityException {
        // 收件人电子邮箱
        String to = "wangldsz@sinosoft.com.cn";
        // 发件人电子邮箱
        String from = "wang9264541@outlook.com";
        // 指定发送邮件的主机为 smtp.qq.com
        // QQ 邮件服务器
        String host = "smtp-mail.outlook.com";
        // 获取系统属性
        Properties properties = System.getProperties();
        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);
        // properties.setProperty("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        // TLS
        properties.put("mail.smtp.starttls.enable", "true");
        // SSL
        // MailSSLSocketFactory sf = new MailSSLSocketFactory();
        // sf.setTrustAllHosts(true);
        // properties.put("mail.smtp.ssl.enable", "true");
        // properties.put("mail.smtp.ssl.socketFactory", sf);
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                // 发件人邮件用户名、密码
                return new PasswordAuthentication("wang9264541@outlook.com", "");
            }
        });
        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));
            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // 主题
            message.setSubject("这是测试");
            // 内容
            message.setText("这是测试");
            // 发送消息
            Transport.send(message);
            System.out.println("Sent message successfully");
        } catch (MessagingException e) {
            e.getMessage();
        }
    }

    @Test
    public void Test2() {
        String mailHost = "smtp.qq.com";
        String type = "SSL";
        String loginAccount = "1107224733@qq.com";
        String loginAuthCode = "f";
        String name = "学长";
        String recipients = "1107224733@qq.com";
        String mailSubject = "节日祝福";
        String mailContent = "节日快乐";
        JavaMailUtil.sendEmail(mailHost, type, loginAccount, loginAuthCode, name, recipients, mailSubject, mailContent);
    }

    @Test
    public void Test3() {
        String mailHost = "smtp-mail.outlook.com";
        String type = "TLS";
        String loginAccount = "wang9264541@outlook.com";
        String loginAuthCode = "9";
        String name = "学长";
        String[] recipients = new String[]{"1107224733@qq.com", "wangldsz@sinosoft.com.cn"};
        String mailSubject = "节日祝福";
        String mailContent = "节日快乐";
        JavaMailUtil.sendEmail(mailHost, type, loginAccount, loginAuthCode, name, recipients, mailSubject, mailContent);
    }
}
