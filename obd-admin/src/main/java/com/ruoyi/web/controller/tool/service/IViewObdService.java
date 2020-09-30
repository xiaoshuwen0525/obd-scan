package com.ruoyi.web.controller.tool.service;
import com.ruoyi.web.controller.tool.mapper.ViewObdMapper;
import com.ruoyi.web.controller.tool.domain.ViewObd;
import java.util.List;

/**
 * VIEW(ViewObd)服务层
 *
 * @author CrystalWings
 * @since 2020-09-30 10:46:39
 */

public interface IViewObdService {
    
    /**
    * 通过ID查询单一ViewObd对象
    * @param   id
    * @return ViewObd 实体类
    */
    ViewObd selectById(Integer id);

    /**
    * 通过封装对象查询单一ViewObd对象
    * @param viewObd 实体类
    * @return ViewObd 实体类
    */
    ViewObd selectOneByEntity(ViewObd viewObd);

    /**
    * 通过封装对象查询ViewObd集合
    * @param viewObd 实体类
    * @return List<ViewObd>  实体类集合
    */
    List<ViewObd> selectListByEntity(ViewObd viewObd);

    /**
    * 通过ID集合查询ViewObd集合
    * @param ids viewObd 实体类ID集合
    * @return List<ViewObd>  实体类集合
    */
    List<ViewObd> selectListByIds(List<Integer> ids);

    /**
    * 通过封装对象【模糊】查询ViewObd集合
    * @param viewObd 实体类(百分号需自行组装)
    * @return List<ViewObd>  实体类集合
    */
    List<ViewObd> selectListLikeEntity(ViewObd viewObd);

    /**
    * 新增ViewObd对象
    * @param viewObd 实体类
    * @return int  操作条数
    */
    int insert(ViewObd viewObd);

    /**
    * 批量新增ViewObd对象
    * @param list viewObd 实体类集合
    * @return int  操作条数
    */
    int insertBatch(List<ViewObd> list);

    /**
    * 更新ViewObd对象
    * @param viewObd 实体类
    * @return int  操作条数
    */
    int update(ViewObd viewObd);

    /**
    * 批量更新ViewObd对象
    * @param list viewObd 实体类集合
    * @return int  操作条数
    */
    int updateBatch(List<ViewObd> list);

    /**
    * 通过ID删除ViewObd对象
    * @param  id
    * @return int  操作条数
    */
    int deleteById(Integer id);

    /**
    * 通过封装对象删除ViewObd对象
    * @param viewObd 实体类
    * @return int  操作条数
    */
    int deleteByEntity(ViewObd viewObd);

    /**
    *  过ID集合删除ViewObd对象 
    * @param list viewObdID集合
    * @return int  操作条数
    */
    int deleteByIds(List<Integer> list);

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
    int countByEntity(ViewObd viewObd);
}