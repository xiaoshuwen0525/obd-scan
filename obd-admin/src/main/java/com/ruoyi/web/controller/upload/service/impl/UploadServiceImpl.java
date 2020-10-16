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
import org.yaml.snakeyaml.events.Event;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

/**
 * @author:
 * @date:
 */
@Service
public class UploadServiceImpl implements IUploadService {


    @Autowired
    private UploadMapper uploadMapper;

    private ReentrantLock lock = new ReentrantLock();

    private final  int portCount = 8;

    @Override
    @Transactional
    public AjaxResult uploadInformation(ObdVO obd) {
        System.out.println(obd.toString());
        String msg = "";
        lock.lock();
        try {
            //建机箱
            int boxId = 0;
            ObdBox obdBox = new ObdBox();
            if (!"".equals(obd.getBoxCode())) {
                obdBox.setCreateTime(new Date());
                obdBox.setBoxCode(obd.getBoxCode());
                obdBox.setJobNumber(obd.getJobNumber());
                //识别不出是null
                if (obd.getBoxCode() != null) {
                    obdBox.setStatus(0);
                } else {
                    obdBox.setBoxCode("");
                    obdBox.setStatus(1);
                    obdBox.setExceptionType(1);
                    obdBox.setExceptionInfo("盒子二维码识别不出");
                }
                uploadMapper.insertObdBox(obdBox);
                boxId = obdBox.getId();
            }

            InfoListVO InfoListVO = JSONUtil.toBean("{infoVOList:" + obd.getPortList() + "}", InfoListVO.class);
            for(InfoVO infoVO:InfoListVO.getInfoVOList()){
                //建obd
                if (getObdFlag(infoVO.getPortData())) {
                    ObdInfo obdInfo = new ObdInfo();
                    obdInfo.setBoxId(boxId);
                    obdInfo.setStatus(0);
                    uploadMapper.insertObdInfo(obdInfo);
                    int obdId = obdInfo.getId();
                    for (ObdPortInfo port : infoVO.getPortData()) {
                        //存端口
                        port.setObdId(obdId);
                        if (port.getPortSer() != null ) {
                            if (port.getPortCode() != null) {
                                port.setStatus(0);
                            } else {
                                port.setStatus(1);
                                port.setPortCode("");
                            }
                            if(port.getPortCode()!=null){
                                if (!"".equals(port.getPortCode()) && isNumber(port.getPortCode())) {
                                    uploadMapper.insertPort(port);
                                }
                            }
                        }

                    }
                    if (uploadMapper.countByPortStatus(obdId) > 0) {
                        obdInfo.setStatus(1);
                        uploadMapper.updateObdInfo(obdInfo);
                        obdBox.setExceptionType(2);
                        obdBox.setExceptionInfo(obdBox.getExceptionInfo()+" "+"存在端口识别异常");
                        uploadMapper.updateObdBox(obdBox);
                    }
                }
            }
        }finally {
            lock.unlock();
        }
        return AjaxResult.successOBD(msg);
    }

    @Override
    @Transactional
    public AjaxResult uploadInformations(ObdsVO obd) {
        lock.lock();
        try {
            System.out.println(obd.toString());
            //建机箱
            int boxId = 0;
            ObdBox obdBox = new ObdBox();
            if (!"".equals(obd.getBoxCode())) {
                obdBox.setCreateTime(new Date());
                obdBox.setBoxCode(obd.getBoxCode());
                obdBox.setJobNumber(obd.getJobNumber());
                //识别不出是null
                if (obd.getBoxCode() != null) {
                    obdBox.setStatus(0);
                } else {
                    obdBox.setStatus(1);
                    obdBox.setExceptionType(1);
                    obdBox.setExceptionInfo("盒子二维码识别不出");
                }
                uploadMapper.insertObdBox(obdBox);
                boxId = obdBox.getId();
            }

            for (InfoVO info : obd.getPortList()) {
                //建obd
                if (getObdFlag(info.getPortData())) {
                    ObdInfo obdInfo = new ObdInfo();
                    obdInfo.setBoxId(boxId);
                    obdInfo.setStatus(0);
                    uploadMapper.insertObdInfo(obdInfo);
                    int obdId = obdInfo.getId();
                    for (ObdPortInfo port : info.getPortData()) {
                        //存端口
                        port.setObdId(obdId);
                        if (port.getPortSer() != null ) {
                            if (port.getPortCode() != null) {
                                port.setStatus(0);
                            } else {
                                port.setStatus(1);
                            }
                            if (!"".equals(port.getPortCode())) {
                                uploadMapper.insertPort(port);
                            }
                        }

                    }
                    if (uploadMapper.countByPortStatus(obdId) > 0) {
                        obdInfo.setStatus(1);
                        uploadMapper.updateObdInfo(obdInfo);
                        obdBox.setExceptionType(2);
                        obdBox.setExceptionInfo(obdBox.getExceptionInfo()+" "+"存在端口识别异常");
                        uploadMapper.updateObdBox(obdBox);
                    }
                }
            }
        } finally {
            lock.unlock();
        }
        return AjaxResult.successOBD("操作成功", 1);
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
        if(jobNumber!=null && "".equals(jobNumber)){
            List<ObdBoxVO> obdBoxList = uploadMapper.pageByJobNumber(jobNumber);
            for (ObdBoxVO obdBox:obdBoxList){
                obdBox.setStatus(changeStatus(obdBox.getStatus()));
                if("1".equals(obdBox.getExceptionType())){
                    obdBox.setExceptionType("盒子异常");
                }else{
                    obdBox.setExceptionType("obd异常");
                }
            }
            int totalPage = uploadMapper.countByJobNumber(jobNumber);
            if(pageNum>totalPage){
                return null;
            }
            PageInfo<ObdBoxVO> pageInfo = new PageInfo<ObdBoxVO>(obdBoxList);
            return pageInfo;
        }else {
            return  null;
        }

    }

    @Override
    public ObdBoxVO selectObdById(int id) {
        String one = "1";
        ObdBoxVO obdBoxVO = uploadMapper.selectBoxById(Integer.toString(id));
        if(obdBoxVO.getId()>0){
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
        }
        return obdBoxVO;
    }

    @Override
    @Transactional
    public AjaxResult updateObd(ObdBoxVO obdBoxVO) {
        System.out.println(obdBoxVO.toString());
        lock.lock();
        try {
            if(!"".equals(obdBoxVO.getBoxCode()) && obdBoxVO.getBoxCode()!=null && obdBoxVO.getId()>0){
                ObdBox obdBox = new ObdBox();
                obdBox.setId(obdBoxVO.getObdInfoVOList().get(0).getBoxId());
                obdBox.setBoxCode(obdBoxVO.getBoxCode());
                if(obdBoxVO.getBoxCode()!=null){
                    obdBox.setStatus(0);
                }else {
                    obdBox.setStatus(1);
                }
                uploadMapper.updateObdBox(obdBox);
            }
            for (ObdInfoVO obdInfoVO: obdBoxVO.getObdInfoVOList()){
                for (ObdPortInfoVO obdPortInfo : obdInfoVO.getObdPortInfoVOList()){
                    if(obdPortInfo.getPortCode()!=null){
                        if(obdPortInfo.getId()>0){
                            if(!"".equals(obdPortInfo.getPortCode()) && isNumber(obdPortInfo.getPortCode())){
                                ObdPortInfo portInfo = new ObdPortInfo();
                                portInfo.setId(obdPortInfo.getId());
                                portInfo.setPortCode(obdPortInfo.getPortCode());
                                portInfo.setStatus(0);
                                uploadMapper.updateObdPort(portInfo);
                            }
                        }
                        if(obdPortInfo.getId() == 0 && isNumber(obdPortInfo.getPortCode()) ) {
                            ObdPortInfo portInfo = new ObdPortInfo();
                            portInfo.setId(obdPortInfo.getId());
                            portInfo.setPortCode(obdPortInfo.getPortCode());
                            if (!"".equals(obdPortInfo.getPortCode())){
                                portInfo.setStatus(0);
                            }else {
                                portInfo.setStatus(1);
                            }
                            uploadMapper.insertPort(portInfo);
                        }
                    }
                    if(uploadMapper.countByPortStatus(obdPortInfo.getObdId())>0){
                        ObdInfo obdInfo = new ObdInfo();
                        obdInfo.setId(obdPortInfo.getObdId());
                        obdInfo.setStatus(1);
                        uploadMapper.updateObdInfo(obdInfo);
                        ObdBox obdBox1 = new ObdBox();
                        obdBox1.setId((obdBoxVO.getId()));
                        obdBox1.setExceptionType(2);
                        obdBox1.setExceptionInfo("存在端口识别异常");
                        uploadMapper.updateObdBox(obdBox1);
                    }else {
                        ObdInfo obdInfo1 = new ObdInfo();
                        obdInfo1.setId(obdPortInfo.getId());
                        obdInfo1.setStatus(0);
                        uploadMapper.updateObdInfo(obdInfo1);
                        ObdBox obdBox1 = new ObdBox();
                        obdBox1.setId((obdBoxVO.getId()));
                        obdBox1.setExceptionType(0);
                        obdBox1.setExceptionInfo("");
                        uploadMapper.updateObdBox(obdBox1);
                    }
                }
            }
        }finally {
            lock.unlock();
        }
        return AjaxResult.successOBD("操作成功");
    }


    /**
     * 判断端口是否为空
     * @param list 前端全部数据
     * @return ObdBox 实体类
     */
    private boolean getObdFlag(List<ObdPortInfo> list){
        int count = 0;
       for (ObdPortInfo obdPortInfo : list){
            if("".equals(obdPortInfo.getPortCode())){
                count++;
            }
       }
       if (count == portCount){
           return  false;
       }
        return true;
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

    /**
     * 验证是否为18位数字
     * @param string
     * @return boolean
     */
    private boolean isNumber(String string) {
        if(string.length() != 18){
            return false;
        }
        if (string == null){
            return false;
        }
        Pattern pattern = Pattern.compile("^[0-9]*[1-9][0-9]*$");
        return pattern.matcher(string).matches();
    }

}
