package com.ruoyi.web.controller.data.domain;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * @author:
 * @date: 2020-10-22 17:43
 */
@Data
public class ImportPortEntity {


    /**
     * obd唯一ID
     */
    @Excel(name = "OBD唯一ID")
    private String obdUniqueId;

    /**
     * obd名称
     */
    @Excel(name = "OBD名称")
    private String obdName;

    /**
     * 端口唯一id
     */
    @Excel(name = "端口唯一ID")
    private String portId;

    /**
     * 端口序号
     */
    @Excel(name = "端口序号")
    private String portCode;


}
