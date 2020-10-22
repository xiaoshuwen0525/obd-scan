package com.ruoyi.web.controller.upload.domain;


import com.ruoyi.common.annotation.Excel;

                                    
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * (ObdBox)实体类
 *
 * @author CrystalWings
 * @since 2020-09-29 15:16:43
 */
 
@Data
public class ObdBox  {

    private static final long serialVersionUID = 1L;


        /** ${column.comment} */
        @Excel(name = "${column.comment}")
        private Integer id;
    
        /** 盒子串码 */
        @Excel(name = "盒子串码")
        private String boxCode;
    
        /** ${column.comment} */
        @Excel(name = "${column.comment}")
        private String imgUrl;
    
        /** 数据状态：0:正常 1:异常 */
        @Excel(name = "数据状态：0:正常 1:异常")
        private Integer status;
    
        /** 异常类型：1：盒子异常；2：OBD异常	 */
        @Excel(name = "异常类型：1：盒子异常；2：OBD异常	")
        private Integer exceptionType;
    
        /** 异常信息	 */
        @Excel(name = "异常信息	")
        private String exceptionInfo;
    
        /** 工号 */
        @Excel(name = "工号")
        private String jobNumber;
    
        /** ${column.comment} */
        @Excel(name = "${column.comment}")
        @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        private Date createTime;

        /** 倒灌标签二维码 */
        private String labelCode;

}