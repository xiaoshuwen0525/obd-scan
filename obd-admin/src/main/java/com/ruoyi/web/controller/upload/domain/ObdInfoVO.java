package com.ruoyi.web.controller.upload.domain;

import lombok.Data;

import java.util.List;

/**
 * (ObdInfo)实体类
 *
 * @author CrystalWings
 * @since 2020-09-29 15:16:43
 */

@Data
public class ObdInfoVO {

    private static final long serialVersionUID = 1L;

    private Integer seq;

    /**
     * ${column.comment}
     */
    private Integer id;

    /**
     * 引用盒子主键
     */
    private Integer boxId;

    /**
     * 数据状态0：正常1：异常
     */
    private String status;

    private List<ObdPortInfoVO> obdPortInfoVOList;

}