package com.ruoyi.upload.domain;



import lombok.Data;

import javax.sound.midi.MidiDevice;
import java.util.List;
import java.util.Map;


/**
 * (Obd)实体类
 *
 * @author
 * @since
 */
 
@Data
public class ObdVO {

    private static final long serialVersionUID = 1L;

        /** 盒子串码 */
        private String boxCode;

        /** 工号 */
        private String jobNumber;

        /** 端口 */
        private String portList;





}