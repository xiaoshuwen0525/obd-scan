package com.ruoyi.web.controller.tool.service.impl;

import com.ruoyi.web.controller.tool.mapper.ObdPortInfoMapper;
import com.ruoyi.web.controller.tool.service.IObdPortInfoService;
import com.ruoyi.web.controller.tool.domain.ObdPortInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * (ObdPortInfo)服务实现类
 *
 * @author CrystalWings
 * @since 2020-09-29 15:16:43
 */

@Service
public class ObdPortInfoServiceImpl implements IObdPortInfoService {

    @Resource(type = ObdPortInfoMapper.class)
    private ObdPortInfoMapper obdPortInfoMapper;

    /**通过ID查询单一ObdPortInfo对象  */
    @Override
    public ObdPortInfo selectById(Integer id) {
        return obdPortInfoMapper.selectById(id);
    }

    /** 通过封装对象查询单一ObdPortInfo对象 */
    @Override
    public ObdPortInfo selectOneByEntity(ObdPortInfo obdPortInfo) {
        return obdPortInfoMapper.selectOneByEntity(obdPortInfo);
    }

    /** 通过封装对象查询ObdPortInfo集合 */
    @Override
    public List<ObdPortInfo> selectListByEntity(ObdPortInfo obdPortInfo) {
        return obdPortInfoMapper.selectListByEntity(obdPortInfo);
    }

    /** 通过ID集合查询ObdPortInfo集合 */    
    @Override
    public List<ObdPortInfo> selectListByIds(List<Integer> ids) {
        return obdPortInfoMapper.selectListByIds(ids);
    }

    /** 通过封装对象【模糊】查询ObdPortInfo集合 */    
    @Override
    public List<ObdPortInfo> selectListLikeEntity(ObdPortInfo obdPortInfo) {
        return obdPortInfoMapper.selectListLikeEntity(obdPortInfo);
    }

    /** 新增ObdPortInfo对象 */
    @Override
    public int insert(ObdPortInfo obdPortInfo) {
        return obdPortInfoMapper.insert(obdPortInfo);
    }

    /** 批量新增ObdPortInfo对象 */
    @Override
    public int insertBatch(List<ObdPortInfo> list) {
        return obdPortInfoMapper.insertBatch(list);
    }

    /** 更新ObdPortInfo对象 */
    @Override
    public int update(ObdPortInfo obdPortInfo) {
        return obdPortInfoMapper.update(obdPortInfo);
    }

    /** 批量更新ObdPortInfo对象 */
    @Override
    public int updateBatch(List<ObdPortInfo> list) {
        return obdPortInfoMapper.updateBatch(list);
    }

    /** 通过ID删除ObdPortInfo对象 */
    @Override
    public int deleteById(Integer id) {
        return obdPortInfoMapper.deleteById(id);
    }

    /** 通过封装对象删除ObdPortInfo对象 */ 
    @Override
    public int deleteByEntity(ObdPortInfo obdPortInfo) {
        return obdPortInfoMapper.deleteByEntity(obdPortInfo);
    } 

    /** 通过ID集合删除ObdPortInfo对象 */    
    @Override
    public int deleteByIds(List<Integer> list) {
        return obdPortInfoMapper.deleteByIds(list);
    }

    /** 统计所有ObdPortInfo对象的数量 */      
    @Override
    public int countAll() {
        return obdPortInfoMapper.countAll();
    }

    /** 统计封装ObdPortInfo对象的数量 */       
    @Override    
    public int countByEntity(ObdPortInfo obdPortInfo) {
        return obdPortInfoMapper.countByEntity(obdPortInfo);
    }

}