package com.ruoyi.web.controller.data.service;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.controller.data.domain.DerivedEntity;
import com.ruoyi.web.controller.data.domain.PcObdBox;
import com.ruoyi.web.controller.data.domain.PcObdInfo;
import com.ruoyi.web.controller.obd.domain.ImportEntity;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * idata管理服务
 *
 * @author 小洪
 * @date 2020/10/23
 */
public interface IDataManagementService
{

    /**
     * 插入obd
     * 插入pcObd
     *
     * @param userList 用户列表
     * @return int
     */
    AjaxResult insertPcObd(List<ImportEntity> userList);


    /**
     * 更新obd盒子
     *
     * @param pcObdBox pc obd盒子
     * @return int
     */
    int updatePcObdBox(PcObdBox pcObdBox);

    /**
     * 更新obd信息
     *
     * @param pcObdInfo pc obd信息
     * @return int
     */
    int updatePcObdInfo(PcObdInfo pcObdInfo);

    /**
     * 删除obd通过id
     *
     * @param id id
     * @return int
     */
    int deletePcObdInfoById(Integer id);

    /**
     * 删除obd箱子通过id
     *
     * @param id id
     * @return int
     */
    int deletePcObdBoxById(Integer id);

    /**
     * 选择obd的实体
     *
     * @param derivedEntity 派生实体
     * @return {@link List<DerivedEntity>}
     */
    List<DerivedEntity> selectObdByEntity(DerivedEntity derivedEntity);


    /**
     * 根据boxid查询obd
     *
     * @param boxId 框标识
     * @return {@link List<PcObdInfo>}
     */
    List<PcObdInfo> selectByBoxId(Integer boxId);
}