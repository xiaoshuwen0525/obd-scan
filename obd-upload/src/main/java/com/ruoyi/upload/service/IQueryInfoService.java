package com.ruoyi.upload.service;

import com.ruoyi.upload.domain.ViewObd;
import java.util.List;

public interface IQueryInfoService {

    /**查询所有信息*/
    List<ViewObd> queryInfo();

    /**根据工单号查询相关信息*/
    List<ViewObd> queryInfoByJobId(ViewObd viewObd);

    /**根据工单号和BOXCODE查询相关信息*/
    List<ViewObd> queryInfoForBox(ViewObd viewObd);

    /**根据工单号和BOXCODE和OBD唯一ID查询相关信息*/
    List<ViewObd> queryInfoForObd(ViewObd viewObd);

    /**根据工单号和BOXCODE和OBD唯一ID和端口号查询相关信息*/
    List<ViewObd> queryInfoForPort(ViewObd viewObd);
}
