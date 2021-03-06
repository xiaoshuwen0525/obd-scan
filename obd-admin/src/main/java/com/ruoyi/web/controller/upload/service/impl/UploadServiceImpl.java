package com.ruoyi.web.controller.upload.service.impl;


import cn.hutool.core.io.FileUtil;

import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.controller.upload.domain.*;
import com.ruoyi.web.controller.upload.mapper.ObdInfoHistoryMapper;
import com.ruoyi.web.controller.upload.mapper.ObdPortHistoryMapper;
import com.ruoyi.web.controller.upload.mapper.UploadMapper;
import com.ruoyi.web.controller.upload.service.IUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

/**
 * @author:
 * @date:
 */
@Service
public class UploadServiceImpl implements IUploadService {

    @Value("${ruoyi.uploadfile}")
    private String uploadfile;

    @Autowired
    private UploadMapper uploadMapper;

    @Autowired
    private ObdInfoHistoryMapper obdInfoHistoryMapper;

    @Autowired
    private ObdPortHistoryMapper obdPortHistoryMapper;

    private static final Logger log = LoggerFactory.getLogger(UploadServiceImpl.class);
    private ReentrantLock lock = new ReentrantLock();


    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult uploadInformation(ObdBoxVO obdBoxVO) {
        List<ObdPicture> obdPictureList;
        String boxCode = null;
        String labelCode = null;
        try {
            if (StringUtils.isNotBlank(obdBoxVO.getJobNumber())) {
                //拼装机箱数据
                ObdBoxVO boxVO = uploadMapper.selectPcObdByCode(obdBoxVO.getBoxUniqueId());
                ObdBox obdBox = new ObdBox();
                obdBox.setBoxCode(boxVO.getBoxCode());
                obdBox.setLabelCode(boxVO.getLabelCode());
                obdBox.setId(obdBoxVO.getId());
                obdBox.setJobNumber(obdBoxVO.getJobNumber());
                obdBox.setBoxUniqueId(boxVO.getBoxUniqueId());
                boxCode = boxVO.getBoxCode();
                labelCode = boxVO.getLabelCode();
                obdPictureList = uploadMapper.selectObdPicture(obdBox);
                if(obdPictureList.size()!=0){
                    for (ObdPicture picture:obdPictureList){
                        obdBox.setImgUrl(picture.getImgUrl());
                    }
                }else {
                    return AjaxResult.error("请重新上传整改图片");
                }

                if (obdBoxVO.getBoxCode() == null && obdBox.getLabelCode() == null) {
                    obdBox.setBoxCode("");
                    obdBox.setStatus(1);
                    obdBox.setExceptionType(1);
                    obdBox.setExceptionInfo("盒子二维码识别不出");
                } else {
                    obdBox.setStatus(0);

                }

                obdBox.setCreateTime(new Date());

                if(!isNullPort(obdBoxVO.getObdInfoVOList())){

                    return AjaxResult.error("请至少扫描一个obd端口二维码");
                }

                //查看是否已经存在，存在则把数据迁移到另三个表上上，并删除原表数据
                int lockFlag = 0;
                lock.lock();
                try {
                    List<ObdBox> obdBoxList = uploadMapper.selectObdBox(obdBox);
                    if (obdBoxList.size() > 0) {
                        for (ObdBox box : obdBoxList) {
                            List<ObdInfoHistory> obdInfoHistories = obdInfoHistoryMapper.selectByBoxId(box.getId());
                            for (ObdInfoHistory obdInfoHistory:obdInfoHistories){
                                List<ObdPortHistory> obdPortHistories = obdPortHistoryMapper.selectByObdId(obdInfoHistory.getId());
                                if(StringUtils.isNotEmpty(obdPortHistories)){
                                    obdPortHistoryMapper.insertBatch(obdPortHistories);
                                    obdPortHistoryMapper.deleteByBatch(obdPortHistories);
                                }
                            }
                            if(StringUtils.isNotEmpty(obdInfoHistories)){
                                obdInfoHistoryMapper.insertBatch(obdInfoHistories);
                                obdInfoHistoryMapper.deleteByBatch(obdInfoHistories);

                            }
                            uploadMapper.insertBoxHistory(box);
                            uploadMapper.deleteByObdBox(box);
                        }
                    }
                    uploadMapper.insertObdBox(obdBox);
                    obdBoxVO.setId(obdBox.getId());
                }catch (Exception e ){
                    e.printStackTrace();
                    lockFlag = -1;
                }finally {
                    lock.unlock();
                }
                if(lockFlag == -1){
                    throw new RuntimeException();
                }
            }
            //插入obd和端口
            int infoCount = 1;
            for (ObdInfoVO obdInfoVO : obdBoxVO.getObdInfoVOList()) {
                ObdInfo info = new ObdInfo();
                info.setBoxId(obdBoxVO.getId());
                info.setStatus(0);
                info.setPortCount(obdInfoVO.getPortCount());
                info.setBoxBelong(obdInfoVO.getBoxBelong());
                info.setObdName(obdInfoVO.getObdName());
                info.setBoxUniqueId(obdInfoVO.getBoxUniqueId());
                info.setObdUniqueId(obdInfoVO.getObdUniqueId());
                uploadMapper.insertObdInfo(info);
                for (ObdPortInfoVO obdPortInfo : obdInfoVO.getObdPortInfoVOList()) {
                    if (!"".equals(obdPortInfo.getPortCode())) {
                        //判断数据是否正确,错误准确提示那个端口数据有误，没有报错事务不生效，手动删除之前数据
                        if (!isNumber(obdPortInfo.getPortCode())) {
                            uploadMapper.deleteByBox(obdBoxVO);
                            List<ObdInfoHistory> obdInfoHistories = obdInfoHistoryMapper.selectByBoxId(obdBoxVO.getId());
                            for (ObdInfoHistory obdInfoHistory:obdInfoHistories){
                                List<ObdPortHistory> obdPortHistories = obdPortHistoryMapper.selectByObdId(obdInfoHistory.getId());
                                if(StringUtils.isNotEmpty(obdPortHistories)){
                                    obdPortHistoryMapper.deleteByBatch(obdPortHistories);
                                }
                            }
                            if(StringUtils.isNotEmpty(obdInfoHistories)){
                                obdInfoHistoryMapper.deleteByBatch(obdInfoHistories);
                            }
                            return AjaxResult.error("第" + infoCount + "个OBD的第" + obdPortInfo.getPortSer() + "端口二维码不对");
                        }
                        if (obdPortInfo.getPortCode() != null) {
                            ObdPortInfo portInfo = new ObdPortInfo();
                            portInfo.setPortSer(obdPortInfo.getPortSer());
                            portInfo.setId(obdPortInfo.getSeq());
                            portInfo.setObdId(info.getId());
                            portInfo.setPortCode(obdPortInfo.getPortCode());
                            portInfo.setStatus(0);
                            uploadMapper.insertPort(portInfo);
                        } else {
                            ObdPortInfo portInfo = new ObdPortInfo();
                            portInfo.setPortSer(obdPortInfo.getPortSer());
                            portInfo.setId(obdPortInfo.getSeq());
                            portInfo.setPortCode("");
                            portInfo.setStatus(1);
                            portInfo.setObdId(info.getId());
                            uploadMapper.insertPort(portInfo);
                        }
                    }
                }
                infoCount++;
            }
            ObdBox obdBox1 = new ObdBox();
            obdBox1.setBoxCode(boxCode);
            obdBox1.setLabelCode(labelCode);
            //清空无效图片数据
            obdPictureList = uploadMapper.selectObdPicture(obdBox1);
            if(obdPictureList.size()!=0){
                for (ObdPicture picture:obdPictureList){
                    uploadMapper.deleteByPicture(picture);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return AjaxResult.successOBD("操作成功");
    }

    @Override
    public int uploadObdBox(ObdBox obdBox) {
        if (obdBox.getBoxCode() != null && !"".equals(obdBox.getBoxCode())) {
            obdBox.setStatus(0);
        } else {
            obdBox.setStatus(1);
            obdBox.setExceptionType(1);
            obdBox.setExceptionInfo("无法识别");
        }
        return uploadMapper.insertObdBox(obdBox);
    }

    @Override
    public int uploadObdPicture(ObdPicture obdPicture, MultipartFile multipartFile, String boxCode) {
        String s = uploadPicture(obdPicture.getJobNumber(), multipartFile);
        int i = 0;
        if(StringUtils.isNotEmpty(s)){
            try {
                obdPicture.setImgUrl(s);
                i = uploadMapper.insertPicture(obdPicture);
            }catch (Exception e){
                e.printStackTrace();
                return -1;
            }
        }else {
            i = -1;
        }
        return i;
    }

    /**
     * 上传obd信息
     *
     * @param obdInfo obd信息
     * @return int
     */
    @Override
    public int uploadObdInfo(ObdInfo obdInfo) {
        return uploadMapper.insertObdInfo(obdInfo);
    }

    /**
     * 上传obd的端口
     *
     * @param obdPortInfo obd端口信息
     * @return int
     */
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
        return list;
    }

    @Override
    public List<ObdInfoVO> infoByBoxId(String boxId) {
        List<ObdInfoVO> list = uploadMapper.selectInfoByBoxId(boxId);
        for (ObdInfoVO obdInfo : list) {
            obdInfo.setStatus(changeStatus(obdInfo.getStatus()));
        }
        return list;
    }

    @Override
    public List<ObdPortInfoVO> portByObdId(String obdId) {
        List<ObdPortInfoVO> list = uploadMapper.selectPortByObdId(obdId);
        for (ObdPortInfoVO port : list) {
            port.setStatus(changeStatus(port.getStatus()));
        }
        return list;
    }

    @Override
    public ObdBoxVO selectBoxById(String id) {
        String one = "1";
        ObdBoxVO obdBox = uploadMapper.selectBoxById(id);
        obdBox.setStatus(changeStatus(obdBox.getStatus()));
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
        try {
            if (StringUtils.isNotBlank(jobNumber)) {
                PageHelper.startPage(pageNum, pageSize);
                List<ObdBoxVO> obdBoxList = uploadMapper.pageByJobNumber(jobNumber);
                for (ObdBoxVO obdBox : obdBoxList) {
                    if("0".equals(obdBox.getCheckState())){
                        obdBox.setCheckState("不合格");
                    }else  if("1".equals(obdBox.getCheckState())){
                        obdBox.setCheckState("合格");
                    }else {
                        obdBox.setCheckState("无");
                    }

                }
                int totalPage = PageUtil.totalPage(uploadMapper.countByJobNumber(jobNumber),pageSize);

                if (pageNum > totalPage) {
                    List<ObdBoxVO> list = new ArrayList();
                    PageInfo<ObdBoxVO> pageInfo = new PageInfo<ObdBoxVO>(list);
                    return pageInfo;
                }
                PageInfo<ObdBoxVO> pageInfo = new PageInfo<ObdBoxVO>(obdBoxList);
                return pageInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ObdBoxVO selectObdById(int id) {
        try {
            ObdBoxVO obdBoxVO = uploadMapper.selectBoxById(Integer.toString(id));
            if (StringUtils.isEmpty(obdBoxVO.getBoxCode())) {
                obdBoxVO.setBoxCode(obdBoxVO.getLabelCode());
            }
            if (obdBoxVO.getId() > 0) {
                List<ObdInfoVO> obdInfoList = uploadMapper.selectInfoByBoxId(obdBoxVO.getId().toString());
                for (ObdInfoVO obdInfo : obdInfoList) {
                    List<ObdPortInfoVO> obdPortList = getPortList(uploadMapper.selectPortByObdId(obdInfo.getId().toString()), obdInfo.getPortCount());
                    obdInfo.setObdPortInfoVOList(obdPortList);
                }
                obdBoxVO.setStatus(changeStatus(obdBoxVO.getStatus()));
                obdBoxVO.setObdInfoVOList(obdInfoList);
            }
            return obdBoxVO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新obd
     *
     * @param obdBoxVO obd盒签证官
     * @return {@link AjaxResult}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult updateObd(ObdBoxVO obdBoxVO) {
        try {
            List<ObdPicture> obdPictureList = new ArrayList<>();
            //更新箱子
            if (!"".equals(obdBoxVO.getBoxCode()) && obdBoxVO.getBoxCode() != null && obdBoxVO.getId() > 0) {
                ObdBox obdBox = new ObdBox();
                obdBox.setId(obdBoxVO.getId());
                if (obdBoxVO.getBoxCode() != null) {
                    obdBox.setStatus(0);
                } else {
                    obdBox.setStatus(1);
                }
                obdBox.setBoxUniqueId(obdBoxVO.getBoxUniqueId());
                obdBox.setBoxCode(obdBoxVO.getBoxCode());
                obdBox.setLabelCode(obdBoxVO.getLabelCode());
                obdBox.setCreateTime(new Date());
                obdPictureList = uploadMapper.selectObdPicture(obdBox);
                if(obdPictureList.size()!=0){
                    for (ObdPicture picture:obdPictureList){
                        obdBox.setImgUrl(picture.getImgUrl());
                    }
                }
                uploadMapper.updateObdBox(obdBox);
            }
            int infoCount = 1;
            for (ObdInfoVO obdInfoVO : obdBoxVO.getObdInfoVOList()) {
                ObdInfo info = new ObdInfo();
                info.setId(obdInfoVO.getId());
                info.setBoxBelong(obdBoxVO.getBoxName());
                info.setBoxUniqueId(obdBoxVO.getBoxUniqueId());
                uploadMapper.updateObdInfo(info);
                //修改端口
                for (ObdPortInfoVO obdPortInfo : obdInfoVO.getObdPortInfoVOList()) {
                    ObdPortInfoVO obdPort = obdPortInfo;
                    //存在id 且 串码有值
                    if (!"".equals(obdPort.getPortCode()) && obdPort.getId() != null) {
                        //id 大于0 即存在，更新操作
                        if (obdPort.getId() > 0) {
                            if (obdPort.getPortCode() != null) {
                                if (isNumber(obdPort.getPortCode())) {
                                    ObdPortInfo portInfo = new ObdPortInfo();
                                    portInfo.setId(obdPort.getId());
                                    portInfo.setPortCode(obdPort.getPortCode());
                                    portInfo.setStatus(0);
                                    uploadMapper.updateObdPort(portInfo);
                                } else {
                                    return AjaxResult.error("第" + infoCount + "个OBD的第" + obdPortInfo.getPortSer() + "端口二维码不对");
                                }
                            } else {
                                ObdPortInfo portInfo = new ObdPortInfo();
                                portInfo.setId(obdPort.getId());
                                portInfo.setPortCode("");
                                portInfo.setStatus(1);
                                uploadMapper.updateObdPort(portInfo);
                            }
                        }
                        //id 等于0 即原来没有此数据，需要插入
                        if (obdPort.getId() == 0) {
                            if (obdPort.getPortCode() != null) {
                                if (isNumber(obdPort.getPortCode())) {
                                    ObdPortInfo portInfo = new ObdPortInfo();
                                    portInfo.setId(obdPort.getId());
                                    portInfo.setPortCode(obdPort.getPortCode());
                                    portInfo.setPortSer(obdPort.getPortSer());
                                    portInfo.setObdId(obdInfoVO.getId());
                                    portInfo.setStatus(0);
                                    uploadMapper.insertPort(portInfo);
                                } else {
                                    return AjaxResult.error("第" + infoCount + "个OBD的第" + obdPortInfo.getPortSer() + "端口二维码不对");
                                }
                            } else {
                                ObdPortInfo portInfo = new ObdPortInfo();
                                portInfo.setId(obdPort.getId());
                                portInfo.setPortCode(obdPort.getPortCode());
                                portInfo.setPortSer(obdPort.getPortSer());
                                portInfo.setObdId(obdInfoVO.getId());
                                portInfo.setStatus(1);
                                uploadMapper.insertPort(portInfo);
                            }
                        }
                    }
                }
                infoCount++;
            }
            //清空无效图片数据
            if(obdPictureList.size()!=0){
                for (ObdPicture picture:obdPictureList){
                    uploadMapper.deleteByPicture(picture);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.successOBD("操作成功");
    }

    @Override
    public ObdBoxVO selectPcObdByCode(String boxCode) {
        try {
            return uploadMapper.selectPcObdByCode(boxCode);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过端口id删除
     *
     * @param id id
     * @return int
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteByPortId(Integer id) {
        int i = 0;
        lock.lock();
        try {
             i = uploadMapper.deleteByPortId(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }finally {
            lock.unlock();
        }
        return i;
    }


    /**
     * 获得端口列表
     *
     * @param list 前端全部数据
     * @return ObdBox 实体类
     */
    private List<ObdPortInfoVO> getPortList(List<ObdPortInfoVO> list, int portCount) {
        List<ObdPortInfoVO> obdPortInfos = new ArrayList<ObdPortInfoVO>();
        for (int i = 0; i < portCount; i++) {
            ObdPortInfoVO obdPortInfo = new ObdPortInfoVO();
            int poreSer = i + 1;
            obdPortInfo.setPortSer(poreSer);
            obdPortInfo.setStatus("0");
            obdPortInfo.setId(0);
            obdPortInfo.setPortCode("");
            for (ObdPortInfoVO obdPort : list) {
                if (obdPort.getPortSer() == poreSer) {
                    obdPortInfo.setId(obdPort.getId());
                    obdPortInfo.setPortCode(obdPort.getPortCode());
                    obdPortInfo.setStatus(obdPort.getStatus());
                    obdPortInfo.setObdId(obdPort.getObdId());
                }
            }
            obdPortInfos.add(obdPortInfo);
        }
        return obdPortInfos;
    }


    /**
     * 将status改文字
     *
     * @param status 值
     * @return 异常状态文字
     */
    private String changeStatus(String status) {
        String zero = "0";
        if (status != null && !"".equals(status)) {
            if (status.equals(zero)) {
                return "正常";
            } else {
                return "异常";
            }
        }
        return status;
    }

    /**
     * 获得 图片路径
     *
     * @param jobNumber     工号
     * @param multipartFile 图片文件
     * @return path 图片路径
     */
    private String uploadPicture(String jobNumber, MultipartFile multipartFile) {
        log.info("上传图片入参工号"+jobNumber+",图片"+multipartFile+",图片转换成名称"+multipartFile.getOriginalFilename());
        String path = "";
        String uploadPaths = uploadfile.replace("/", "\\");
        String jobNumbers = StrUtil.trim(jobNumber);
        String folder = uploadPaths + File.separator + jobNumbers + File.separator;
        if (!FileUtil.exist(folder)) {
            FileUtil.mkdir(folder);
        }
        try {
            String fileName = multipartFile.getOriginalFilename();
            File upload = FileUtil.writeBytes(multipartFile.getBytes(), folder + fileName);
            if (upload.length() > 0) {
                path = "static" + File.separator + "obdImg" + File.separator + jobNumbers + File.separator+ fileName;
            }
            log.info("图片上传成功"+path);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("图片上传失败");
            return null;
        }
        return path.replace("\\", "/");
    }


    /**
     * 验证是否为18位数字
     *
     * @param string
     * @return boolean
     */
    private boolean isNumber(String string) {
        int start = 17;
        int end = 18;
        String regex = "^[0-9]*[0-9][0-9]*$";
        if (string != null && !"".equals(string)) {
            if (string.length() != end && string.length() != start) {
                return false;
            }
        }
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(string).matches();
    }


    /** 判断是否为空端口
     * @param list
     * @return boolean
     */
    private  boolean isNullPort(List<ObdInfoVO> list){
        boolean flag = false;
        for (ObdInfoVO obdInfo:list){
            for (ObdPortInfoVO portInfo:obdInfo.getObdPortInfoVOList()){
                if(StringUtils.isNotEmpty(portInfo.getPortCode())){
                    flag = true;
                }
            }
        }
        return flag;
    }

}