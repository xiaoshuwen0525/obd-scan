package com.ruoyi.web.controller.tool.service;
import com.ruoyi.web.controller.tool.mapper.WxUserMapper;
import com.ruoyi.web.controller.tool.domain.WxUser;
import java.util.List;

/**
 * (WxUser)服务层
 *
 * @author CrystalWings
 * @since 2020-09-29 15:16:43
 */

public interface IWxUserService {
    
    /**
    * 通过ID查询单一WxUser对象
    * @param id  wxUserID
    * @return WxUser 实体类
    */
    WxUser selectById(Integer id);

    /**
    * 通过封装对象查询单一WxUser对象
    * @param wxUser 实体类
    * @return WxUser 实体类
    */
    WxUser selectOneByEntity(WxUser wxUser);

    /**
    * 通过封装对象查询WxUser集合
    * @param wxUser 实体类
    * @return List<WxUser>  实体类集合
    */
    List<WxUser> selectListByEntity(WxUser wxUser);

    /**
    * 通过ID集合查询WxUser集合
    * @param ids wxUser 实体类ID集合
    * @return List<WxUser>  实体类集合
    */
    List<WxUser> selectListByIds(List<Integer> ids);

    /**
    * 通过封装对象【模糊】查询WxUser集合
    * @param wxUser 实体类(百分号需自行组装)
    * @return List<WxUser>  实体类集合
    */
    List<WxUser> selectListLikeEntity(WxUser wxUser);

    /**
    * 新增WxUser对象
    * @param wxUser 实体类
    * @return int  操作条数
    */
    int insert(WxUser wxUser);

    /**
    * 批量新增WxUser对象
    * @param list wxUser 实体类集合
    * @return int  操作条数
    */
    int insertBatch(List<WxUser> list);

    /**
    * 更新WxUser对象
    * @param wxUser 实体类
    * @return int  操作条数
    */
    int update(WxUser wxUser);

    /**
    * 批量更新WxUser对象
    * @param list wxUser 实体类集合
    * @return int  操作条数
    */
    int updateBatch(List<WxUser> list);

    /**
    * 通过ID删除WxUser对象
    * @param id wxUserID
    * @return int  操作条数
    */
    int deleteById(Integer id);

    /**
    * 通过封装对象删除WxUser对象
    * @param wxUser 实体类
    * @return int  操作条数
    */
    int deleteByEntity(WxUser wxUser);

    /**
    *  过ID集合删除WxUser对象 
    * @param list wxUserID集合
    * @return int  操作条数
    */
    int deleteByIds(List<Integer> list);

    /**
    * 统计所有wxUser对象的数量
    * @return int 所有数量
    */
    int countAll();

    /**
    * 统计指定WxUser对象的数量
    * @param wxUser 实体类
    * @return int 查询数量
    */   
    int countByEntity(WxUser wxUser);
}