package com.ruoyi.web.controller.data.domain;

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
    @Excel(name = "区域")
    private String area;

    /**
     * 业务局
     */
    @Excel(name = "业务局")
    private String businessBureau;

    /**
     * 营服中心
     */
    @Excel(name = "营服中心")
    private String campService;

    /**
     * 盒子串码
     */
    @Excel(name = "设备标签二维码")
    private String boxCode;

    /**
     * 倒灌标签二维码
     */
    @Excel(name = "倒灌标签二维码")
    private String labelCode;

    /**
     * 归属机箱
     */
    @Excel(name = "归属设备")
    private String boxBelong;

    /**
     * 机箱唯一ID
     */
    @Excel(name = "归属设备ID")
    private String boxUniqueId;

    /**
     * OBD名称
     */
    @Excel(name = "OBD名称")
    private String obdName;

    /**
     * OBD唯一ID
     */
    @Excel(name = "OBD实物ID")
    private String obdUniqueId;

    /**
     * 端口数量
     */
    @Excel(name = "OBD端口数")
    private int portCount;




}
