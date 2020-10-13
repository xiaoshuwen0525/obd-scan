package com.ruoyi.upload.domain;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * (ObdPost)实体类
 *
 * @author
 * @since
 */
 
@Data
public class ObdPost {

    /** obd端口串码 */
    private String portCode;

    /** 标识符 */
    private String  marking;

    /** 序号*/
    private String  portSer;

    /** 端口照片*/
    private MultipartFile portImgUrl;

    
}