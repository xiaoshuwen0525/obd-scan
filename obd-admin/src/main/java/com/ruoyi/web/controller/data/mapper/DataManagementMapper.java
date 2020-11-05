package com.ruoyi.web.controller.data.mapper;


import com.ruoyi.web.controller.data.domain.BaseUpdate;
import com.ruoyi.web.controller.data.domain.DerivedEntity;
import com.ruoyi.web.controller.data.domain.PcObdBox;
import com.ruoyi.web.controller.data.domain.PcObdInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 数据管理映射器
 *
 * @author Administrator
 * @author: 小洪
 * @date 2020/10/23
 * @since 2020-10-23 11:36:15
 */
@Repository
public interface DataManagementMapper {


    /**
     * 插入obd盒子
     *
     * @param pcObdBox pc obd盒子
     * @return int
     */
    int insertPcObdBox(PcObdBox pcObdBox);


    /**
     * 插入obd信息
     *
     * @param pcObdInfo pc obd信息
     * @return int
     */
    int insertPcObdInfo(List<PcObdInfo> pcObdInfo);

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
     * 更新基础数据
     *
     * @param baseUpdate 基础更新
     * @return int
     */
    int updateBaseData(BaseUpdate baseUpdate);

    /**
     * 删除obd信息通过id
     *
     * @param id id
     * @return int
     */
    int deletePcObdInfoById(@NotNull Integer id);

    /**
     * 删除obd框通过id
     *
     * @param boxIds boxIds
     * @return int
     */
    int deletePcObdBoxByIds(Long[] boxIds);

    /**
     * 删除导入obd信息通过机箱id
     *
     * @param boxIds 机箱id
     * @return int
     */
    int deletePcObdInfoByBoxIds(Long[] boxIds);


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
    List<PcObdInfo> selectByBoxId(@NotNull Integer boxId);

    /**
     * 根据对象查询obd_box
     *
     * @param pcObdBox pc obd盒子
     * @return {@link List<PcObdBox>}
     */
    List<PcObdBox> selectBoxListByEntity(PcObdBox pcObdBox);
}