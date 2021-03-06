package com.ruoyi.web.controller.upload.domain;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;
            /**
 * (ObdInfo)实体类
 *
 * @author CrystalWings
 * @since 2020-09-29 15:16:43
 */

@Data
public class ObdInfo {

    /** ${column.comment} */
    @Excel(name = "${column.comment}")
    private Integer id;

    /** 引用盒子主键	 */
    @Excel(name = "引用盒子主键	")
    private Integer boxId;

    /** 数据状态0：正常1：异常 */
    @Excel(name = "数据状态0：正常1：异常")
    private Integer status;

    private Integer portCount;

    @Excel(name = "归属设备")
    private String boxBelong;

    @Excel(name = "OBD名称")
    private String obdName;

    @Excel(name = "归属设备ID")
    private String boxUniqueId;

    @Excel(name = "OBD实物ID")
    private String obdUniqueId;
}