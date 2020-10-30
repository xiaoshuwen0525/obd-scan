package com.ruoyi.web.controller.employee.domain;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

@Data
public class ImportUser {

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 工号 */
    @Excel(name = "工号")
    private String jobNumber;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;
}
