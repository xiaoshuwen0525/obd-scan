package com.ruoyi.web.controller.upload.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
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
public class ObdPicture {

        /** ${column.comment} */
        private Integer id;
    
        /** 盒子串码 */
        private String boxCode;
    
        /** ${column.comment} */
        private String imgUrl;

        /** 工号 */
        private String jobNumber;

        /** 倒灌标签二维码 */
        private String labelCode;

}