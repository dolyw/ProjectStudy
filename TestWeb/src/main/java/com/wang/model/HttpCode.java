package com.wang.model;

/**
 * 响应码枚举，参考HTTP状态码的语义
 *
 * @author Wang926454
 * @date 2018/11/13 18:17
 */
public enum HttpCode {
    // 成功
    SUCCESS(200),
    // 失败
    FAIL(400),
    // 未认证
    UNAUTHORIZED(401),
    // 接口不存在
    NOT_FOUND(404),
    // 服务器内部错误
    INTERNAL_SERVER_ERROR(500);

    private final int code;

    HttpCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
