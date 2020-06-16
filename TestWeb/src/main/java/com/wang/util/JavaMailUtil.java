package com.wang.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * JavaMailUtil工具类
 *
 * @author Wang926454
 * @date 2018/9/14 15:50
 */
public class JavaMailUtil {

    /**
     * 逗号
     */
    private final static String COMMA = ",";

    /**
     * 分号
     */
    private final static String SEMICOLON = ";";

    /**
     * SSL
     */
    private final static String SSL = "SSL";

    /**
     * TLS
     */
    private final static String TLS = "TLS";

    /**
     * 发送邮件，通过OutLook邮件发送，因为具有TLS加密，采用的是SMTP协议
     * 发送邮件，通过QQ邮件发送，因为具有SSH加密，采用的是SMTP协议
     *
     * @param mailHost      邮件服务器的主机名 "smtp-mail.outlook.com" "smtp.qq.com"
     * @param type          类型 SSL or TLS
     * @param loginAccount  登录邮箱的账号 "xxxxxxxx@outlook.com" "xxxxxxxx@qq.com"
     * @param loginAuthCode 登录邮箱时候需要的密码(授权码)
     * @param name          昵称
     * @param recipients    收件人
     * @param mailSubject   邮件的主题
     * @param mailContent   邮件的内容
     * @return boolean
     * @author Wang926454
     * @date 2018/9/14 16:35
     */
    public static boolean sendEmail(final String mailHost, final String type, final String loginAccount, final String loginAuthCode,
                                    String name, String[] recipients, String mailSubject, String mailContent) {
        try {
            // 跟smtp服务器建立一个连接
            Properties properties = new Properties();
            // 设置邮件服务器主机名
            properties.setProperty("mail.host", mailHost);
            // properties.setProperty("mail.smtp.port", "587");
            // 发送服务器需要身份验证，要采用指定用户名密码的方式去认证
            properties.setProperty("mail.smtp.auth", "true");
            // 发送邮件协议名称
            properties.setProperty("mail.transport.protocol", "smtp");
            // 判断类型是SSL还是TLS
            if (TLS.equals(type)) {
                // 开启TLS加密，否则会失败
                properties.put("mail.smtp.starttls.enable", "true");
            } else if (SSL.equals(type)) {
                // 开启SSL加密，否则会失败
                MailSSLSocketFactory mailSSLSocketFactory = new MailSSLSocketFactory();
                mailSSLSocketFactory.setTrustAllHosts(true);
                properties.put("mail.smtp.ssl.enable", "true");
                properties.put("mail.smtp.ssl.socketFactory", mailSSLSocketFactory);
                // 不做服务器证书校验
                // properties.put("mail.smtp.ssl.checkserveridentity", "false");
            }
            // 创建session
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 发件人邮箱(邮箱别名)，密码(授权码)
                    return new PasswordAuthentication(loginAccount, loginAuthCode);
                }
            });
            // 设置打开调试状态
            session.setDebug(true);
            // 声明一个Message对象(代表一封邮件)，从session中创建
            MimeMessage message = new MimeMessage(session);
            // 设置发件人及昵称
            if (name != null) {
                try {
                    name = javax.mail.internet.MimeUtility.encodeText(name);
                } catch (UnsupportedEncodingException e) {
                    System.out.println("邮件发送失败: " + e.getMessage());
                    return false;
                }
                message.setFrom(new InternetAddress(name + "<" + loginAccount + ">"));
            } else {
                message.setFrom(new InternetAddress(loginAccount));
            }
            // 设置收件人
            InternetAddress[] receptientsEmail = new InternetAddress[recipients.length];
            for (int i = 0, len = recipients.length; i < len; i++) {
                receptientsEmail[i] = new InternetAddress(recipients[i]);
            }
            message.setRecipients(Message.RecipientType.TO, receptientsEmail);
            /**
             * 邮件内容的类型
             * 支持纯文本："text/plain;charset=utf-8"
             * 带有Html格式的内容："text/html;charset=utf-8"
             */
            // 设置主题
            message.setSubject(mailSubject, "utf-8");
            // 设置内容
            message.setContent(mailContent, "text/html;charset=utf-8");
            // 发送
            Transport.send(message);
            System.out.println("邮件发送成功");
        } catch (GeneralSecurityException e) {
            System.out.println("邮件发送失败: " + e.getMessage());
            return false;
        } catch (MessagingException e) {
            System.out.println("邮件发送失败: " + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 方法重载，收件人以String方式传递，逗号或分号分割
     *
     * @return boolean
     * @author Wang926454
     * @date 2018/9/29 16:12
     */
    public static boolean sendEmail(final String mailHost, final String type, final String loginAccount, final String loginAuthCode,
                                    String name, String recipients, String mailSubject, String mailContent) {
        String[] recipientsTemp;
        if (recipients.indexOf(COMMA) != -1) {
            recipientsTemp = recipients.split(COMMA);
        } else if (recipients.indexOf(SEMICOLON) != -1) {
            recipientsTemp = recipients.split(SEMICOLON);
        } else {
            recipientsTemp = new String[1];
            recipientsTemp[0] = recipients;
        }
        return JavaMailUtil.sendEmail(mailHost, type, loginAccount, loginAuthCode, name, recipientsTemp, mailSubject, mailContent);
    }

    public static boolean sendEmail(final String mailHost, final String type, final String loginAccount, final String loginAuthCode,
                                    String[] recipients, String mailSubject, String mailContent) {
        return JavaMailUtil.sendEmail(mailHost, type, loginAccount, loginAuthCode, null, recipients, mailSubject, mailContent);
    }

    public static boolean sendEmail(final String mailHost, final String type, final String loginAccount, final String loginAuthCode,
                                    String recipients, String mailSubject, String mailContent) {
        return JavaMailUtil.sendEmail(mailHost, type, loginAccount, loginAuthCode, null, recipients, mailSubject, mailContent);
    }

}
