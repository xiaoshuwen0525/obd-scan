package com.ruoyi.web.controller.tool.service.impl;

import com.ruoyi.web.controller.tool.mapper.ObdBoxMapper;
import com.ruoyi.web.controller.tool.service.IObdBoxService;
import com.ruoyi.web.controller.tool.domain.ObdBox;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * (ObdBox)服务实现类
 *
 * @author CrystalWings
 * @since 2020-09-29 15:16:43
 */

@Service
public class ObdBoxServiceImpl implements IObdBoxService {

    @Resource(type = ObdBoxMapper.class)
    private ObdBoxMapper obdBoxMapper;

    /**通过ID查询单一ObdBox对象  */
    @Override
    public ObdBox selectById(Integer id) {
        return obdBoxMapper.selectById(id);
    }

    /** 通过封装对象查询单一ObdBox对象 */
    @Override
    public ObdBox selectOneByEntity(ObdBox obdBox) {
        return obdBoxMapper.selectOneByEntity(obdBox);
    }

    /** 通过封装对象查询ObdBox集合 */
    @Override
    public List<ObdBox> selectListByEntity(ObdBox obdBox) {
        return obdBoxMapper.selectListByEntity(obdBox);
    }

    /** 通过ID集合查询ObdBox集合 */    
    @Override
    public List<ObdBox> selectListByIds(List<Integer> ids) {
        return obdBoxMapper.selectListByIds(ids);
    }

    /** 通过封装对象【模糊】查询ObdBox集合 */    
    @Override
    public List<ObdBox> selectListLikeEntity(ObdBox obdBox) {
        return obdBoxMapper.selectListLikeEntity(obdBox);
    }

    /** 新增ObdBox对象 */
    @Override
    public int insert(ObdBox obdBox) {
        Date date = new Date();
        obdBox.setCreateTime(date);
        obdBox.setCreateTime(date);
        return obdBoxMapper.insert(obdBox);
    }

    /** 批量新增ObdBox对象 */
    @Override
    public int insertBatch(List<ObdBox> list) {
        return obdBoxMapper.insertBatch(list);
    }

    /** 更新ObdBox对象 */
    @Override
    public int update(ObdBox obdBox) {
        obdBox.setCreateTime(new Date());
        return obdBoxMapper.update(obdBox);
    }

    /** 批量更新ObdBox对象 */
    @Override
    public int updateBatch(List<ObdBox> list) {
        return obdBoxMapper.updateBatch(list);
    }

    /** 通过ID删除ObdBox对象 */
    @Override
    public int deleteById(Integer id) {
        return obdBoxMapper.deleteById(id);
    }

    /** 通过封装对象删除ObdBox对象 */ 
    @Override
    public int deleteByEntity(ObdBox obdBox) {
        return obdBoxMapper.deleteByEntity(obdBox);
    } 

    /** 通过ID集合删除ObdBox对象 */    
    @Override
    public int deleteByIds(List<Integer> list) {
        return obdBoxMapper.deleteByIds(list);
    }

    /** 统计所有ObdBox对象的数量 */      
    @Override
    public int countAll() {
        return obdBoxMapper.countAll();
    }

    /** 统计封装ObdBox对象的数量 */       
    @Override    
    public int countByEntity(ObdBox obdBox) {
        return obdBoxMapper.countByEntity(obdBox);
    }

}