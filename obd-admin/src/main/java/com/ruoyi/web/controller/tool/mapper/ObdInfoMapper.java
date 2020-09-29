package com.ruoyi.web.controller.tool.mapper;

import com.ruoyi.web.controller.tool.domain.ObdInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import javax.validation.constraints.*;
import java.util.List;

/**
 * (ObdInfo)持久层
 *
 * @author CrystalWings
 * @since 2020-09-29 15:16:43
 */

@Repository
public interface ObdInfoMapper {

    /**
    * 通过ID查询单一ObdInfo对象
    * @param id  obdInfoID
    * @return ObdInfo 实体类
    */
    ObdInfo selectById(@NotNull Integer id);
    
    /**
    * 通过封装对象查询单一ObdInfo对象
    * @param obdInfo 实体类
    * @return ObdInfo 实体类
    */
    ObdInfo selectOneByEntity(@NotNull ObdInfo obdInfo);

    /**
    * 通过封装对象查询ObdInfo集合
    * @param obdInfo 实体类
    * @return List<ObdInfo>  实体类集合
    */
    List<ObdInfo> selectListByEntity(ObdInfo obdInfo);
    
    /**
    * 通过ID集合查询ObdInfo集合
    * @param list obdInfo 实体类ID集合
    * @return List<ObdInfo>  实体类集合
    */
    List<ObdInfo> selectListByIds(@NotEmpty List<Integer> list);
    
    /**
    * 通过封装对象【模糊】查询ObdInfo集合
    * @param obdInfo 实体类(百分号需自行组装)
    * @return List<ObdInfo>  实体类集合
    */
    List<ObdInfo> selectListLikeEntity(@NotNull ObdInfo obdInfo);

    /**
    * 新增ObdInfo对象
    * @param obdInfo 实体类
    * @return int  操作条数
    */
    int insert(@NotNull ObdInfo obdInfo);

    /**
    * 批量新增ObdInfo对象
    * @param list obdInfo 实体类集合
    * @return int  操作条数
    */
    int insertBatch(@NotEmpty List<ObdInfo> list);

    /**
    * 更新ObdInfo对象
    * @param obdInfo 实体类
    * @return int  操作条数
    */
    int update(@NotNull ObdInfo obdInfo);

    /**
    * 批量更新ObdInfo对象
    * @param list obdInfo 实体类集合
    * @return int  操作条数
    */
    int updateBatch(@NotEmpty List<ObdInfo> list);

    /**
    * 通过ID删除ObdInfo对象
    * @param id obdInfoID
    * @return int  操作条数
    */
    int deleteById(@NotNull Integer id);

    /**
    * 通过封装对象删除ObdInfo对象
    * @param obdInfo 实体类
    * @return int  操作条数
    */
    int deleteByEntity(@NotNull ObdInfo obdInfo);
  

    /**
    *  过ID集合删除ObdInfo对象 
    * @param list obdInfoID集合
    * @return int  操作条数
    */
    int deleteByIds(@NotEmpty List<Integer> list);
    
    /**
    * 统计所有obdInfo对象的数量
    * @return int 所有数量
    */
    int countAll();
    
    /**
    * 统计指定ObdInfo对象的数量
    * @param obdInfo 实体类
    * @return int 查询数量
    */  
    int countByEntity(@NotNull ObdInfo obdInfo);
    
}