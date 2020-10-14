package com.ruoyi.web.controller.upload.mapper;


import com.ruoyi.web.controller.upload.domain.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *
 *
 * @author
 * @since
 */

@Repository
public interface UploadMapper {

    /**
     * 新增ObdBox对象
     * @param obdBox 实体类
     * @return int  id
     */
    int insertObdBox(@NotNull ObdBox obdBox);

    /**
     * 检验该obd箱子是否存在
     * @param boxCode
     * @return int 所有数量
     */
    int countByBoxCode(@Param("boxCode") String boxCode);

    /**
     * 新增obdInfo对象
     * @param obdInfo 实体类
     * @return int  id
     */
    int insertObdInfo(@NotNull ObdInfo obdInfo);

    /**
     * 新增obdPortInfo对象
     * @param obdPortInfo 实体类
     * @return int  id
     */
    int insertPort(@NotNull ObdPortInfo obdPortInfo);

    /**
     * 根据状态判断obd是否异常
     * @param obdId 状态
     * @return int  id
     */
    int countByPortStatus(@Param("obdId") int obdId);

    /**
     * 更新obdInfo
     * @param obdInfo
     * @return int  id
     */
    int  updateObdInfo(ObdInfo obdInfo);

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
     * 根据工号查询obd盒子
     * @param jobNumber 工号
     * @return AjaxResult
     */
    List<ObdBoxVO> selectBoxByJobNumber(@Param("jobNumber") String  jobNumber);

    /**
     * 根据盒子唯一ID和工号查询obd
     * @param  boxId  盒子唯一id
     * @return AjaxResult
     */
    List<ObdInfoVO> selectInfoByBoxId(@Param("boxId") String  boxId);


    /**
     * 根据obdId查询端口
     * @param obdId obdId
     * @return AjaxResult
     */
    List<ObdPortInfoVO> selectPortByObdId(@Param("obdId") String  obdId);


    /**
     * 根据id查询obd盒子
     * @param id 工号
     * @return AjaxResult
     */
    ObdBoxVO selectBoxById(@Param("id") String  id);

    /**
     * 根据ID查询obd
     * @param id
     * @return AjaxResult
     */
    ObdInfoVO selectInfoById(@Param("id") String  id);


    /**
     * 根据Id查询端口
     * @param id id
     * @return AjaxResult
     */
    ObdPortInfoVO selectPortById(@Param("id") String  id);



}