package com.ruoyi.web.controller.upload.domain;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;
                                        /**
 * (ObdInfoHistory)实体类
 *
 * @author CrystalWings
 * @since 2020-12-27 14:21:28
 */
 
@Data
public class ObdInfoHistory {

    private static final long serialVersionUID = 1L;


        /** ${column.comment} */
        @Excel(name = "${column.comment}")
        private Integer id;
    
        /** 引用盒子主键	 */
        @Excel(name = "引用盒子主键	")
        private Integer boxId;
    
        /** 数据状态0：正常1：异常 */
        @Excel(name = "数据状态0：正常1：异常")
        private Integer status;
    
        /** 归属设备 */
        @Excel(name = "归属设备")
        private String boxBelong;
    
        /** OBD名称 */
        @Excel(name = "OBD名称")
        private String obdName;
    
        /** 端口 */
        @Excel(name = "端口")
        private Integer portCount;
    
        /** 归属设备ID  箱子ID 唯一 */
        @Excel(name = "归属设备ID  箱子ID 唯一")
        private String boxUniqueId;
    
        /** OBD实物ID 唯一 */
        @Excel(name = "OBD实物ID 唯一")
        private String obdUniqueId;
    
        /** 合格状态 0 不合格 1合格 -无 */
        @Excel(name = "合格状态 0 不合格 1合格 -无")
        private String checkState;
    
        /** 备注信息 */
        @Excel(name = "备注信息")
        private String remarks;
    
}