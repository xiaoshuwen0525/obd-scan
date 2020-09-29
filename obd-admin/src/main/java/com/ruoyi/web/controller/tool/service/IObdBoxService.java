package com.ruoyi.web.controller.tool.service;
import com.ruoyi.web.controller.tool.mapper.ObdBoxMapper;
import com.ruoyi.web.controller.tool.domain.ObdBox;
import java.util.List;

/**
 * (ObdBox)服务层
 *
 * @author CrystalWings
 * @since 2020-09-29 15:16:43
 */

public interface IObdBoxService {
    
    /**
    * 通过ID查询单一ObdBox对象
    * @param id  obdBoxID
    * @return ObdBox 实体类
    */
    ObdBox selectById(Integer id);

    /**
    * 通过封装对象查询单一ObdBox对象
    * @param obdBox 实体类
    * @return ObdBox 实体类
    */
    ObdBox selectOneByEntity(ObdBox obdBox);

    /**
    * 通过封装对象查询ObdBox集合
    * @param obdBox 实体类
    * @return List<ObdBox>  实体类集合
    */
    List<ObdBox> selectListByEntity(ObdBox obdBox);

    /**
    * 通过ID集合查询ObdBox集合
    * @param ids obdBox 实体类ID集合
    * @return List<ObdBox>  实体类集合
    */
    List<ObdBox> selectListByIds(List<Integer> ids);

    /**
    * 通过封装对象【模糊】查询ObdBox集合
    * @param obdBox 实体类(百分号需自行组装)
    * @return List<ObdBox>  实体类集合
    */
    List<ObdBox> selectListLikeEntity(ObdBox obdBox);

    /**
    * 新增ObdBox对象
    * @param obdBox 实体类
    * @return int  操作条数
    */
    int insert(ObdBox obdBox);

    /**
    * 批量新增ObdBox对象
    * @param list obdBox 实体类集合
    * @return int  操作条数
    */
    int insertBatch(List<ObdBox> list);

    /**
    * 更新ObdBox对象
    * @param obdBox 实体类
    * @return int  操作条数
    */
    int update(ObdBox obdBox);

    /**
    * 批量更新ObdBox对象
    * @param list obdBox 实体类集合
    * @return int  操作条数
    */
    int updateBatch(List<ObdBox> list);

    /**
    * 通过ID删除ObdBox对象
    * @param id obdBoxID
    * @return int  操作条数
    */
    int deleteById(Integer id);

    /**
    * 通过封装对象删除ObdBox对象
    * @param obdBox 实体类
    * @return int  操作条数
    */
    int deleteByEntity(ObdBox obdBox);

    /**
    *  过ID集合删除ObdBox对象 
    * @param list obdBoxID集合
    * @return int  操作条数
    */
    int deleteByIds(List<Integer> list);

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
    int countByEntity(ObdBox obdBox);
}