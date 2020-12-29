package com.ruoyi.web.controller.upload.mapper;

import com.ruoyi.web.controller.upload.domain.ObdInfoHistory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import javax.validation.constraints.*;
import java.util.List;

/**
 * (ObdInfoHistory)持久层
 *
 * @author CrystalWings
 * @since 2020-12-27 14:21:28
 */

@Repository
public interface ObdInfoHistoryMapper {

    /**
    * 通过ID查询单一ObdInfoHistory对象
    * @param id  obdInfoHistoryID
    * @return ObdInfoHistory 实体类
    */
    ObdInfoHistory selectById(@NotNull Integer id);


    /**
     * 获取原表数据
     *
     * @param boxId 框标识
     * @return {@link List<ObdInfoHistory>}
     */
    List<ObdInfoHistory> selectByBoxId( Integer boxId);
    
    /**
    * 通过封装对象查询单一ObdInfoHistory对象
    * @param obdInfoHistory 实体类
    * @return ObdInfoHistory 实体类
    */
    ObdInfoHistory selectOneByEntity(@NotNull ObdInfoHistory obdInfoHistory);

    /**
    * 通过封装对象查询ObdInfoHistory集合
    * @param obdInfoHistory 实体类
    * @return List<ObdInfoHistory>  实体类集合
    */
    List<ObdInfoHistory> selectListByEntity(ObdInfoHistory obdInfoHistory);
    
    /**
    * 通过ID集合查询ObdInfoHistory集合
    * @param list obdInfoHistory 实体类ID集合
    * @return List<ObdInfoHistory>  实体类集合
    */
    List<ObdInfoHistory> selectListByIds(@NotEmpty List<Integer> list);
    
    /**
    * 通过封装对象【模糊】查询ObdInfoHistory集合
    * @param obdInfoHistory 实体类(百分号需自行组装)
    * @return List<ObdInfoHistory>  实体类集合
    */
    List<ObdInfoHistory> selectListLikeEntity(@NotNull ObdInfoHistory obdInfoHistory);

    /**
    * 新增ObdInfoHistory对象
    * @param obdInfoHistory 实体类
    * @return int  操作条数
    */
    int insert(@NotNull ObdInfoHistory obdInfoHistory);

    /**
    * 批量新增ObdInfoHistory对象
    * @param list obdInfoHistory 实体类集合
    * @return int  操作条数
    */
    int insertBatch(@NotEmpty List<ObdInfoHistory> list);

    /**
    * 更新ObdInfoHistory对象
    * @param obdInfoHistory 实体类
    * @return int  操作条数
    */
    int update(@NotNull ObdInfoHistory obdInfoHistory);

    /**
    * 批量更新ObdInfoHistory对象
    * @param list obdInfoHistory 实体类集合
    * @return int  操作条数
    */
    int updateBatch(@NotEmpty List<ObdInfoHistory> list);

    /**
    * 通过ID删除ObdInfoHistory对象
    * @param id obdInfoHistoryID
    * @return int  操作条数
    */
    int deleteById(@NotNull Integer id);

    /**
    * 通过封装对象删除ObdInfoHistory对象
    * @param obdInfoHistory 实体类
    * @return int  操作条数
    */
    int deleteByEntity(@NotNull ObdInfoHistory obdInfoHistory);
  

    /**
    *  过ID集合删除ObdInfoHistory对象 
    * @param list obdInfoHistoryID集合
    * @return int  操作条数
    */
    int deleteByIds(@NotEmpty List<Integer> list);

    /**
     * 批量删除原来表数据
     * @param list obdInfoHistory 实体类集合
     * @return int  操作条数
     */
    int deleteByBatch(@NotEmpty List<ObdInfoHistory> list);
    
    /**
    * 统计所有obdInfoHistory对象的数量
    * @return int 所有数量
    */
    int countAll();
    
    /**
    * 统计指定ObdInfoHistory对象的数量
    * @param obdInfoHistory 实体类
    * @return int 查询数量
    */  
    int countByEntity(@NotNull ObdInfoHistory obdInfoHistory);
    
}