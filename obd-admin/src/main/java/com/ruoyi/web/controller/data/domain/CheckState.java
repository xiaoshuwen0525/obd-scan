package com.ruoyi.web.controller.data.domain;
/**
 * @author CrystalWings_
 * @date 2020-12-27  14:05
 */

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * @author: 曾志伟
 * @date: 2020-12-27 14:05
 */
@Data
public class CheckState {

    /**
     * OBD唯一ID
     */
    @Excel(name = "OBD唯一ID")
    private String obdUniqueId;

    /**
     * 审核状态
     */
    @Excel(name = "审核状态")
    private String checkState;

    /**
     * 备注信息
     */
    @Excel(name = "备注信息")
    private String remarks;

}
