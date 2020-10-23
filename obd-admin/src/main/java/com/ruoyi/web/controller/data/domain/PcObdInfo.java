package com.ruoyi.web.controller.data.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * (PcObdInfo)实体类
 *
 * @author makejava
 * @since 2020-10-23 11:37:29
 */
@Data
public class PcObdInfo implements Serializable {
    private static final long serialVersionUID = -10314166765699806L;

    private Integer id;
    /**
     * 引用盒子主键
     */
    private Integer boxId;
    /**
     * 数据状态0：正常1：异常
     */
    private Integer status;
    /**
     * 归属设备
     */
    private String boxBelong;
    /**
     * OBD名称
     */
    private String obdName;
    /**
     * 端口数
     */
    private int portCount;

}