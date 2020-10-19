package com.ruoyi.web.controller.obd.mapper;

import com.ruoyi.web.controller.system.domain.WxUser;
import com.ruoyi.web.controller.upload.domain.ObdBoxVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObdDeviceMapper {

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
     * 查询指定手机号是否存在
     * */
    WxUser isPhoneNumberExist(String newPhone);

    /**
     * 绑定手机号码
     * */
    int bindPhone(@Param("jobNumber") String jobNumber, @Param("phone")String phone, @Param("newPhone")String newPhone);

    /**
     * 解绑手机号码
     * */
    int unBindPhone(@Param("id") String id);

    /**
     * 查询所有机箱信息
     */
    List<ObdBoxVO> searchByCondition(@Param("jobNumber") String jobNumber, @Param("phone")String phone, @Param("boxCode")String boxCode, @Param("status")String status);

    /**
     * 查询微信用户相关信息
     */
    List<WxUser> queryWechatInfo(WxUser wxUser);

}
