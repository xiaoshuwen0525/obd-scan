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
public class ObdsVO {

    private static final long serialVersionUID = 1L;

        /** 盒子串码 */
        private String boxCode;

        /** 工号 */
        private String jobNumber;

        /** 端口 */
        private List<InfoVO> portList;





}