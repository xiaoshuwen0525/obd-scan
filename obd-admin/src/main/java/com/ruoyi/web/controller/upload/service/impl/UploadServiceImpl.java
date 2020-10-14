package com.ruoyi.web.controller.upload.service.impl;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.PageUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.controller.upload.domain.*;
import com.ruoyi.web.controller.upload.mapper.UploadMapper;
import com.ruoyi.web.controller.upload.service.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * @author:
 * @date:
 */
@Service
public class UploadServiceImpl implements IUploadService {


    @Autowired
    private UploadMapper uploadMapper;

    @Override
    @Transactional
    public AjaxResult uploadInformation(ObdVO obd) {
        System.out.println(obd.toString());

        //建机箱
        int boxId  = 0;
        ObdBox obdBox = getObdBox(obd);
        uploadMapper.insertObdBox(obdBox);
        boxId = obdBox.getId();

        JSONArray parse = JSONUtil.parseArray(obd.getPortList());
        Iterator<Object> it = parse.iterator();
        while (it.hasNext()){
            //string 转对象
            Object next = it.next();
            ObjectMapper objectMapper = new ObjectMapper();
            InfoVO infoVO = objectMapper.convertValue(next, InfoVO.class);
            //建obd
            ObdInfo obdInfo = getObdInfo(boxId);
            uploadMapper.insertObdInfo(obdInfo);
            int obdId = obdInfo.getId();

            for (ObdPortInfo port:  infoVO.getPortData()){
                //存端口
                port.setObdId(obdId);
                if(port.getPortCode()!=null && !"".equals(port.getPortCode())) {
                     port.setStatus(0);
                }else {
                    port.setStatus(1);
                }
                uploadMapper.insertPort(port);
            }

            if(uploadMapper.countByPortStatus(obdId)>0){
                ObdInfo obdInfo1 = new ObdInfo();
                obdInfo1.setId(obdId);
                obdInfo1.setStatus(1);
                uploadMapper.updateObdInfo(obdInfo1);
                ObdBox obdBox1 = new ObdBox();
                obdBox1.setId(boxId);
                obdBox1.setExceptionType(2);
                obdBox1.setExceptionInfo("存在端口识别异常");
                uploadMapper.updateObdBox(obdBox1);
            }else {
                ObdInfo obdInfo1 = new ObdInfo();
                obdInfo1.setId(obdId);
                obdInfo1.setStatus(0);
                uploadMapper.updateObdInfo(obdInfo1);
            }
        }

        return AjaxResult.success();
    }

    @Override
    public int uploadObdBox(ObdBox obdBox) {
        if(obdBox.getBoxCode()!=null && !"".equals(obdBox.getBoxCode())){
            obdBox.setStatus(0);
        }else{
            obdBox.setStatus(1);
            obdBox.setExceptionType(1);
            obdBox.setExceptionInfo("无法识别");
        }
        return uploadMapper.insertObdBox(obdBox);
    }

    @Override
    public int uploadObdInfo(ObdInfo obdInfo) {
        return uploadMapper.insertObdInfo(obdInfo);
    }

    @Override
    public int uploadObdPost(ObdPortInfo obdPortInfo) {
        if (obdPortInfo.getPortCode() != null && !"".equals(obdPortInfo.getPortCode())) {
            obdPortInfo.setStatus(0);

        } else {
            obdPortInfo.setStatus(1);
        }
        return uploadMapper.insertPort(obdPortInfo);
    }

    @Override
    public List<ObdBoxVO> obdBoxByJobNumber(String JobNumber) {
        List<ObdBoxVO> list = uploadMapper.selectBoxByJobNumber(JobNumber);
        for (ObdBoxVO obdBox:list){
            obdBox.setStatus(changeStatus(obdBox.getStatus()));
            if("1".equals(obdBox.getExceptionType())){
                obdBox.setExceptionType("盒子异常");
            }else if ("2".equals(obdBox.getExceptionType())){
                obdBox.setExceptionType("obd异常");
            }else {
                obdBox.setExceptionType("正常");
            }
        }
        return list;
    }

    @Override
    public List<ObdInfoVO> infoByBoxId(String boxId) {
        List<ObdInfoVO> list = uploadMapper.selectInfoByBoxId(boxId);
        for (ObdInfoVO obdInfo:list){
            obdInfo.setStatus(changeStatus(obdInfo.getStatus()));
        }
        return list;
    }

    @Override
    public List<ObdPortInfoVO> portByObdId(String obdId) {
        List<ObdPortInfoVO> list = uploadMapper.selectPortByObdId(obdId);
        for (ObdPortInfoVO port:list){
            port.setStatus(changeStatus(port.getStatus()));
        }
        return list;
    }

    @Override
    public ObdBoxVO selectBoxById(String id) {
        String one = "1";
        ObdBoxVO obdBox = uploadMapper.selectBoxById(id);
        obdBox.setStatus(changeStatus(obdBox.getStatus()));
        if(one.equals(obdBox.getExceptionType())){
            obdBox.setExceptionType("盒子异常");
        }else{
            obdBox.setExceptionType("obd异常");
        }
        return obdBox;
    }

    @Override
    public ObdInfoVO selectInfoById(String id) {
        ObdInfoVO obdInfo = uploadMapper.selectInfoById(id);
        obdInfo.setStatus(changeStatus(obdInfo.getStatus()));
        return obdInfo;
    }

    @Override
    public ObdPortInfoVO selectPortById(String id) {
        ObdPortInfoVO obdPort = uploadMapper.selectPortById(id);
        obdPort.setStatus(obdPort.getStatus());
        return obdPort;
    }

    @Override
    public int updateObdPort(ObdPortInfo obdPortInfo) {
        return uploadMapper.updateObdPort(obdPortInfo);
    }

    @Override
    public int updateObdBox(ObdBox obdBox) {
        return uploadMapper.updateObdBox(obdBox);
    }

    @Override
    public int updateObdInfo(ObdInfo obdInfo) {
        return uploadMapper.updateObdInfo(obdInfo);
    }

    @Override
    public PageInfo<ObdBoxVO> selectObdByJobNumber(String jobNumber, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        List<ObdBoxVO> obdBoxList = uploadMapper.selectBoxByJobNumber(jobNumber);
        for (ObdBoxVO obdBox:obdBoxList){
            List<ObdInfoVO> obdInfoList = uploadMapper.selectInfoByBoxId(obdBox.getId().toString());
            for (ObdInfoVO obdInfo:obdInfoList){
                List<ObdPortInfoVO> obdPortInfoList = uploadMapper.selectPortByObdId(obdInfo.getId().toString());
                obdInfo.setStatus(changeStatus(obdInfo.getStatus()));
                for (ObdPortInfoVO obdPortInfo:obdPortInfoList){
                    obdPortInfo.setStatus(changeStatus(obdPortInfo.getStatus()));
                }
                obdInfo.setObdPortInfoVOList(obdPortInfoList);
            }
            obdBox.setStatus(changeStatus(obdBox.getStatus()));
            obdBox.setObdInfoVOList(obdInfoList);
            if("1".equals(obdBox.getExceptionType())){
                obdBox.setExceptionType("盒子异常");
            }else{
                obdBox.setExceptionType("obd异常");
            }
        }
        int totalPage = PageUtil.totalPage(obdBoxList.size(), pageSize)+1;
        if(pageNum>totalPage){
            return null;
        }
        PageInfo<ObdBoxVO> pageInfo = new PageInfo<ObdBoxVO>(obdBoxList);
        return pageInfo;
    }

    @Override
    public ObdBoxVO selectObdById(int id) {
        String one = "1";
        ObdBoxVO obdBoxVO = uploadMapper.selectBoxById(Integer.toString(id));
        List<ObdInfoVO> obdInfoList = uploadMapper.selectInfoByBoxId(obdBoxVO.getId().toString());
        for (ObdInfoVO obdInfo : obdInfoList) {
            List<ObdPortInfoVO> obdPortInfoList = uploadMapper.selectPortByObdId(obdInfo.getId().toString());
            obdInfo.setStatus(changeStatus(obdInfo.getStatus()));
            for (ObdPortInfoVO obdPortInfo : obdPortInfoList) {
                obdPortInfo.setStatus(changeStatus(obdPortInfo.getStatus()));
            }
            obdInfo.setObdPortInfoVOList(obdPortInfoList);
        }
        obdBoxVO.setStatus(changeStatus(obdBoxVO.getStatus()));
        obdBoxVO.setObdInfoVOList(obdInfoList);
        if (one.equals(obdBoxVO.getExceptionType())) {
            obdBoxVO.setExceptionType("盒子异常");
        } else {
            obdBoxVO.setExceptionType("obd异常");
        }
        return obdBoxVO;
    }

    @Override
    public AjaxResult updateObd(UpdateObd obd) {
        System.out.println(obd.toString());
        if(obd.getBoxCode()!=null && "".equals(obd.getBoxCode())){
            ObdBox obdBox = new ObdBox();
            obdBox.setId(Integer.parseInt(obd.getId()));
            obdBox.setBoxCode(obd.getBoxCode());
            if(obd.getBoxCode()!=null && !"".equals(obd.getBoxCode())){
                obdBox.setStatus(0);
            }else {
                obdBox.setStatus(1);
            }
            uploadMapper.updateObdBox(obdBox);
        }
        JSONArray parse = JSONUtil.parseArray(obd.getPortList());
        Iterator<Object> it = parse.iterator();
        while (it.hasNext()){
            //string 转对象
            Object next = it.next();
            ObjectMapper objectMapper = new ObjectMapper();
            ObdPortInfo obdPortInfo = objectMapper.convertValue(next, ObdPortInfo.class);
            uploadMapper.updateObdPort(obdPortInfo);
            if(uploadMapper.countByPortStatus(obdPortInfo.getObdId())>0){
                ObdInfo obdInfo1 = new ObdInfo();
                obdInfo1.setId(obdPortInfo.getObdId());
                obdInfo1.setStatus(1);
                uploadMapper.updateObdInfo(obdInfo1);
                ObdBox obdBox1 = new ObdBox();
                obdBox1.setId(Integer.parseInt(obd.getId()));
                obdBox1.setExceptionType(2);
                obdBox1.setExceptionInfo("存在端口识别异常");
                uploadMapper.updateObdBox(obdBox1);
            }else {
                ObdInfo obdInfo1 = new ObdInfo();
                obdInfo1.setId(obdPortInfo.getId());
                obdInfo1.setStatus(0);
                uploadMapper.updateObdInfo(obdInfo1);
                ObdBox obdBox1 = new ObdBox();
                obdBox1.setId(Integer.parseInt(obd.getId()));
                obdBox1.setExceptionType(0);
                obdBox1.setExceptionInfo("");
                uploadMapper.updateObdBox(obdBox1);
            }
        }
        return AjaxResult.success();
    }


    /**
     * 获得 obdBox 实体类
     * @param obd 前端全部数据
     * @return ObdBox 实体类
     */
    private ObdBox getObdBox(Obd obd){
        ObdBox obdBox = new ObdBox();
        obdBox.setCreateTime(new Date());
        obdBox.setBoxCode(obd.getBoxCode());
        obdBox.setJobNumber(obd.getJobNumber());
        if(obd.getBoxCode()!=null && !"".equals(obd.getBoxCode())){
            obdBox.setStatus(0);
        }else{
            obdBox.setStatus(1);
        }
        obdBox.setExceptionType(obd.getExceptionType());
        obdBox.setExceptionInfo(obd.getExceptionInfo());
        return obdBox;
    }

    /**
     * 获得 obdBox 实体类
     * @param obd 前端全部数据
     * @return ObdBox 实体类
     */
    private ObdBox getObdBox(ObdVO obd){
        ObdBox obdBox = new ObdBox();
        obdBox.setCreateTime(new Date());
        obdBox.setBoxCode(obd.getBoxCode());
        obdBox.setJobNumber(obd.getJobNumber());
        if(obd.getBoxCode()!=null && !"".equals(obd.getBoxCode())){
            obdBox.setStatus(0);
        }else{
            obdBox.setStatus(1);
        }
        return obdBox;
    }


    /**
     * 将status改文字
     * @param status 值
     * @return 异常状态文字
     */
    private String changeStatus(String status){
        String zero = "0";
       if(status.equals(zero)){
           return "正常";
       }else {
           return "异常";
       }

    }



    /**
     * 获得 ObdInfo 实体类
     * @param boxId 盒子id
     * @return ObdInfo 实体类
     */
    private ObdInfo getObdInfo(int boxId ){
        ObdInfo obdInfo = new ObdInfo();
        obdInfo.setBoxId(boxId);
        return obdInfo;
    }

    /**
     * 获得 ObdPortInfo 实体类
     * @param portSer 端口序号
     * @param portCode 端口sn码
     * @param obdId obdId
     * @return ObdPortInfo 实体类
     */
    private ObdPortInfo getPortInfo(String portSer, String portCode, int obdId){
        ObdPortInfo obdPortInfo = new ObdPortInfo();
        if (portSer!= null &&  !"".equals(portSer)){
            obdPortInfo.setPortSer(Integer.parseInt(portSer));
           if(portCode!=null  && !"".equals(portCode)) {
               obdPortInfo.setStatus(0);
           }else {
               obdPortInfo.setStatus(1);
           }
        }
        obdPortInfo.setPortCode(portCode);
        obdPortInfo.setObdId(obdId);
        return obdPortInfo;
    }

    /**
     * 获得 ObdPortInfo 实体类
     * @param portSer 端口序号
     * @param portCode 端口sn码
     * @param obdId obdId
     * @return ObdPortInfo 实体类
     */
    private ObdPortInfo getPort(String portSer, String portCode, int obdId){
        ObdPortInfo obdPortInfo = new ObdPortInfo();
        if (portSer!= null &&  !"".equals(portSer)){
            obdPortInfo.setPortSer((Integer.parseInt(portSer)+1));
            if(!"".equals(portCode)) {
                obdPortInfo.setStatus(0);
            }else {
                obdPortInfo.setStatus(1);
            }
        }
        obdPortInfo.setPortCode(portCode);
        obdPortInfo.setObdId(obdId);
        return obdPortInfo;
    }

    /**
     * 获得 图片路径
     * @param workNumber 工号
     * @param multipartFile 图片文件
     * @return path 图片路径
     */
    private String uploadPicture(String workNumber, MultipartFile multipartFile)  {
        Properties props = System.getProperties();
        String path = "";
        String uploadPath="obd-admin/src/main/resources/static/obdImg";
        String uploadPaths = props.getProperty("user.dir")+ File.separator+uploadPath.replace("/", "\\");
        String folder = uploadPaths+ File.separator + workNumber + File.separator;
        if (!FileUtil.exist(folder)) {
            FileUtil.mkdir(folder);
        }
        String fileName = UUID.randomUUID().toString() + "@" + multipartFile.getOriginalFilename();
        try {
            File upload = FileUtil.writeBytes(multipartFile.getBytes(), folder + fileName);
            if(upload.length() > 0){
                path = folder + fileName;
            }
        }catch (Exception e){
            return null;
        }
        return path.replace("\\", "/");
    }



}
