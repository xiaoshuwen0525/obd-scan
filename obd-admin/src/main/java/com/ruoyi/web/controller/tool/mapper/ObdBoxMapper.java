package com.ruoyi.web.controller.tool.mapper;

import com.ruoyi.web.controller.tool.domain.ObdBox;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import javax.validation.constraints.*;
import java.util.List;

/**
 * (ObdBox)持久层
 *
 * @author CrystalWings
 * @since 2020-09-29 15:16:43
 */

@Repository
public interface ObdBoxMapper {

    /**
    * 通过ID查询单一ObdBox对象
    * @param id  obdBoxID
    * @return ObdBox 实体类
    */
    ObdBox selectById(@NotNull Integer id);
    
    /**
    * 通过封装对象查询单一ObdBox对象
    * @param obdBox 实体类
    * @return ObdBox 实体类
    */
    ObdBox selectOneByEntity(@NotNull ObdBox obdBox);

    /**
    * 通过封装对象查询ObdBox集合
    * @param obdBox 实体类
    * @return List<ObdBox>  实体类集合
    */
    List<ObdBox> selectListByEntity(ObdBox obdBox);
    
    /**
    * 通过ID集合查询ObdBox集合
    * @param list obdBox 实体类ID集合
    * @return List<ObdBox>  实体类集合
    */
    List<ObdBox> selectListByIds(@NotEmpty List<Integer> list);
    
    /**
    * 通过封装对象【模糊】查询ObdBox集合
    * @param obdBox 实体类(百分号需自行组装)
    * @return List<ObdBox>  实体类集合
    */
    List<ObdBox> selectListLikeEntity(@NotNull ObdBox obdBox);

    /**
    * 新增ObdBox对象
    * @param obdBox 实体类
    * @return int  操作条数
    */
    int insert(@NotNull ObdBox obdBox);

    /**
    * 批量新增ObdBox对象
    * @param list obdBox 实体类集合
    * @return int  操作条数
    */
    int insertBatch(@NotEmpty List<ObdBox> list);

    /**
    * 更新ObdBox对象
    * @param obdBox 实体类
    * @return int  操作条数
    */
    int update(@NotNull ObdBox obdBox);

    /**
    * 批量更新ObdBox对象
    * @param list obdBox 实体类集合
    * @return int  操作条数
    */
    int updateBatch(@NotEmpty List<ObdBox> list);

    /**
    * 通过ID删除ObdBox对象
    * @param id obdBoxID
    * @return int  操作条数
    */
    int deleteById(@NotNull Integer id);

    /**
    * 通过封装对象删除ObdBox对象
    * @param obdBox 实体类
    * @return int  操作条数
    */
    int deleteByEntity(@NotNull ObdBox obdBox);
  

    /**
    *  过ID集合删除ObdBox对象 
    * @param list obdBoxID集合
    * @return int  操作条数
    */
    int deleteByIds(@NotEmpty List<Integer> list);
    
    /**
    * 统计所有obdBox对象的数量
    * @return int 所有数量
    */
    int countAll();
    
    /**
    * 统计指定ObdBox对象的数量
    * @param obdBox 实体类
    * @return int 查询数量
    */  
    int countByEntity(@NotNull ObdBox obdBox);
    
}