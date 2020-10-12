package com.ruoyi.upload.mapper;


import com.ruoyi.upload.domain.ViewObd;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryInfoMapper {

    /**查询所有信息*/
    List<ViewObd> queryInfo();

    /**根据工单号查询相关信息*/
    List<ViewObd> queryInfoByJobId(String jobNumber);

    /**根据工单号和BOXCODE查询相关信息*/
    List<ViewObd> queryInfoForBox(String jobNumber, String boxCode);

    /**根据工单号和BOXCODE和OBD唯一ID查询相关信息*/
    List<ViewObd> queryInfoForObd(String jobNumber, String boxCode, Integer infoId);

    /**根据工单号和BOXCODE和OBD唯一ID和端口号查询相关信息*/
    List<ViewObd> queryInfoForPort(String jobNumber, String boxCode, Integer infoId, Integer portId);

}
