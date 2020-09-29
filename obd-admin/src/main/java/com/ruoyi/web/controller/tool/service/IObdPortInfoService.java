package com.ruoyi.web.controller.tool.service;
import com.ruoyi.web.controller.tool.mapper.ObdPortInfoMapper;
import com.ruoyi.web.controller.tool.domain.ObdPortInfo;
import java.util.List;

/**
 * (ObdPortInfo)服务层
 *
 * @author CrystalWings
 * @since 2020-09-29 15:16:43
 */

public interface IObdPortInfoService {
    
    /**
    * 通过ID查询单一ObdPortInfo对象
    * @param id  obdPortInfoID
    * @return ObdPortInfo 实体类
    */
    ObdPortInfo selectById(Integer id);

    /**
    * 通过封装对象查询单一ObdPortInfo对象
    * @param obdPortInfo 实体类
    * @return ObdPortInfo 实体类
    */
    ObdPortInfo selectOneByEntity(ObdPortInfo obdPortInfo);

    /**
    * 通过封装对象查询ObdPortInfo集合
    * @param obdPortInfo 实体类
    * @return List<ObdPortInfo>  实体类集合
    */
    List<ObdPortInfo> selectListByEntity(ObdPortInfo obdPortInfo);

    /**
    * 通过ID集合查询ObdPortInfo集合
    * @param ids obdPortInfo 实体类ID集合
    * @return List<ObdPortInfo>  实体类集合
    */
    List<ObdPortInfo> selectListByIds(List<Integer> ids);

    /**
    * 通过封装对象【模糊】查询ObdPortInfo集合
    * @param obdPortInfo 实体类(百分号需自行组装)
    * @return List<ObdPortInfo>  实体类集合
    */
    List<ObdPortInfo> selectListLikeEntity(ObdPortInfo obdPortInfo);

    /**
    * 新增ObdPortInfo对象
    * @param obdPortInfo 实体类
    * @return int  操作条数
    */
    int insert(ObdPortInfo obdPortInfo);

    /**
    * 批量新增ObdPortInfo对象
    * @param list obdPortInfo 实体类集合
    * @return int  操作条数
    */
    int insertBatch(List<ObdPortInfo> list);

    /**
    * 更新ObdPortInfo对象
    * @param obdPortInfo 实体类
    * @return int  操作条数
    */
    int update(ObdPortInfo obdPortInfo);

    /**
    * 批量更新ObdPortInfo对象
    * @param list obdPortInfo 实体类集合
    * @return int  操作条数
    */
    int updateBatch(List<ObdPortInfo> list);

    /**
    * 通过ID删除ObdPortInfo对象
    * @param id obdPortInfoID
    * @return int  操作条数
    */
    int deleteById(Integer id);

    /**
    * 通过封装对象删除ObdPortInfo对象
    * @param obdPortInfo 实体类
    * @return int  操作条数
    */
    int deleteByEntity(ObdPortInfo obdPortInfo);

    /**
    *  过ID集合删除ObdPortInfo对象 
    * @param list obdPortInfoID集合
    * @return int  操作条数
    */
    int deleteByIds(List<Integer> list);

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
    int countByEntity(ObdPortInfo obdPortInfo);
}