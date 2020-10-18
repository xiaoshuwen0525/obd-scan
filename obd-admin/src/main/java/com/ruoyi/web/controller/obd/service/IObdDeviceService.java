package com.ruoyi.web.controller.obd.service;

import com.ruoyi.web.controller.system.domain.WxUser;
import com.ruoyi.web.controller.upload.domain.ObdBoxVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IObdDeviceService {

    /**
     * 根据boxCode机箱查询
     * */
    List<ObdBoxVO> boxSearch(String boxCode);

    /**
     * 根据jobNumber机箱查询
     * */
    List<ObdBoxVO> jobNumberSearch(String jobNumber);

    /**
     * 根据phone机箱查询
     * */
    List<ObdBoxVO> phoneSearch(String phone);

    /**
     * 查询所有机箱信息
     * */
    List<ObdBoxVO> allSearch();

    /**
     * 绑定手机号码
     * */
    String bindPhone(@Param("jobNumber") String jobNumber, @Param("phone")String phone);

    /**
     * 解绑手机号码
     * */
    String unBindPhone(@Param("jobNumber") String jobNumber);

    /**
     * 查询微信用户相关信息
     */
    List<WxUser> queryWechatInfo(String jobNumber, String phone, String id);

    /**
     * 查询所有机箱信息
     * */
    List<ObdBoxVO> searchByCondition(String jobNumber, String phone, String boxCode,String status);

}
