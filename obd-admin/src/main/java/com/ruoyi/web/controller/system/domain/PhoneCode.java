package com.ruoyi.web.controller.system.domain;

import lombok.Data;

import java.util.Date;

/**
 * 手机号验证码 实体类
 *
 */
@Data
public class PhoneCode {
    /**  */
    private Integer id;
    /** 手机号 */
    private String phone;
    /** 验证码 */
    private Integer authCode;
    /** 创建时间 */
    private Date createTime;

}
