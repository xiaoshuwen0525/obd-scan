package com.ruoyi.upload.domain;

import lombok.Data;

import java.util.List;

/**
 * (ObdInfo)实体类
 *
 * @author CrystalWings
 * @since 2020-09-29 15:16:43
 */

@Data
public class InfoVO {

    private static final long serialVersionUID = 1L;

    private List<ObdPortInfo> portData;



}