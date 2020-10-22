package com.ruoyi.web.controller.obd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author: 曾志伟
 * @date: 2020-10-22 17:43
 */
@Data
public class ImportEntity{

    /**
     * 区域
     */
    @Excel(name = "区域", type = Excel.Type.IMPORT)
    private String area;

    /**
     * 业务局
     */
    @Excel(name = "业务局", type = Excel.Type.IMPORT)
    private String businessBureau;

    /**
     * 营服中心
     */
    @Excel(name = "营服中心", type = Excel.Type.IMPORT)
    private String campService;

    /**
     * 盒子串码
     */
    @Excel(name = "盒子串码", type = Excel.Type.IMPORT)
    private String boxCode;

    /**
     * 倒灌标签二维码
     */
    @Excel(name = "倒灌标签二维码", type = Excel.Type.IMPORT)
    private String labelCode;

    /**
     * 归属机箱
     */
    @Excel(name = "归属机箱", type = Excel.Type.IMPORT)
    private String boxBelong;

    /**
     * OBD名称
     */
    @Excel(name = "OBD名称", type = Excel.Type.IMPORT)
    private String obdName;

    /**
     * 端口数量
     */
    @Excel(name = "端口数量", type = Excel.Type.IMPORT)
    private int portCount;
}
