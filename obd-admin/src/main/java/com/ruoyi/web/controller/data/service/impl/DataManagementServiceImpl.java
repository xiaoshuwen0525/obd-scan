package com.ruoyi.web.controller.data.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.controller.data.domain.*;
import com.ruoyi.web.controller.data.mapper.DataManagementMapper;
import com.ruoyi.web.controller.data.service.IDataManagementService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * 数据管理服务impl
 *
 * @author Administrator
 * @author: 曾志伟，小洪
 * @date:
 * @date 2020/10/23
 */
@Service
public class DataManagementServiceImpl implements IDataManagementService {

    private static final Lock lock = new ReentrantLock();

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
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult insertPcObd(List<ImportEntity> userList) {
        try {
            List<ImportEntity> toRepeatList = userList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ImportEntity::getBoxUniqueId))), ArrayList::new));
            Map<Integer, String> boxIdMap = new HashMap<>();
            for (ImportEntity toRepeat : toRepeatList) {
                PcObdBox pcObdBox = new PcObdBox();
                if (StringUtils.isNotEmpty(toRepeat.getBoxCode()) || StringUtils.isNotEmpty(toRepeat.getLabelCode())) {
                    pcObdBox.setArea(toRepeat.getArea());
                    pcObdBox.setBoxUniqueId(trim(toRepeat.getBoxUniqueId()));
                    pcObdBox.setObdUniqueId(trim(toRepeat.getObdUniqueId()));
                    pcObdBox.setBusinessBureau(toRepeat.getBusinessBureau());
                    pcObdBox.setCampService(toRepeat.getCampService());
                    pcObdBox.setBoxCode(toRepeat.getBoxCode());
                    pcObdBox.setLabelCode(toRepeat.getLabelCode());
                    pcObdBox.setBoxName(toRepeat.getBoxBelong());
                    dataManagementMapper.insertPcObdBox(pcObdBox);
                    boxIdMap.put(pcObdBox.getId(), trim(toRepeat.getBoxUniqueId()));
                } else {
                    return AjaxResult.warn("存在code为空");
                }
            }
            List<PcObdInfo> list = new ArrayList<>();
            for (ImportEntity importEntity : userList) {
                PcObdInfo pcObdInfo = new PcObdInfo();
                pcObdInfo.setObdName(importEntity.getObdName());
                pcObdInfo.setBoxBelong(importEntity.getBoxBelong());
                pcObdInfo.setPortCount(importEntity.getPortCount());
                pcObdInfo.setBoxUniqueId(trim(importEntity.getBoxUniqueId()));
                pcObdInfo.setObdUniqueId(trim(importEntity.getObdUniqueId()));
                String boxUniqueId = "";
                if (StringUtils.isNotEmpty(trim(importEntity.getBoxUniqueId()))) {
                    boxUniqueId = trim(importEntity.getBoxUniqueId());
                } else {
                    return AjaxResult.warn("归属设备ID为空");
                }
                for (Map.Entry<Integer, String> entry : boxIdMap.entrySet()) {
                    if (entry.getValue().contains(boxUniqueId)) {
                        pcObdInfo.setBoxId(entry.getKey());
                    }
                }
                if (pcObdInfo.getBoxId() != null && pcObdInfo.getBoxId() != 0) {
                    list.add(pcObdInfo);
                } else {
                    return AjaxResult.warn("插入出错");
                }
            }
            dataManagementMapper.insertPcObdInfo(list);
            toRepeat();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success("插入成功");
    }

    /**
     * 插入obd
     *
     * @param userList 用户列表
     * @return {@link AjaxResult}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult insertPcObdPort(List<ImportPortEntity> userList) {
        try {
            dataManagementMapper.insertPcObdPort(userList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success("插入成功");
    }



    /**
     * 更新obd盒子
     *
     * @param pcObdBox pc obd盒子
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePcObdBox(PcObdBox pcObdBox) {
        int i = 0;
        lock.lock();
        try {
            i = dataManagementMapper.updatePcObdBox(pcObdBox);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBaseData(BaseDataVo baseDataVo) {
        int j = 0;
        List<BaseUpdate> baseUpdates = new ArrayList<>();
        BaseUpdate baseUpdate0 = new BaseUpdate();
        BeanUtils.copyProperties(baseDataVo, baseUpdate0);
        //更新机箱
        j = dataManagementMapper.updateBaseData(baseUpdate0);
        if (!CollectionUtils.isEmpty(baseDataVo.getBaseUpdateList())) {
            baseUpdates = baseDataVo.getBaseUpdateList();
            lock.lock();
            try {
                for (BaseUpdate baseUpdate : baseUpdates) {
                    int i = 0;
                    //如果obdBelong被修改了，则传过来的boxUniqueId值不为空
                    PcObdBox pcObdBox = new PcObdBox();
                    PcObdInfo pcObdInfo = new PcObdInfo();
                    if (StringUtils.isNotBlank(baseUpdate.getBoxUniqueId())) {
                        try {
                            pcObdBox = dataManagementMapper.selectByBoxUniqueId(baseUpdate.getBoxUniqueId());
                        } catch (Exception e) {
                            throw new BusinessException("对应机箱名称不唯一,无法判定更新对象");
                        }
                    } else {
                        try {
                            pcObdBox.setId(baseUpdate.getBoxId());
                            pcObdBox = dataManagementMapper.selectBoxListByEntity(pcObdBox).get(0);
                        } catch (Exception e) {
                            throw new BusinessException("对应机箱名称不唯一,无法判定更新对象");
                        }
                    }
                    //指定要更新的obd设备
                    pcObdInfo.setId(baseUpdate.getId());
                    //更新obd的设备归属名称
                    pcObdInfo.setBoxBelong(pcObdBox.getBoxName());
                    //更新obd的关联机箱主键ID
                    pcObdInfo.setBoxId(pcObdBox.getId());
                    //更新obd的设备归属机箱的唯一ID
                    pcObdInfo.setBoxUniqueId(pcObdBox.getBoxUniqueId());
                    //如果有更新obd名称则同步更新
                    pcObdInfo.setObdName(baseUpdate.getObdName());
                    //如果有更新端口数则同步更新
                    pcObdInfo.setPortCount(baseUpdate.getPortCount());
                    i = dataManagementMapper.updatePcObdInfoForBaseData(pcObdInfo);
                    j++;
                    if (i <= 0) {
                        j = 0;
                        int a = 1 / 0;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        return j;
    }

    /**
     * 删除obd信息通过id
     *
     * @param id id
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deletePcObdInfoById(Integer id) {
        int i = 0;
        try {
            i = dataManagementMapper.deletePcObdInfoById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return i;
    }

    /**
     * 删除obd框通过id
     *
     * @param ids ids
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deletePcObdBoxByIds(String ids) {
        Long[] boxIds = Convert.toLongArray(ids);
        if (boxIds.length == 0) {
            return 0;
        }
        int i = 0;
        int j = 0;
        lock.lock();
        try {
            i = dataManagementMapper.deletePcObdBoxByIds(boxIds);
            j = dataManagementMapper.deletePcObdInfoByBoxIds(boxIds);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return i + j;

    }

    /**
     * 选择obd的实体--专用于表格导出
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
        List<PcObdInfo> pcObdInfos = new ArrayList<>();
        try {
            pcObdInfos = dataManagementMapper.selectByBoxId(boxId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pcObdInfos;
    }

    /**
     * 查询所有机箱名称
     *
     * @return {@link List<PcObdBox>}
     */
    @Override
    public List<PcObdBox> selectAllBoxName(String boxName) {
        List<PcObdBox> pcObdBoxs = new ArrayList<>();
        try {
            pcObdBoxs = dataManagementMapper.selectAllBoxName(boxName);
        } catch (Exception e) {
            return pcObdBoxs;
        }
        return pcObdBoxs;
    }

    /**
     * 查询机箱的实体列表
     *
     * @param pcObdBox pc obd盒子
     * @return {@link List<PcObdBox>}
     */
    @Override
    public List<PcObdBox> selectBoxListByEntity(PcObdBox pcObdBox) {
        List<PcObdBox> pcObdBoxes = new ArrayList<PcObdBox>();
        try {
            pcObdBoxes = dataManagementMapper.selectBoxListByEntity(pcObdBox);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pcObdBoxes;
    }


    /**
     * 去掉非数字修剪
     *
     * @param str str
     * @return {@link String}
     */
    public static String trim(String str) {
        str = StringUtils.trim(str);
        String s = "";
        String startStr = String.valueOf(str.charAt(0));
        if (s.equals(startStr)) {
            str = str.substring(1, str.length());
        }
        return str;

    }

    /**
     * 去重
     *
     * @return {@link String}
     */
    public void toRepeat() {
        //obdCount 表示 条数 box_name 表示 id
        List<PcObdBox> pcObdBoxes = dataManagementMapper.selectCount();
        List<Integer> list = new ArrayList<>();
        for (PcObdBox pcObdBox : pcObdBoxes) {
            String[] idStrList = pcObdBox.getBoxName().split("-");
            for (String s : idStrList) {
                //seq表示where 的id  id 表示set 的id
                pcObdBox.setId(Integer.parseInt(idStrList[idStrList.length - 1]));
                pcObdBox.setSeq(Integer.parseInt(s));
                if (!s.equals(idStrList[idStrList.length - 1])) {
                    list.add(Integer.parseInt(s));
                    dataManagementMapper.updateCount(pcObdBox);
                }
            }
        }
        dataManagementMapper.deleteCount(list);
    }

}