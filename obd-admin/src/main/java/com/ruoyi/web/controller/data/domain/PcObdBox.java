package com.ruoyi.web.controller.data.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (PcObdBox)实体类
 *
 * @author makejava
 * @since 2020-10-23 11:36:14
 */
@Data
public class PcObdBox implements Serializable {
    private static final long serialVersionUID = 836954598803765565L;

    private Integer id;
    /**
     * 盒子串码
     */
    private String boxCode;
    /**
     * 倒灌标签二维码
     */
    private String labelCode;
    /**
     * 机箱名称
     */
    private String boxName;
    /**
     * 区域
     */
    private String area;
    /**
     * 业务局
     */
    private String businessBureau;
    /**
     * 营服中心
     */
    private String campService;
    /**
     * 工号
     */
    private String jobNumber;
    /**
     * 图片地址
     */
    private String imgUrl;
    /**
     * 数据状态：0:正常 1:异常
     */
    private Integer status;
    /**
     * 异常类型：1：盒子异常；2：OBD异常
     */
    private Integer exceptionType;
    /**
     * 异常信息
     */
    private String exceptionInfo;
    /**
     * 创建时间
     */
    private Date createTime;


}