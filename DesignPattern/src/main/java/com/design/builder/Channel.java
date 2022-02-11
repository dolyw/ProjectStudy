package com.design.builder;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 渠道
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/2/11 14:13
 */
@Data
@Builder
public class Channel {

    /** 主键ID */
    private Long channelId;

    /** 渠道代码 */
    private String channelCode;

    /** 渠道名称 */
    private String channelName;

    /** 渠道类型 */
    private Integer channelType;

    /** 渠道状态：1-待审核 2-审核通过 3-合作中 4-暂停合作 5-废弃 */
    private Integer status;

    /** 渠道生效日期 */
    private Date effectiveDate;

    /** 渠道失效日期 */
    private Date invalidDate;

    /** 渠道联系人 */
    private String channelContactName;

    /** 渠道联系电话 */
    private String channelContactPhone;

    /** 渠道联系邮箱 */
    private String channelContactEmail;

    /** 创建人 */
    private String createdBy;

    /** 创建时间 */
    private Date createdTime;

}