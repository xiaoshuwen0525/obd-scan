package com.ruoyi.upload.service.impl;


import com.ruoyi.upload.mapper.QueryInfoMapper;
import com.ruoyi.upload.service.IQueryInfoService;
import com.ruoyi.upload.domain.ViewObd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 曾志伟
 * @date: 2020-09-30 10:34
 */
@Service
public class QueryInfoServiceImpl implements IQueryInfoService {

    @Autowired
    private QueryInfoMapper queryInfoMapper;

    /**查询机箱OBD和端口的所有信息*/
    @Override
    public List<ViewObd> queryInfo(){
        return queryInfoMapper.queryInfo();
    }

    /**根据工号查询该工号下的所有信息*/
    @Override
    public List<ViewObd> queryInfoByJobId(ViewObd viewObd) {
        if (null == viewObd.getJobNumber()){
            return null;
        }
        return queryInfoMapper.queryInfoByJobId(viewObd.getJobNumber());
    }

    /**根据工号和机箱号查询该工号下的所有指定机箱信息*/
    @Override
    public List<ViewObd> queryInfoForBox(ViewObd viewObd) {
        if (null == viewObd.getJobNumber() && null == viewObd.getBoxCode()){
            return null;
        }
        return queryInfoMapper.queryInfoForBox(viewObd.getJobNumber(), viewObd.getBoxCode());
    }

    /**根据工号和机箱号和OBD号查询该工号下的所有指定OBD信息*/
    @Override
    public List<ViewObd> queryInfoForObd(ViewObd viewObd) {
        if (null == viewObd.getJobNumber() && null == viewObd.getBoxCode() && null == viewObd.getInfoId()){
            return null;
        }
        return queryInfoMapper.queryInfoForObd(viewObd.getJobNumber(), viewObd.getBoxCode(), viewObd.getInfoId());
    }

    /**根据工号和机箱号和OBD号和端口号查询该工号下的所有指定端口信息*/
    @Override
    public List<ViewObd> queryInfoForPort(ViewObd viewObd) {
        if (null == viewObd.getJobNumber() && null == viewObd.getBoxCode() && null == viewObd.getInfoId() && null == viewObd.getPortId()){
            return null;
        }
        return queryInfoMapper.queryInfoForPort(viewObd.getJobNumber(), viewObd.getBoxCode(), viewObd.getInfoId(), viewObd.getPortId());
    }
}
