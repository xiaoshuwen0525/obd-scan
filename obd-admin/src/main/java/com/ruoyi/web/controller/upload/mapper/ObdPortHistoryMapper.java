package com.ruoyi.web.controller.upload.mapper;

import com.ruoyi.web.controller.upload.domain.ObdPortHistory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import javax.validation.constraints.*;
import java.util.List;

/**
 * (ObdPortHistory)持久层
 *
 * @author CrystalWings
 * @since 2020-12-27 14:21:28
 */

@Repository
public interface ObdPortHistoryMapper {

    /**
    * 通过ID查询单一ObdPortHistory对象
    * @param id  obdPortHistoryID
    * @return ObdPortHistory 实体类
    */
    ObdPortHistory selectById(@NotNull Integer id);


    /**
     * 查询原来端口数据
     *
     * @param ObdId obd id
     * @return {@link List<ObdPortHistory>}
     */
    List<ObdPortHistory> selectByObdId(@NotNull Integer ObdId);
    
    /**
    * 通过封装对象查询单一ObdPortHistory对象
    * @param obdPortHistory 实体类
    * @return ObdPortHistory 实体类
    */
    ObdPortHistory selectOneByEntity(@NotNull ObdPortHistory obdPortHistory);

    /**
    * 通过封装对象查询ObdPortHistory集合
    * @param obdPortHistory 实体类
    * @return List<ObdPortHistory>  实体类集合
    */
    List<ObdPortHistory> selectListByEntity(ObdPortHistory obdPortHistory);
    
    /**
    * 通过ID集合查询ObdPortHistory集合
    * @param list obdPortHistory 实体类ID集合
    * @return List<ObdPortHistory>  实体类集合
    */
    List<ObdPortHistory> selectListByIds(@NotEmpty List<Integer> list);
    
    /**
    * 通过封装对象【模糊】查询ObdPortHistory集合
    * @param obdPortHistory 实体类(百分号需自行组装)
    * @return List<ObdPortHistory>  实体类集合
    */
    List<ObdPortHistory> selectListLikeEntity(@NotNull ObdPortHistory obdPortHistory);

    /**
    * 新增ObdPortHistory对象
    * @param obdPortHistory 实体类
    * @return int  操作条数
    */
    int insert(@NotNull ObdPortHistory obdPortHistory);

    /**
    * 批量新增ObdPortHistory对象
    * @param list obdPortHistory 实体类集合
    * @return int  操作条数
    */
    int insertBatch(@NotEmpty List<ObdPortHistory> list);

    /**
    * 更新ObdPortHistory对象
    * @param obdPortHistory 实体类
    * @return int  操作条数
    */
    int update(@NotNull ObdPortHistory obdPortHistory);

    /**
    * 批量更新ObdPortHistory对象
    * @param list obdPortHistory 实体类集合
    * @return int  操作条数
    */
    int updateBatch(@NotEmpty List<ObdPortHistory> list);

    /**
    * 通过ID删除ObdPortHistory对象
    * @param id obdPortHistoryID
    * @return int  操作条数
    */
    int deleteById(@NotNull Integer id);

    /**
    * 通过封装对象删除ObdPortHistory对象
    * @param obdPortHistory 实体类
    * @return int  操作条数
    */
    int deleteByEntity(@NotNull ObdPortHistory obdPortHistory);
  

    /**
    *  过ID集合删除ObdPortHistory对象 
    * @param list obdPortHistoryID集合
    * @return int  操作条数
    */
    int deleteByIds(@NotEmpty List<Integer> list);


    /**
     * 批量删除原来表数据
     *
     * @param list 列表
     * @return int
     */
    int deleteByBatch(@NotEmpty List<ObdPortHistory> list);
    
    /**
    * 统计所有obdPortHistory对象的数量
    * @return int 所有数量
    */
    int countAll();
    
    /**
    * 统计指定ObdPortHistory对象的数量
    * @param obdPortHistory 实体类
    * @return int 查询数量
    */  
    int countByEntity(@NotNull ObdPortHistory obdPortHistory);
    
}