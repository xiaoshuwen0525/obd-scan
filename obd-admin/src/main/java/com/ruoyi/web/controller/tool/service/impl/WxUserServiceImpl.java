package com.ruoyi.web.controller.tool.service.impl;

import com.ruoyi.web.controller.tool.mapper.WxUserMapper;
import com.ruoyi.web.controller.tool.service.IWxUserService;
import com.ruoyi.web.controller.tool.domain.WxUser;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * (WxUser)服务实现类
 *
 * @author CrystalWings
 * @since 2020-09-29 15:16:43
 */

@Service
public class WxUserServiceImpl implements IWxUserService {

    @Resource(type = WxUserMapper.class)
    private WxUserMapper wxUserMapper;

    /**通过ID查询单一WxUser对象  */
    @Override
    public WxUser selectById(Integer id) {
        return wxUserMapper.selectById(id);
    }

    /** 通过封装对象查询单一WxUser对象 */
    @Override
    public WxUser selectOneByEntity(WxUser wxUser) {
        return wxUserMapper.selectOneByEntity(wxUser);
    }

    /** 通过封装对象查询WxUser集合 */
    @Override
    public List<WxUser> selectListByEntity(WxUser wxUser) {
        return wxUserMapper.selectListByEntity(wxUser);
    }

    /** 通过ID集合查询WxUser集合 */    
    @Override
    public List<WxUser> selectListByIds(List<Integer> ids) {
        return wxUserMapper.selectListByIds(ids);
    }

    /** 通过封装对象【模糊】查询WxUser集合 */    
    @Override
    public List<WxUser> selectListLikeEntity(WxUser wxUser) {
        return wxUserMapper.selectListLikeEntity(wxUser);
    }

    /** 新增WxUser对象 */
    @Override
    public int insert(WxUser wxUser) {
        return wxUserMapper.insert(wxUser);
    }

    /** 批量新增WxUser对象 */
    @Override
    public int insertBatch(List<WxUser> list) {
        return wxUserMapper.insertBatch(list);
    }

    /** 更新WxUser对象 */
    @Override
    public int update(WxUser wxUser) {
        return wxUserMapper.update(wxUser);
    }

    /** 批量更新WxUser对象 */
    @Override
    public int updateBatch(List<WxUser> list) {
        return wxUserMapper.updateBatch(list);
    }

    /** 通过ID删除WxUser对象 */
    @Override
    public int deleteById(Integer id) {
        return wxUserMapper.deleteById(id);
    }

    /** 通过封装对象删除WxUser对象 */ 
    @Override
    public int deleteByEntity(WxUser wxUser) {
        return wxUserMapper.deleteByEntity(wxUser);
    } 

    /** 通过ID集合删除WxUser对象 */    
    @Override
    public int deleteByIds(List<Integer> list) {
        return wxUserMapper.deleteByIds(list);
    }

    /** 统计所有WxUser对象的数量 */      
    @Override
    public int countAll() {
        return wxUserMapper.countAll();
    }

    /** 统计封装WxUser对象的数量 */       
    @Override    
    public int countByEntity(WxUser wxUser) {
        return wxUserMapper.countByEntity(wxUser);
    }

}