package com.ruoyi.web.controller.tool.service.impl;

import com.ruoyi.web.controller.tool.mapper.ViewObdMapper;
import com.ruoyi.web.controller.tool.service.IViewObdService;
import com.ruoyi.web.controller.tool.domain.ViewObd;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * VIEW(ViewObd)服务实现类
 *
 * @author CrystalWings
 * @since 2020-09-30 10:46:39
 */

@Service
public class ViewObdServiceImpl implements IViewObdService {

    @Resource(type = ViewObdMapper.class)
    private ViewObdMapper viewObdMapper;

    /**通过ID查询单一ViewObd对象  */
    @Override
    public ViewObd selectById(Integer id) {
        return viewObdMapper.selectById(id);
    }

    /** 通过封装对象查询单一ViewObd对象 */
    @Override
    public ViewObd selectOneByEntity(ViewObd viewObd) {
        return viewObdMapper.selectOneByEntity(viewObd);
    }

    /** 通过封装对象查询ViewObd集合 */
    @Override
    public List<ViewObd> selectListByEntity(ViewObd viewObd) {
        return viewObdMapper.selectListByEntity(viewObd);
    }

    /** 通过ID集合查询ViewObd集合 */    
    @Override
    public List<ViewObd> selectListByIds(List<Integer> ids) {
        return viewObdMapper.selectListByIds(ids);
    }

    /** 通过封装对象【模糊】查询ViewObd集合 */    
    @Override
    public List<ViewObd> selectListLikeEntity(ViewObd viewObd) {
        return viewObdMapper.selectListLikeEntity(viewObd);
    }

    /** 新增ViewObd对象 */
    @Override
    public int insert(ViewObd viewObd) {
        Date date = new Date();
        viewObd.setCreateTime(date);
        viewObd.setCreateTime(date);
        return viewObdMapper.insert(viewObd);
    }

    /** 批量新增ViewObd对象 */
    @Override
    public int insertBatch(List<ViewObd> list) {
        return viewObdMapper.insertBatch(list);
    }

    /** 更新ViewObd对象 */
    @Override
    public int update(ViewObd viewObd) {
        viewObd.setCreateTime(new Date());
        return viewObdMapper.update(viewObd);
    }

    /** 批量更新ViewObd对象 */
    @Override
    public int updateBatch(List<ViewObd> list) {
        return viewObdMapper.updateBatch(list);
    }

    /** 通过ID删除ViewObd对象 */
    @Override
    public int deleteById(Integer id ) {
        return viewObdMapper.deleteById(id);
    }

    /** 通过封装对象删除ViewObd对象 */ 
    @Override
    public int deleteByEntity(ViewObd viewObd) {
        return viewObdMapper.deleteByEntity(viewObd);
    } 

    /** 通过ID集合删除ViewObd对象 */    
    @Override
    public int deleteByIds(List<Integer> list) {
        return viewObdMapper.deleteByIds(list);
    }

    /** 统计所有ViewObd对象的数量 */      
    @Override
    public int countAll() {
        return viewObdMapper.countAll();
    }

    /** 统计封装ViewObd对象的数量 */       
    @Override    
    public int countByEntity(ViewObd viewObd) {
        return viewObdMapper.countByEntity(viewObd);
    }

}