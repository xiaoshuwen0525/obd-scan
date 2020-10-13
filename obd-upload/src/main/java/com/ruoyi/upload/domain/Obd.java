package com.ruoyi.upload.domain;



import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * (Obd)实体类
 *
 * @author
 * @since
 */
 
@Data
public class Obd {

    private static final long serialVersionUID = 1L;

        /** 盒子串码 */
        private String boxCode;

        /** 异常类型：1：盒子异常；2：OBD异常	 */
        private Integer exceptionType;
    
        /** 异常信息	 */
        private String exceptionInfo;
    
        /** 工号 */
        private String jobNumber;

        /** 盒子图片 */
        private MultipartFile imgUrl;

        /** obd */
        private List<String> obdList;

        /** 端口 */
        private List<ObdPost> postList;





}