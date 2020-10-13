package com.ruoyi.upload.domain;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * (ObdPortInfo)实体类
 *
 * @author CrystalWings
 * @since 2020-09-29 15:16:43
 */

@Data
public class ObdPortInfoVO {

    private static final long serialVersionUID = 1L;

    private String seq;

    /**
     * ${column.comment}
     */
    private Integer id;

    /**
     * 引用obd主键
     */
    private Integer obdId;

    /**
     * 序号：取值范围（1-8）
     */
    private Integer portSer;

    /**
     * obd端口串码
     */
    private String portCode;

    /**
     * 数据状态0：正常1：异常
     */
    private Integer status;

    /**
     * 图片路径
     */
    private String imgUrl;

}