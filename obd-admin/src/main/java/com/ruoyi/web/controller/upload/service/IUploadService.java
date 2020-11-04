package com.ruoyi.web.controller.upload.service;


import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.controller.upload.domain.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUploadService
{

//    /**
//     * 上传信息
//     * @param obd 实体类
//     * @return AjaxResult
//     */
//     AjaxResult uploadInformation(ObdVO obd);

    /**
     * 上传信息
     * @param obdBoxVO 实体类
     * @return AjaxResult
     */
    AjaxResult uploadInformation(ObdBoxVO obdBoxVO);

    /**
     * 上传obd盒子
     * @param obdBox 实体类
     * @return 盒子id
     */
     int uploadObdBox(ObdBox obdBox);

    /**
     * 上传obd盒子
     * @param obdPicture 实体类
     * @return 盒子id
     */
    int uploadObdPicture(ObdPicture obdPicture,MultipartFile file,String boxCode);

    /**
     * 上传obd
     * @param obdInfo 实体类
     * @return obdid
     */
     int uploadObdInfo(ObdInfo obdInfo);

    /**
     * 上传obd端口
     * @param obdPortInfo 实体类
     * @return 端口id
     */
     int uploadObdPost(ObdPortInfo obdPortInfo);

    /**
     * 根据工号查询
     * @param jobNumber
     * @return AjaxResult
     */
     List<ObdBoxVO> obdBoxByJobNumber(String jobNumber);

    /**
     * 根据盒子id查询
     * @param boxId
     * @return
     */
     List<ObdInfoVO> infoByBoxId(String boxId);

    /**
     * 根据obdId查询端口
     * @param obdId
     * @return
     */
     List<ObdPortInfoVO> portByObdId(String obdId);

    /**
     * 根据id查询obd盒子
     * @param id 工号
     * @return AjaxResult
     */
    ObdBoxVO selectBoxById(String  id);

    /**
     * 根据ID查询obd
     * @param id
     * @return AjaxResult
     */
    ObdInfoVO selectInfoById(String  id);


    /**
     * 根据Id查询端口
     * @param id id
     * @return AjaxResult
     */
    ObdPortInfoVO selectPortById(String  id);

    /**
     * 更新obdPortInfo
     * @param obdPortInfo
     * @return int  id
     */
    int updateObdPort(ObdPortInfo obdPortInfo);


    /**
     * 更新obdBox
     * @param obdBox
     * @return int  id
     */
    int updateObdBox(ObdBox obdBox);


    /**
     * 更新obdInfo
     * @param obdInfo
     * @return int  id
     */
    int  updateObdInfo(ObdInfo obdInfo);

    /**
     * 根据工号获取obd信息（树状）
     * @param jobNumber
     * @param pageNum
     * @param pageSize
     * @return int  id
     */
    PageInfo<ObdBoxVO> selectObdByJobNumber(String jobNumber, Integer pageNum, Integer pageSize);

    /**
     * 根据id获取obd信息（树状）
     * @param id
     * @return int  id
     */
    ObdBoxVO selectObdById(int id);


    /**
     * 更新obd
     * @param obdBoxVO
     * @return int  id
     */
    AjaxResult updateObd(ObdBoxVO obdBoxVO);

    /**
     * 选择电脑obd代码
     *
     * @param boxCode 框代码
     * @return {@link ObdBoxVO}
     */
    ObdBoxVO selectPcObdByCode(String  boxCode);


}
