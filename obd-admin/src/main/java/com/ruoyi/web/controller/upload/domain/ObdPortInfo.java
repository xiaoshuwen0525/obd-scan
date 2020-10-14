package com.ruoyi.web.controller.upload.domain;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;
                        /**
 * (ObdPortInfo)实体类
 *
 * @author CrystalWings
 * @since 2020-09-29 15:16:43
 */
 
@Data
public class ObdPortInfo  {

    private static final long serialVersionUID = 1L;


        /** ${column.comment} */
        @Excel(name = "${column.comment}")
        private Integer id;
    
        /** 引用obd主键	 */
        @Excel(name = "引用obd主键	")
        private Integer obdId;
    
        /** 序号：取值范围（1-8）	 */
        @Excel(name = "序号：取值范围（1-8）	")
        private Integer portSer;
    
        /** obd端口串码	 */
        @Excel(name = "obd端口串码	")
        private String portCode;
    
        /** 数据状态0：正常1：异常 */
        @Excel(name = "数据状态0：正常1：异常")
        private Integer status;
    
        /** 图片路径	 */
        @Excel(name = "图片路径	")
        private String imgUrl;
    
}