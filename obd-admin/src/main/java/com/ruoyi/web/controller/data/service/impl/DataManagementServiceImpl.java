package com.ruoyi.web.controller.data.service.impl;



import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.controller.data.domain.DerivedEntity;
import com.ruoyi.web.controller.data.domain.PcObdBox;
import com.ruoyi.web.controller.data.domain.PcObdInfo;
import com.ruoyi.web.controller.data.mapper.DataManagementMapper;
import com.ruoyi.web.controller.data.service.IDataManagementService;
import com.ruoyi.web.controller.obd.domain.ImportEntity;
import org.apache.poi.hssf.record.OldFormulaRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.metal.OceanTheme;
import javax.validation.constraints.NotNull;
import java.beans.Transient;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 数据管理服务impl
 *
 * @author Administrator
 * @author: 小洪
 * @date:
 * @date 2020/10/23
 */
@Service
public class DataManagementServiceImpl implements IDataManagementService {

    /**
     * 数据管理映射器
     */
    @Autowired
    private DataManagementMapper dataManagementMapper;


    /**
     * 插入obd
     *
     * @param userList 用户列表
     * @return {@link AjaxResult}
     */
    @Override
    @Transactional
    public AjaxResult insertPcObd(List<ImportEntity> userList) {
        List<ImportEntity> toRepeatList = userList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getBoxCode() + ";" + o.getLabelCode()))), ArrayList::new));
        Map<Integer, String> boxIdMap = new HashMap<>();
        for (ImportEntity toRepeat : toRepeatList) {
            PcObdBox pcObdBox = new PcObdBox();
            if(StringUtils.isNotEmpty(toRepeat.getBoxCode()) || StringUtils.isNotEmpty(toRepeat.getLabelCode())){
                pcObdBox.setArea(toRepeat.getArea());
                pcObdBox.setBusinessBureau(toRepeat.getBusinessBureau());
                pcObdBox.setCampService(toRepeat.getCampService());
                pcObdBox.setBoxCode(toRepeat.getBoxCode());
                pcObdBox.setLabelCode(toRepeat.getLabelCode());
                pcObdBox.setBoxName(toRepeat.getObdName());
                dataManagementMapper.insertPcObdBox(pcObdBox);
                boxIdMap.put(pcObdBox.getId(), pcObdBox.getBoxCode() + "," + pcObdBox.getLabelCode());
            }else {
                return  AjaxResult.warn("存在code为空");
            }
        }
        List<PcObdInfo> list = new ArrayList<>();
        for (ImportEntity importEntity : userList) {
            PcObdInfo pcObdInfo = new PcObdInfo();
            pcObdInfo.setObdName(importEntity.getObdName());
            pcObdInfo.setBoxBelong(importEntity.getBoxBelong());
            pcObdInfo.setPortCount(importEntity.getPortCount());
            String code = "";
            if (StringUtils.isNotEmpty(importEntity.getBoxCode())) {
                code = importEntity.getBoxCode();
            } else {
                code = importEntity.getLabelCode();
            }
            for (Map.Entry<Integer, String> entry : boxIdMap.entrySet()) {
                if (entry.getValue().contains(code)) {
                    pcObdInfo.setBoxId(entry.getKey());
                }
            }
            if (pcObdInfo.getBoxId() != null && pcObdInfo.getBoxId() != 0) {
                list.add(pcObdInfo);
            }else {
                AjaxResult.warn("插入出错");
            }
        }
        dataManagementMapper.insertPcObdInfo(list);
        return AjaxResult.success();
    }

    /**
     * 更新obd盒子
     *
     * @param pcObdBox pc obd盒子
     * @return int
     */
    @Override
    public int updatePcObdBox(PcObdBox pcObdBox) {
        return dataManagementMapper.updatePcObdBox(pcObdBox);
    }

    /**
     * 更新obd信息
     *
     * @param pcObdInfo pc obd信息
     * @return int
     */
    @Override
    public int updatePcObdInfo(PcObdInfo pcObdInfo) {
        return dataManagementMapper.updatePcObdInfo(pcObdInfo);
    }

    /**
     * 删除obd信息通过id
     *
     * @param id id
     * @return int
     */
    @Override
    public int deletePcObdInfoById(Integer id) {
        return dataManagementMapper.deletePcObdInfoById(id);
    }

    /**
     * 删除obd框通过id
     *
     * @param id id
     * @return int
     */
    @Override
    public int deletePcObdBoxById(Integer id) {
        return dataManagementMapper.deletePcObdBoxById(id);
    }

    /**
     * 选择obd的实体
     *
     * @param derivedEntity 派生实体
     * @return {@link List<DerivedEntity>}
     */
    @Override
    public List<DerivedEntity> selectObdByEntity(DerivedEntity derivedEntity) {
        return dataManagementMapper.selectObdByEntity(derivedEntity);
    }

    @Override
    public List<PcObdInfo> selectByBoxId(@NotNull Integer boxId) {
        return dataManagementMapper.selectByBoxId(boxId);
    }


}