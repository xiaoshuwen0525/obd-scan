package com.ruoyi.upload.service.impl;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import com.ruoyi.common.core.domain.AjaxResult;

import com.ruoyi.upload.domain.*;
import com.ruoyi.upload.mapper.UploadMapper;
import com.ruoyi.upload.service.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;


/**
 * @author:
 * @date:
 */
@Service
public class UploadServiceImpl implements IUploadService {


    @Autowired
    private UploadMapper uploadMapper;

    @Override
    public AjaxResult uploadInformation(Obd obd) {
        int boxId  = 0;
        String jobNumber = obd.getJobNumber();
        if(uploadMapper.countByBoxCode(obd.getBoxCode()) == 0){
            boxId = uploadMapper.insertObdBox(getObdBox(obd));
        }
        Map<String,Integer> info = new HashMap<String, Integer>(20);
        for (String obdInfo:obd.getObdList()){
           int  infoId =  uploadMapper.insertObdInfo(getObdInfo(boxId));
           info.put(obdInfo,infoId);
        }
        for (ObdPost obdPost : obd.getPostList()){
            uploadMapper.insertPort(getPortInfo(obdPost.getPortSer(),obdPost.getPortCode(),info.get(obdPost.getMarking())));
        }
        for (String obdInfo:obd.getObdList()){
            int  infoId =  info.get(obdInfo);
            if(uploadMapper.countByPortStatus(infoId)>0){
                ObdInfo obdInfo1 = new ObdInfo();
                obdInfo1.setId(info.get(obdInfo));
                obdInfo1.setStatus(1);
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
        return uploadMapper.selectBoxByJobNumber(JobNumber);
    }

    @Override
    public List<ObdInfoVO> obdInfoByJobNumber(String boxId, String jobNumber) {
        return uploadMapper.selectInfoByJobNumber(jobNumber,boxId);
    }

    @Override
    public List<ObdPortInfoVO> obdPortByJobNumber(String obdId) {
        return uploadMapper.selectPortByJobNumber(obdId);
    }

    @Override
    public List<ObdBoxVO> selectBoxById(String id) {
        return uploadMapper.selectBoxById(id);
    }

    @Override
    public List<ObdInfoVO> selectInfoById(String id) {
        return uploadMapper.selectInfoById(id);
    }

    @Override
    public List<ObdPortInfoVO> selectPortById(String id) {
        return uploadMapper.selectPortById(id);
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
            obdBox.setStatus(1);
        }else{
            obdBox.setStatus(0);
        }
        obdBox.setExceptionType(obd.getExceptionType());
        obdBox.setExceptionInfo(obd.getExceptionInfo());
        return obdBox;
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
