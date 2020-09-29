package com.ruoyi.web.controller.tool.service.impl;

import com.ruoyi.web.controller.tool.mapper.ObdInfoMapper;
import com.ruoyi.web.controller.tool.service.IObdInfoService;
import com.ruoyi.web.controller.tool.domain.ObdInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * (ObdInfo)服务实现类
 *
 * @author CrystalWings
 * @since 2020-09-29 15:16:43
 */

@Service
public class ObdInfoServiceImpl implements IObdInfoService {

    @Resource(type = ObdInfoMapper.class)
    private ObdInfoMapper obdInfoMapper;

    /**通过ID查询单一ObdInfo对象  */
    @Override
    public ObdInfo selectById(Integer id) {
        return obdInfoMapper.selectById(id);
    }

    /** 通过封装对象查询单一ObdInfo对象 */
    @Override
    public ObdInfo selectOneByEntity(ObdInfo obdInfo) {
        return obdInfoMapper.selectOneByEntity(obdInfo);
    }

    /** 通过封装对象查询ObdInfo集合 */
    @Override
    public List<ObdInfo> selectListByEntity(ObdInfo obdInfo) {
        return obdInfoMapper.selectListByEntity(obdInfo);
    }

    /** 通过ID集合查询ObdInfo集合 */    
    @Override
    public List<ObdInfo> selectListByIds(List<Integer> ids) {
        return obdInfoMapper.selectListByIds(ids);
    }

    /** 通过封装对象【模糊】查询ObdInfo集合 */    
    @Override
    public List<ObdInfo> selectListLikeEntity(ObdInfo obdInfo) {
        return obdInfoMapper.selectListLikeEntity(obdInfo);
    }

    /** 新增ObdInfo对象 */
    @Override
    public int insert(ObdInfo obdInfo) {
        Date date = new Date();
        obdInfo.setCreateTime(date);
        obdInfo.setCreateTime(date);
        return obdInfoMapper.insert(obdInfo);
    }

    /** 批量新增ObdInfo对象 */
    @Override
    public int insertBatch(List<ObdInfo> list) {
        return obdInfoMapper.insertBatch(list);
    }

    /** 更新ObdInfo对象 */
    @Override
    public int update(ObdInfo obdInfo) {
        obdInfo.setCreateTime(new Date());
        return obdInfoMapper.update(obdInfo);
    }

    /** 批量更新ObdInfo对象 */
    @Override
    public int updateBatch(List<ObdInfo> list) {
        return obdInfoMapper.updateBatch(list);
    }

    /** 通过ID删除ObdInfo对象 */
    @Override
    public int deleteById(Integer id) {
        return obdInfoMapper.deleteById(id);
    }

    /** 通过封装对象删除ObdInfo对象 */ 
    @Override
    public int deleteByEntity(ObdInfo obdInfo) {
        return obdInfoMapper.deleteByEntity(obdInfo);
    } 

    /** 通过ID集合删除ObdInfo对象 */    
    @Override
    public int deleteByIds(List<Integer> list) {
        return obdInfoMapper.deleteByIds(list);
    }

    /** 统计所有ObdInfo对象的数量 */      
    @Override
    public int countAll() {
        return obdInfoMapper.countAll();
    }

    /** 统计封装ObdInfo对象的数量 */       
    @Override    
    public int countByEntity(ObdInfo obdInfo) {
        return obdInfoMapper.countByEntity(obdInfo);
    }

}