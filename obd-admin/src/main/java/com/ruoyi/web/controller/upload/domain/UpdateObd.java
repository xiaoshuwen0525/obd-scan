package com.ruoyi.web.controller.upload.domain;



import lombok.Data;

import java.util.List;


/**
 * (Obd)实体类
 *
 * @author
 * @since
 */
 
@Data
public class UpdateObd {

    private static final long serialVersionUID = 1L;

        /** 盒子串码 */
        private String boxCode;

        /** id */
        private Integer id;

        /** 端口 */
        private List<ObdPortInfo> portList;





}