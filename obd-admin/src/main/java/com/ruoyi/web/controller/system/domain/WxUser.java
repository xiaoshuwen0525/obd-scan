package com.ruoyi.web.controller.system.domain;

import lombok.Data;

import java.util.Date;
/**
 * 用户 实体类
 *
 */
@Data
public class WxUser {
    /** id */
    private Integer id;
    /** 工号 */
    private String jobNumber;
    /** openid */
    private String wxOpenId;
    /** sessionKey */
    private String sessionKey;
    /** 手机号 */
    private String phone;
    /** 创建时间 */
    private Date createTime;


}
