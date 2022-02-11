package com.design.builder;

import java.util.Date;

/**
 * 应用
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/2/11 14:13
 */
public class App {

    /** 主键ID */
    private Long appId;

    /** 应用代码 */
    private String appCode;

    /** 应用名称 */
    private String appName;

    /** 应用私钥 */
    private String appSecret;

    /** 应用类型 */
    private String appType;

    /** 创建人 */
    private String createdBy;

    /** 创建时间 */
    private Date createdTime;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 建造者模式
     *
     * @return
     */
    public static Builder Builder() {
        return new Builder();
    }

    /**
     * 建造者模式 - Builder内部类
     */
    public static final class Builder {
        private Long appId;
        private String appCode;
        private String appName;
        private String appSecret;
        private String appType;
        private String createdBy;
        private Date createdTime;

        private Builder() {
        }

        public Builder withAppId(Long appId) {
            this.appId = appId;
            return this;
        }

        public Builder withAppCode(String appCode) {
            this.appCode = appCode;
            return this;
        }

        public Builder withAppName(String appName) {
            this.appName = appName;
            return this;
        }

        public Builder withAppSecret(String appSecret) {
            this.appSecret = appSecret;
            return this;
        }

        public Builder withAppType(String appType) {
            this.appType = appType;
            return this;
        }

        public Builder withCreatedBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder withCreatedTime(Date createdTime) {
            this.createdTime = createdTime;
            return this;
        }

        public App build() {
            App app = new App();
            app.setAppId(appId);
            app.setAppCode(appCode);
            app.setAppName(appName);
            app.setAppSecret(appSecret);
            app.setAppType(appType);
            app.setCreatedBy(createdBy);
            app.setCreatedTime(createdTime);
            return app;
        }
    }
}
