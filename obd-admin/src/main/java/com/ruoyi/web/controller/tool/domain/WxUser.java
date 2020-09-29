package com.ruoyi.web.controller.tool.domain;

import java.util.Date;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;
                        
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * (WxUser)实体类
 *
 * @author CrystalWings
 * @since 2020-09-29 15:16:43
 */
 
@Data
public class WxUser  {

    private static final long serialVersionUID = 1L;


        /** ${column.comment} */
        @Excel(name = "${column.comment}")
        private Integer id;
    
        /** 工号 */
        @Excel(name = "工号")
        private String jobNumber;
    
        /** 微信openId */
        @Excel(name = "微信openId")
        private String wxOpenId;
    
        /** 手机号 */
        @Excel(name = "手机号")
        private String phone;
    
        /** ${column.comment} */
        @Excel(name = "${column.comment}")
        @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        private Date createTime;
    
}