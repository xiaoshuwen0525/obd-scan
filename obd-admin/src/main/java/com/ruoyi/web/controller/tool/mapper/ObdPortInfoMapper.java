package com.ruoyi.web.controller.tool.mapper;

import com.ruoyi.web.controller.tool.domain.ObdPortInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import javax.validation.constraints.*;
import java.util.List;

/**
 * (ObdPortInfo)持久层
 *
 * @author CrystalWings
 * @since 2020-09-29 15:16:43
 */

@Repository
public interface ObdPortInfoMapper {

    /**
    * 通过ID查询单一ObdPortInfo对象
    * @param id  obdPortInfoID
    * @return ObdPortInfo 实体类
    */
    ObdPortInfo selectById(@NotNull Integer id);
    
    /**
    * 通过封装对象查询单一ObdPortInfo对象
    * @param obdPortInfo 实体类
    * @return ObdPortInfo 实体类
    */
    ObdPortInfo selectOneByEntity(@NotNull ObdPortInfo obdPortInfo);

    /**
    * 通过封装对象查询ObdPortInfo集合
    * @param obdPortInfo 实体类
    * @return List<ObdPortInfo>  实体类集合
    */
    List<ObdPortInfo> selectListByEntity(ObdPortInfo obdPortInfo);
    
    /**
    * 通过ID集合查询ObdPortInfo集合
    * @param list obdPortInfo 实体类ID集合
    * @return List<ObdPortInfo>  实体类集合
    */
    List<ObdPortInfo> selectListByIds(@NotEmpty List<Integer> list);
    
    /**
    * 通过封装对象【模糊】查询ObdPortInfo集合
    * @param obdPortInfo 实体类(百分号需自行组装)
    * @return List<ObdPortInfo>  实体类集合
    */
    List<ObdPortInfo> selectListLikeEntity(@NotNull ObdPortInfo obdPortInfo);

    /**
    * 新增ObdPortInfo对象
    * @param obdPortInfo 实体类
    * @return int  操作条数
    */
    int insert(@NotNull ObdPortInfo obdPortInfo);

    /**
    * 批量新增ObdPortInfo对象
    * @param list obdPortInfo 实体类集合
    * @return int  操作条数
    */
    int insertBatch(@NotEmpty List<ObdPortInfo> list);

    /**
    * 更新ObdPortInfo对象
    * @param obdPortInfo 实体类
    * @return int  操作条数
    */
    int update(@NotNull ObdPortInfo obdPortInfo);

    /**
    * 批量更新ObdPortInfo对象
    * @param list obdPortInfo 实体类集合
    * @return int  操作条数
    */
    int updateBatch(@NotEmpty List<ObdPortInfo> list);

    /**
    * 通过ID删除ObdPortInfo对象
    * @param id obdPortInfoID
    * @return int  操作条数
    */
    int deleteById(@NotNull Integer id);

    /**
    * 通过封装对象删除ObdPortInfo对象
    * @param obdPortInfo 实体类
    * @return int  操作条数
    */
    int deleteByEntity(@NotNull ObdPortInfo obdPortInfo);
  

    /**
    *  过ID集合删除ObdPortInfo对象 
    * @param list obdPortInfoID集合
    * @return int  操作条数
    */
    int deleteByIds(@NotEmpty List<Integer> list);
    
    /**
    * 统计所有obdPortInfo对象的数量
    * @return int 所有数量
    */
    int countAll();
    
    /**
    * 统计指定ObdPortInfo对象的数量
    * @param obdPortInfo 实体类
    * @return int 查询数量
    */  
    int countByEntity(@NotNull ObdPortInfo obdPortInfo);
    
}