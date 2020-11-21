package com.ruoyi.web.controller.data.service;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.controller.data.domain.*;

import java.util.List;
import java.util.Map;

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
     * 更新基础数据
     *
     * @param baseDataVo 基础更新
     * @return int
     */
    int updateBaseData(BaseDataVo baseDataVo);

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
     * @param ids ids
     * @return int
     */
    int deletePcObdBoxByIds(String ids);

    /**
     * 选择obd的实体
     *
     * @param derivedEntity 派生实体
     * @return {@link List<DerivedEntity>}
     */
    List<DerivedEntity> selectObdByEntity(DerivedEntity derivedEntity);

    /**
     * 查询所有机箱名称
     *
     * @return {@link List<PcObdBox>}
     */
    List<PcObdBox> selectAllBoxName(String boxName);

    /**
     * 根据boxid查询obd
     *
     * @param boxId 框标识
     * @return {@link List<PcObdInfo>}
     */
    List<PcObdInfo> selectByBoxId(Integer boxId);

    /**
     * 根据对象查询obd_box
     *
     * @param pcObdBox pc obd盒子
     * @return {@link List<PcObdBox>}
     */
    List<PcObdBox> selectBoxListByEntity(PcObdBox pcObdBox);
}
