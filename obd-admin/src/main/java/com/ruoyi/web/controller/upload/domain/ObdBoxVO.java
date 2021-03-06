package com.ruoyi.web.controller.upload.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


/**
 * (ObdBox)实体类
 *
 * @author CrystalWings
 * @since 2020-09-29 15:16:43
 */

@Data
public class ObdBoxVO {

    private static final long serialVersionUID = 1L;

    private Integer seq;

    private Integer id;

    /**
     * 盒子串码
     */
    private String boxCode;

    /**
     * ${column.comment}
     */
    private String imgUrl;

    /**
     * 数据状态：0:正常 1:异常
     */
    private String status;

    /**
     * 异常类型：1：盒子异常；2：OBD异常
     */
    private String exceptionType;

    /**
     * 异常信息
     */
    private String exceptionInfo;

    /**
     * 工号
     */
    private String jobNumber;
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
     * ${column.comment}
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private List<ObdInfoVO> obdInfoVOList;


    /**
     * 机箱唯一ID
     */
    private String boxUniqueId;

    /**
     * 审核状态
     */
    private String checkState;

    /**
     * 备注信息
     */
    private String remarks;



}