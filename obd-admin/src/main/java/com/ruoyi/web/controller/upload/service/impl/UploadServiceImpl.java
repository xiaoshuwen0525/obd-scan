package com.ruoyi.web.controller.upload.service.impl;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.controller.upload.domain.*;
import com.ruoyi.web.controller.upload.mapper.UploadMapper;
import com.ruoyi.web.controller.upload.service.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * @author:
 * @date:
 */
@Service
public class UploadServiceImpl implements IUploadService {


  @Autowired
  private UploadMapper uploadMapper;

  //private ReentrantLock lock = new ReentrantLock();

  private final  int portCount = 8;

//  @Override
//  @Transactional
//  public AjaxResult uploadInformation(ObdVO obd) {
//    System.out.println(obd.toString());
//    String msg = "";
//    String undefined = "undefined";
//    //lock.lock();
//    try {
//      //建机箱
//      int boxId = 0;
//      ObdBox obdBox = new ObdBox();
//      if (!"".equals(obd.getBoxCode())) {
//        obdBox.setCreateTime(new Date());
//        obdBox.setBoxCode(obd.getBoxCode());
//        if(StringUtils.isNotEmpty(obd.getJobNumber()) && !undefined.equals(obd.getJobNumber())){
//          obdBox.setJobNumber(obd.getJobNumber());
//        }else {
//          return AjaxResult.error("工号为空或不是正常值："+obd.getJobNumber());
//        }
//        //识别不出是null
//        if (obd.getBoxCode() != null) {
//          obdBox.setStatus(0);
//        } else {
//          obdBox.setBoxCode("");
//          obdBox.setStatus(1);
//          obdBox.setExceptionType(1);
//          obdBox.setExceptionInfo("盒子二维码识别不出");
//        }
//        uploadMapper.insertObdBox(obdBox);
//        boxId = obdBox.getId();
//      }
//      int infoCount = 1;
//      InfoListVO InfoListVO = JSONUtil.toBean("{infoVOList:" + obd.getPortList() + "}", InfoListVO.class);
//      for(InfoVO infoVO:InfoListVO.getInfoVOList()){
//        //建obd
//        ObdInfo obdInfo = new ObdInfo();
//        obdInfo.setBoxId(boxId);
//        obdInfo.setStatus(0);
//        uploadMapper.insertObdInfo(obdInfo);
//        int obdId = obdInfo.getId();
//        for (ObdPortInfo port : infoVO.getPortData()) {
//          ObdPortInfo obdPortInfo = new ObdPortInfo();
//          //存端口
//          obdPortInfo.setObdId(obdId);
//          obdPortInfo.setPortSer(port.getPortSer());
//          if (port.getPortSer() != null ) {
//            if (port.getPortCode() != null) {
//              obdPortInfo.setStatus(0);
//              obdPortInfo.setPortCode(port.getPortCode());
//            } else {
//              obdPortInfo.setStatus(1);
//              obdPortInfo.setPortCode("");
//            }
//            if (!"".equals(port.getPortCode())) {
//              if(port.getPortCode()!=null){
//                if(!isNumber(port.getPortCode()) ){
//                  return AjaxResult.error("第"+infoCount+"个OBD的第"+port.getPortSer()+"端口二维码不对");
//                }
//              }
//              uploadMapper.insertPort(obdPortInfo);
//            }
//          }
//
//        }
//        if (uploadMapper.countByPortStatus(obdId) > 0) {
//          obdInfo.setStatus(1);
//          uploadMapper.updateObdInfo(obdInfo);
//          obdBox.setExceptionType(2);
//          if(StringUtils.isNoneBlank(obdBox.getExceptionInfo())){
//            obdBox.setExceptionInfo(obdBox.getExceptionInfo()+",且存在端口识别异常");
//          }else {
//            obdBox.setExceptionInfo("存在端口识别异常");
//          }
//          uploadMapper.updateObdBox(obdBox);
//        }
//        infoCount++;
//      }
//    }catch (Exception e){
//      e.printStackTrace();
//    }/*finally {
//      lock.unlock();
//    }*/
//    return AjaxResult.successOBD(msg);
//  }

  @Override
  @Transactional
  public AjaxResult uploadInformation(ObdBoxVO obdBoxVO) {
    System.out.println(obdBoxVO.toString());
    String boxCode = null;
    String labelCode = null;
    try {
      if (StringUtils.isNotBlank(obdBoxVO.getJobNumber())) {
        if (obdBoxVO.getBoxCode().startsWith("DG")) {
          labelCode = obdBoxVO.getBoxCode();
        } else if (obdBoxVO.getBoxCode().startsWith("光分纤箱")) {
          boxCode = obdBoxVO.getBoxCode();
        } else {
          return null;
        }
        ObdBox obdBox = new ObdBox();
        obdBox.setBoxCode(boxCode);
        obdBox.setLabelCode(labelCode);
        obdBox.setId(obdBoxVO.getId());
        obdBox.setJobNumber(obdBoxVO.getJobNumber());
        if (obdBoxVO.getBoxCode() != null) {
          obdBox.setStatus(0);
        } else {
          obdBox.setStatus(1);
        }
        obdBox.setCreateTime(new Date());
        uploadMapper.updateBox(obdBox);
      }
      int infoCount = 1;
      System.out.println(obdBoxVO.getObdInfoVOList().toString());
      for (ObdInfoVO obdInfoVO : obdBoxVO.getObdInfoVOList()) {
        for (ObdPortInfoVO obdPortInfo : obdInfoVO.getObdPortInfoVOList()) {
          ObdPortInfoVO obdPort = obdPortInfo;
          if (!"".equals(obdPort.getPortCode()) && obdPort.getSeq() != null) {
            if (obdPort.getSeq() > 0) {
              if (obdPort.getPortCode() != null) {
                if (isNumber(obdPort.getPortCode())) {
                  ObdPortInfo portInfo = new ObdPortInfo();
                  portInfo.setId(obdPort.getSeq());
                  portInfo.setPortCode(obdPort.getPortCode());
                  portInfo.setStatus(0);
                  uploadMapper.updateObdPort(portInfo);
                } else {
                  return AjaxResult.error("第" + infoCount + "个OBD的第" + obdPortInfo.getPortSer() + "端口二维码不对");
                }
              } else {
                ObdPortInfo portInfo = new ObdPortInfo();
                portInfo.setId(obdPort.getSeq());
                portInfo.setPortCode("");
                portInfo.setStatus(1);
                uploadMapper.updateObdPort(portInfo);
              }
            }
            if (obdPort.getSeq() == 0) {
              if (obdPort.getPortCode() != null) {
                if (isNumber(obdPort.getPortCode())) {
                  ObdPortInfo portInfo = new ObdPortInfo();
                  portInfo.setId(obdPort.getSeq());
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
                portInfo.setId(obdPort.getSeq());
                portInfo.setPortCode(obdPort.getPortCode());
                portInfo.setPortSer(obdPort.getPortSer());
                portInfo.setObdId(obdInfoVO.getId());
                portInfo.setStatus(1);
                uploadMapper.insertPort(portInfo);
              }
            }
          }
        }
        if (obdInfoVO.getId() != null) {
          if (uploadMapper.countByPortStatus(obdInfoVO.getId()) > 0) {
            ObdInfo obdInfo = new ObdInfo();
            obdInfo.setId(obdInfoVO.getId());
            obdInfo.setStatus(1);
            uploadMapper.updateObdInfo(obdInfo);
            ObdBox obdBox1 = new ObdBox();
            obdBox1.setId((obdBoxVO.getId()));
            obdBox1.setExceptionType(2);
            if (StringUtils.isNoneBlank(obdBoxVO.getExceptionInfo())) {
              obdBox1.setExceptionInfo(obdBoxVO.getExceptionInfo() + ",且存在端口识别异常");
            } else {
              obdBox1.setExceptionInfo("存在端口识别异常");
            }
            uploadMapper.updateObdBox(obdBox1);
          } else {
            ObdInfo obdInfo1 = new ObdInfo();
            obdInfo1.setId(obdInfoVO.getId());
            obdInfo1.setStatus(0);
            uploadMapper.updateObdInfo(obdInfo1);
            ObdBox obdBox1 = new ObdBox();
            obdBox1.setId((obdBoxVO.getId()));
            if (StringUtils.isNoneBlank(obdBoxVO.getBoxCode())) {
              obdBox1.setExceptionType(0);
              obdBox1.setExceptionInfo("");
            } else {
              obdBox1.setExceptionType(1);
              obdBox1.setExceptionInfo("盒子二维码识别不出");
            }
            uploadMapper.updateObdBox(obdBox1);
          }
        }
        infoCount++;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return AjaxResult.successOBD("操作成功");
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
      if("1".equals(obdBox.getExceptionType())){
        obdBox.setStatus(changeStatus("1"));
        obdBox.setExceptionType("盒子异常");
      }else if ("2".equals(obdBox.getExceptionType())){
        obdBox.setStatus(changeStatus("1"));
        obdBox.setExceptionType("obd异常");
      }else {
        obdBox.setStatus(changeStatus("0"));
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
    try {
      if(StringUtils.isNotBlank(jobNumber)){
        PageHelper.startPage(pageNum,pageSize);
        List<ObdBoxVO> obdBoxList = uploadMapper.pageByJobNumber(jobNumber);
        for (ObdBoxVO obdBox:obdBoxList){
          if("1".equals(obdBox.getStatus())){
            obdBox.setStatus("异常");
          }else {
            obdBox.setStatus("正常");
          }
          if(StringUtils.isNotBlank(obdBox.getLabelCode())){
            obdBox.setBoxCode(obdBox.getLabelCode());
          }
          if("1".equals(obdBox.getExceptionType())){
            obdBox.setExceptionType("盒子异常");
          }else if("2".equals(obdBox.getExceptionType())){
            obdBox.setExceptionType("obd异常");
          }else {
            obdBox.setExceptionType("正常");
          }
        }
        int totalPage = uploadMapper.countByJobNumber(jobNumber);

        if(pageNum>totalPage){
          List<ObdBoxVO> list = new ArrayList();
          PageInfo<ObdBoxVO> pageInfo = new PageInfo<ObdBoxVO>(list);
          return pageInfo;
        }
        PageInfo<ObdBoxVO> pageInfo = new PageInfo<ObdBoxVO>(obdBoxList);
        System.out.println("service selectObdByJobNumber pageInfo :"+pageInfo);
        return pageInfo;
      }
    }catch (Exception e){
      e.printStackTrace();
    }
    return  null;
  }

  @Override
  public ObdBoxVO selectObdById(int id) {
    String one = "1";
    String two = "2";
    try {
      ObdBoxVO obdBoxVO = uploadMapper.selectBoxById(Integer.toString(id));
      if(obdBoxVO.getStatus().equals(one)){
        obdBoxVO.setStatus("异常");
      }else {
        obdBoxVO.setStatus("正常");
      }
      if(obdBoxVO.getId()>0){
        List<ObdInfoVO> obdInfoList = uploadMapper.selectInfoByBoxId(obdBoxVO.getId().toString());
        for (ObdInfoVO obdInfo : obdInfoList) {
//          List<ObdPortInfoVO> obdPortList = getPortList(uploadMapper.selectPortByObdId(obdInfo.getId().toString()));
          List<ObdPortInfoVO> obdPortList = uploadMapper.selectPortByObdId(obdInfo.getId().toString());
          if(obdInfo.getStatus().equals(one)){
            obdInfo.setStatus("异常");
          }else {
            obdInfo.setStatus("正常");
          }
          for (ObdPortInfoVO obdPortInfo : obdPortList) {
            if(obdPortInfo.getStatus().equals(one)){
              obdPortInfo.setStatus("异常");
            }else {
              obdPortInfo.setStatus("正常");
            }
          }
          obdInfo.setObdPortInfoVOList(obdPortList);
        }
        obdBoxVO.setStatus(changeStatus(obdBoxVO.getStatus()));
        obdBoxVO.setObdInfoVOList(obdInfoList);
        if (one.equals(obdBoxVO.getExceptionType())) {
          obdBoxVO.setExceptionType("盒子异常");
        } else if (two.equals(obdBoxVO.getExceptionType())) {
          obdBoxVO.setExceptionType("obd异常");
        }else {
          obdBoxVO.setExceptionType("正常");
        }
      }
      return obdBoxVO;
    }catch (Exception e){
      e.printStackTrace();
    }
    return null;
  }

  @Override
  @Transactional
  public AjaxResult updateObd(ObdBoxVO obdBoxVO) {
    System.out.println(obdBoxVO.toString());
    //lock.lock();
    try {
      if(!"".equals(obdBoxVO.getBoxCode()) && obdBoxVO.getBoxCode()!=null && obdBoxVO.getId()>0){
        ObdBox obdBox = new ObdBox();
        obdBox.setId(obdBoxVO.getId());
        if(obdBoxVO.getBoxCode()!=null){
          obdBox.setStatus(0);
        }else {
          obdBox.setStatus(1);
        }
        obdBox.setCreateTime(new Date());
        uploadMapper.updateObdBox(obdBox);
      }
      int infoCount = 1;
      for (ObdInfoVO obdInfoVO: obdBoxVO.getObdInfoVOList()){
        for (ObdPortInfoVO obdPortInfo : obdInfoVO.getObdPortInfoVOList()){
          ObdPortInfoVO obdPort = obdPortInfo;
          if(!"".equals(obdPort.getPortCode()) && obdPort.getId()!=null){
              if(obdPort.getId()>0){
                if(obdPort.getPortCode()!=null){
                  if(isNumber(obdPort.getPortCode())){
                    ObdPortInfo portInfo = new ObdPortInfo();
                    portInfo.setId(obdPort.getId());
                    portInfo.setPortCode(obdPort.getPortCode());
                    portInfo.setStatus(0);
                    uploadMapper.updateObdPort(portInfo);
                  }else {
                    return AjaxResult.error("第"+infoCount+"个OBD的第"+obdPortInfo.getPortSer()+"端口二维码不对");
                  }
                }else {
                  ObdPortInfo portInfo = new ObdPortInfo();
                  portInfo.setId(obdPort.getId());
                  portInfo.setPortCode("");
                  portInfo.setStatus(1);
                  uploadMapper.updateObdPort(portInfo);
                }
              }
              if(obdPort.getId() == 0) {
                if(obdPort.getPortCode()!=null){
                  if(isNumber(obdPort.getPortCode())){
                    ObdPortInfo portInfo = new ObdPortInfo();
                    portInfo.setId(obdPort.getId());
                    portInfo.setPortCode(obdPort.getPortCode());
                    portInfo.setPortSer(obdPort.getPortSer());
                    portInfo.setObdId(obdInfoVO.getId());
                    portInfo.setStatus(0);
                    uploadMapper.insertPort(portInfo);
                  }else {
                    return AjaxResult.error("第"+infoCount+"个OBD的第"+obdPortInfo.getPortSer()+"端口二维码不对");
                  }
                }else {
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
          if(obdPort.getObdId()!=null){
            if(uploadMapper.countByPortStatus(obdPort.getObdId())>0){
              ObdInfo obdInfo = new ObdInfo();
              obdInfo.setId(obdPort.getObdId());
              obdInfo.setStatus(1);
              uploadMapper.updateObdInfo(obdInfo);
              ObdBox obdBox1 = new ObdBox();
              obdBox1.setId((obdBoxVO.getId()));
              obdBox1.setExceptionType(2);
              if(StringUtils.isNoneBlank(obdBoxVO.getExceptionInfo())){
                obdBox1.setExceptionInfo(obdBoxVO.getExceptionInfo()+",且存在端口识别异常");
              }else {
                obdBox1.setExceptionInfo("存在端口识别异常");
              }
              uploadMapper.updateObdBox(obdBox1);
            }else {
              ObdInfo obdInfo1 = new ObdInfo();
              obdInfo1.setId(obdPort.getId());
              obdInfo1.setStatus(0);
              uploadMapper.updateObdInfo(obdInfo1);
              ObdBox obdBox1 = new ObdBox();
              obdBox1.setId((obdBoxVO.getId()));
              if(StringUtils.isNoneBlank(obdBoxVO.getBoxCode())){
                obdBox1.setExceptionType(0);
                obdBox1.setExceptionInfo("");
              }else {
                obdBox1.setExceptionType(1);
                obdBox1.setExceptionInfo("盒子二维码识别不出");
              }
              uploadMapper.updateObdBox(obdBox1);
            }
          }
        }
        infoCount++;
      }
    }catch (Exception e){
      e.printStackTrace();
    }/*finally {
      lock.unlock();
    }*/
    return AjaxResult.successOBD("操作成功");
  }

  /**
   * 获得端口列表
   * @param list 前端全部数据
   * @return ObdBox 实体类
   */
  private List<ObdPortInfoVO> getPortList (List<ObdPortInfoVO> list){
    List<ObdPortInfoVO> obdPortInfos = new ArrayList<ObdPortInfoVO>();
    for (int i = 0; i < 8 ; i++){
      ObdPortInfoVO obdPortInfo = new ObdPortInfoVO();
      int poreSer = i+1;
      obdPortInfo.setPortSer(poreSer);
      obdPortInfo.setStatus("0");
      obdPortInfo.setId(0);
      obdPortInfo.setPortCode("");
      for (ObdPortInfoVO obdPort:list){
        if(obdPort.getPortSer()!=null){
          if(obdPort.getPortSer() == poreSer){
            obdPortInfo.setId(obdPort.getId());
            obdPortInfo.setPortCode(obdPort.getPortCode());
            obdPortInfo.setStatus(obdPort.getStatus());
            obdPortInfo.setObdId(obdPort.getObdId());
          }
        }
      }
      obdPortInfos.add(obdPortInfo);
    }
    return obdPortInfos;
  }


  /**
   * 将status改文字
   * @param status 值
   * @return 异常状态文字
   */
  private String changeStatus(String status){
    String zero = "0";
    if(status!=null && !"".equals(status)){
      if(status.equals(zero)){
        return "正常";
      }else {
        return "异常";
      }
    }
    return status;
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
    int start = 17;
    int end  = 18;
    String regex = "^[0-9]*[0-9][0-9]*$";
    if(string!=null && !"".equals(string)){
      if(string.length() != end && string.length() != start){
        return false;
      }
    }
    Pattern pattern = Pattern.compile(regex);
    return pattern.matcher(string).matches();
  }

}