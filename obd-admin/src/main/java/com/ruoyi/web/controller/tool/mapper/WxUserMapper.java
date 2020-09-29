package com.ruoyi.web.controller.tool.mapper;

import com.ruoyi.web.controller.tool.domain.WxUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import javax.validation.constraints.*;
import java.util.List;

/**
 * (WxUser)持久层
 *
 * @author CrystalWings
 * @since 2020-09-29 15:16:43
 */

@Repository
public interface WxUserMapper {

    /**
    * 通过ID查询单一WxUser对象
    * @param id  wxUserID
    * @return WxUser 实体类
    */
    WxUser selectById(@NotNull Integer id);
    
    /**
    * 通过封装对象查询单一WxUser对象
    * @param wxUser 实体类
    * @return WxUser 实体类
    */
    WxUser selectOneByEntity(@NotNull WxUser wxUser);

    /**
    * 通过封装对象查询WxUser集合
    * @param wxUser 实体类
    * @return List<WxUser>  实体类集合
    */
    List<WxUser> selectListByEntity(WxUser wxUser);
    
    /**
    * 通过ID集合查询WxUser集合
    * @param list wxUser 实体类ID集合
    * @return List<WxUser>  实体类集合
    */
    List<WxUser> selectListByIds(@NotEmpty List<Integer> list);
    
    /**
    * 通过封装对象【模糊】查询WxUser集合
    * @param wxUser 实体类(百分号需自行组装)
    * @return List<WxUser>  实体类集合
    */
    List<WxUser> selectListLikeEntity(@NotNull WxUser wxUser);

    /**
    * 新增WxUser对象
    * @param wxUser 实体类
    * @return int  操作条数
    */
    int insert(@NotNull WxUser wxUser);

    /**
    * 批量新增WxUser对象
    * @param list wxUser 实体类集合
    * @return int  操作条数
    */
    int insertBatch(@NotEmpty List<WxUser> list);

    /**
    * 更新WxUser对象
    * @param wxUser 实体类
    * @return int  操作条数
    */
    int update(@NotNull WxUser wxUser);

    /**
    * 批量更新WxUser对象
    * @param list wxUser 实体类集合
    * @return int  操作条数
    */
    int updateBatch(@NotEmpty List<WxUser> list);

    /**
    * 通过ID删除WxUser对象
    * @param id wxUserID
    * @return int  操作条数
    */
    int deleteById(@NotNull Integer id);

    /**
    * 通过封装对象删除WxUser对象
    * @param wxUser 实体类
    * @return int  操作条数
    */
    int deleteByEntity(@NotNull WxUser wxUser);
  

    /**
    *  过ID集合删除WxUser对象 
    * @param list wxUserID集合
    * @return int  操作条数
    */
    int deleteByIds(@NotEmpty List<Integer> list);
    
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
    int countByEntity(@NotNull WxUser wxUser);
    
}