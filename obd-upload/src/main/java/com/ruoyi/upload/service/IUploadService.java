package com.ruoyi.upload.service;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.upload.domain.*;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface IUploadService
{

    /**
     * 上传信息
     * @param obd 实体类
     * @return AjaxResult
     */
    public AjaxResult uploadInformation(Obd obd);

    /**
     * 上传obd盒子
     * @param obdBox 实体类
     * @return 盒子id
     */
    public int uploadObdBox(ObdBox obdBox);

    /**
     * 上传obd
     * @param obdInfo 实体类
     * @return obdid
     */
    public int uploadObdInfo(ObdInfo obdInfo);

    /**
     * 上传obd端口
     * @param obdPortInfo 实体类
     * @return 端口id
     */
    public int uploadObdPost(ObdPortInfo obdPortInfo);

    /**
     * 根据工号查询
     * @param jobNumber
     * @return AjaxResult
     */
    public List<ObdBoxVO> obdBoxByJobNumber(String jobNumber);

    /**
     * 根据盒子id和工号查询
     * @param boxId
     * @param jobNumber
     * @return
     */
    public List<ObdInfoVO> InfoByJobNumberAndBoxId(String boxId,String jobNumber);

    /**
     * 根据obdId查询端口
     * @param obdId
     * @return
     */
    public List<ObdPortInfoVO> portByObdId(String obdId);

    /**
     * 根据id查询obd盒子
     * @param id 工号
     * @return AjaxResult
     */
    List<ObdBoxVO> selectBoxById(String  id);

    /**
     * 根据ID查询obd
     * @param id
     * @return AjaxResult
     */
    List<ObdInfoVO> selectInfoById(String  id);


    /**
     * 根据Id查询端口
     * @param id id
     * @return AjaxResult
     */
    List<ObdPortInfoVO> selectPortById(String  id);

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
}
