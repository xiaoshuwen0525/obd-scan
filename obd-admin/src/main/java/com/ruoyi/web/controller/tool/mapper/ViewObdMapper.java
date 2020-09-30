package com.ruoyi.web.controller.tool.mapper;

import com.ruoyi.web.controller.tool.domain.ViewObd;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import javax.validation.constraints.*;
import java.util.List;

/**
 * VIEW(ViewObd)持久层
 *
 * @author CrystalWings
 * @since 2020-09-30 10:46:39
 */

@Repository
public interface ViewObdMapper {

    /**
    * 通过ID查询单一ViewObd对象
    * @param   id
    * @return ViewObd 实体类
    */
    ViewObd selectById(@NotNull Integer id);
    
    /**
    * 通过封装对象查询单一ViewObd对象
    * @param viewObd 实体类
    * @return ViewObd 实体类
    */
    ViewObd selectOneByEntity(@NotNull ViewObd viewObd);

    /**
    * 通过封装对象查询ViewObd集合
    * @param viewObd 实体类
    * @return List<ViewObd>  实体类集合
    */
    List<ViewObd> selectListByEntity(ViewObd viewObd);
    
    /**
    * 通过ID集合查询ViewObd集合
    * @param list viewObd 实体类ID集合
    * @return List<ViewObd>  实体类集合
    */
    List<ViewObd> selectListByIds(@NotEmpty List<Integer> list);
    
    /**
    * 通过封装对象【模糊】查询ViewObd集合
    * @param viewObd 实体类(百分号需自行组装)
    * @return List<ViewObd>  实体类集合
    */
    List<ViewObd> selectListLikeEntity(@NotNull ViewObd viewObd);

    /**
    * 新增ViewObd对象
    * @param viewObd 实体类
    * @return int  操作条数
    */
    int insert(@NotNull ViewObd viewObd);

    /**
    * 批量新增ViewObd对象
    * @param list viewObd 实体类集合
    * @return int  操作条数
    */
    int insertBatch(@NotEmpty List<ViewObd> list);

    /**
    * 更新ViewObd对象
    * @param viewObd 实体类
    * @return int  操作条数
    */
    int update(@NotNull ViewObd viewObd);

    /**
    * 批量更新ViewObd对象
    * @param list viewObd 实体类集合
    * @return int  操作条数
    */
    int updateBatch(@NotEmpty List<ViewObd> list);

    /**
    * 通过ID删除ViewObd对象
    * @param  id
    * @return int  操作条数
    */
    int deleteById(@NotNull Integer id);

    /**
    * 通过封装对象删除ViewObd对象
    * @param viewObd 实体类
    * @return int  操作条数
    */
    int deleteByEntity(@NotNull ViewObd viewObd);
  

    /**
    *  过ID集合删除ViewObd对象 
    * @param list viewObdID集合
    * @return int  操作条数
    */
    int deleteByIds(@NotEmpty List<Integer> list);
    
    /**
    * 统计所有viewObd对象的数量
    * @return int 所有数量
    */
    int countAll();
    
    /**
    * 统计指定ViewObd对象的数量
    * @param viewObd 实体类
    * @return int 查询数量
    */  
    int countByEntity(@NotNull ViewObd viewObd);
    
}