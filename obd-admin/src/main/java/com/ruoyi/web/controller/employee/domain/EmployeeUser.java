package com.ruoyi.web.controller.employee.domain;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.util.Date;
/**
 * 员工用户资料 实体类
 */
@Data
public class EmployeeUser {
    /** id */
    private Integer id;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 工号 */
    @Excel(name = "工号")
    private String jobNumber;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 创建时间 */
    private Date createTime;
}
