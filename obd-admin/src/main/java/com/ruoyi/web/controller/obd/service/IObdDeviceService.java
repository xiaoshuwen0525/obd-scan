package com.ruoyi.web.controller.obd.service;

import com.ruoyi.web.controller.system.domain.WxUser;
import com.ruoyi.web.controller.upload.domain.ObdBoxVO;
import com.ruoyi.web.controller.upload.domain.ObdInfoVO;
import com.ruoyi.web.controller.upload.domain.ObdPortInfoVO;
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
     * 指定手机是否存在
     *
     * @return*/
    int isPhoneNumberExist(String newPhone);

    /**
     * 绑定手机号码
     *
     * @return*/
    int bindPhone(String jobNumber, String phone, String newPhone);

    /**
     * 解绑手机号码
     * */
    String unBindPhone(String jobNumber);

    /**
     * 查询微信用户相关信息
     */
    List<WxUser> queryWechatInfo(String jobNumber, String phone, String id);

    /**
     * 查询所有机箱信息
     * */
    List<ObdBoxVO> searchByCondition(String jobNumber, String phone, String boxCode,String status);

    /**
     * 根据obdid查询端口信息
     * */
    List<ObdPortInfoVO> portByObdId(String obdId);


    /**
     * 根据boxid查询obd信息
     * */
    List<ObdInfoVO> infoByBoxId(String boxId);


    /**
     * 根据工号查询机箱信息
     * */
    List<ObdBoxVO> selectBoxByJobNumber(String  jobNumber);

    /**
     * 根据boxCode或者labelCode查询机箱信息
     * */
    ObdBoxVO selectAllInfoByCode(String code);

}
