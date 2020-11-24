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
    @Excel(name = "OBDID")
    private String obdUniqueId;

    /**
     * obd名称
     */
    @Excel(name = "OBDNAME")
    private String obdName;

    /**
     * 端口唯一id
     */
    @Excel(name = "OBDPORTID")
    private String portId;

    /**
     * 端口序号
     */
    @Excel(name = "OBDPORTCODE")
    private String portCode;


}
