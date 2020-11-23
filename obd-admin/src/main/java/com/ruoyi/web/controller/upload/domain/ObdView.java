package com.ruoyi.web.controller.upload.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * VIEW(ViewObd)实体类
 *
 * @author CrystalWings
 * @since 2020-09-30 10:46:39
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ObdView extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * ${column.comment}
     */
    private Integer id;

    @Excel(name = "机箱唯一ID")
    private String boxUniqueId;

    /**
     * 盒子串码
     */
    @Excel(name = "盒子串码")
    private String boxCode;

    /**
     * 倒灌标签二维码
     */
    @Excel(name = "倒灌标签二维码")
    private String labelCode;

    /**
     * 数据状态：0:正常 1:异常
     */
    @Excel(name = "机箱状态")
    private String Status;

    /**
     * 异常类型：1：盒子异常；2：OBD异常
     */
    @Excel(name = "异常类型")
    private String exceptionType;

    /**
     * 异常信息
     */
    @Excel(name = "异常信息")
    private String exceptionInfo;

    /**
     * 异常信息
     */
    private String imgUrl;

    /**
     * 工号
     */
    @Excel(name = "工号")
    private String jobNumber;

    private String phone;

    private String code;

    /**
     * ${column.comment}
     */
    @Excel(name = "时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * ${column.comment}
     */
    private Integer infoId;

    /**
     * 数据状态0：正常1：异常
     */
    @Excel(name = "OBD状态")
    private String obdStatus;

    /**
     * ${column.comment}
     */
    private Integer portId;

    /**
     * 序号：取值范围（1-8）
     */
    @Excel(name = "端口序号")
    private Integer portSer;

    /**
     * obd端口串码
     */
    @Excel(name = "OBD端口串码")
    private String portCode;

    /**
     * 数据状态0：正常1：异常
     */
    @Excel(name = "端口状态")
    private String portStatus;

    @Excel(name = "归属设备")
    private String boxBelong;

    @Excel(name = "OBD名称")
    private String obdName;

    @Excel(name = "OBD唯一ID")
    private String obdUniqueId;

}