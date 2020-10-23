package com.ruoyi.web.controller.data.mapper;

import com.ruoyi.web.controller.data.domain.PcObdBox;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (PcObdBox)表数据库访问层
 *
 * @author makejava
 * @since 2020-10-23 11:36:15
 */
public interface PcObdBoxDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PcObdBox queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<PcObdBox> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param pcObdBox 实例对象
     * @return 对象列表
     */
    List<PcObdBox> queryAll(PcObdBox pcObdBox);

    /**
     * 新增数据
     *
     * @param pcObdBox 实例对象
     * @return 影响行数
     */
    int insert(PcObdBox pcObdBox);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<PcObdBox> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<PcObdBox> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<PcObdBox> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<PcObdBox> entities);

    /**
     * 修改数据
     *
     * @param pcObdBox 实例对象
     * @return 影响行数
     */
    int update(PcObdBox pcObdBox);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}