package com.ruoyi.web.controller.obd.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.controller.obd.mapper.ObdDeviceMapper;
import com.ruoyi.web.controller.obd.service.IObdDeviceService;
import com.ruoyi.web.controller.system.domain.WxUser;
import com.ruoyi.web.controller.upload.domain.ObdBoxVO;
import com.ruoyi.web.controller.upload.domain.ObdInfoVO;
import com.ruoyi.web.controller.upload.domain.ObdPortInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author: 曾志伟
 * @date: 2020-10-17 12:13
 */
@Service
public class ObdDeviceServiceImpl implements IObdDeviceService {

    @Autowired
    private ObdDeviceMapper obdDeviceMapper;

    @Override
    public List<ObdBoxVO> boxSearch(String boxCode) {
        if (StringUtils.isNotBlank(boxCode)) {
            return obdDeviceMapper.boxSearch(boxCode);
        }
        return null;

    }

    @Override
    public List<ObdBoxVO> jobNumberSearch(String jobNumber) {
        if (StringUtils.isNotBlank(jobNumber)) {
            return obdDeviceMapper.jobNumberSearch(jobNumber);
        }
        return null;
    }

    @Override
    public List<ObdBoxVO> phoneSearch(String phone) {
        if (StringUtils.isNotBlank(phone)) {
            return obdDeviceMapper.phoneSearch(phone);
        }
        return null;
    }

    @Override
    public List<ObdBoxVO> allSearch() {
        return obdDeviceMapper.allSearch();
    }

    @Override
    public int isPhoneNumberExist(String newPhone) {
        WxUser wxUser = obdDeviceMapper.isPhoneNumberExist(newPhone);
        if (wxUser != null) {
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional
    public int bindPhone(String jobNumber, String phone, String newPhone) {
        int i = 0;
        try {
            i = obdDeviceMapper.bindPhone(jobNumber, phone, newPhone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    @Transactional
    public String unBindPhone(String id) {
        int i = 0;
        try {
            i = obdDeviceMapper.unBindPhone(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i != 0 ? "解绑成功" : "解绑失败";
    }

    @Override
    public List<WxUser> queryWechatInfo(String jobNumber, String phone, String id) {
        WxUser wxUser = new WxUser();
        if (StringUtils.isNotBlank(jobNumber)) {
            wxUser.setJobNumber(jobNumber);
        }
        if (StringUtils.isNotBlank(phone)) {
            wxUser.setPhone(phone);
        }
        if (StringUtils.isNotBlank(id)) {
            wxUser.setId(Integer.parseInt(id));
        }
        List<WxUser> wxUsers = null;
        try {
            wxUsers = obdDeviceMapper.queryWechatInfo(wxUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wxUsers;
    }

    @Override
    public List<ObdBoxVO> searchByCondition(String jobNumber, String phone, String boxCode, String status) {
        List<ObdBoxVO> obdBoxVOS = obdDeviceMapper.searchByCondition(jobNumber, phone, boxCode, status);
        if (obdBoxVOS == null) {
            return null;
        }
        for (ObdBoxVO obdBox : obdBoxVOS) {
            if ("0".equals(obdBox.getStatus())) {
                if ("0".equals(obdBox.getExceptionType())) {
                    obdBox.setStatus("正常");
                    obdBox.setExceptionType("正常");
                } else if ("1".equals(obdBox.getExceptionType())) {
                    obdBox.setStatus("异常");
                    obdBox.setExceptionType("OBD异常");
                } else if ("2".equals(obdBox.getExceptionType())) {
                    obdBox.setStatus("异常");
                    obdBox.setExceptionType("端口异常");
                }
            } else {
                obdBox.setStatus("异常");
                obdBox.setExceptionType("机箱异常");
            }
        }
        return obdBoxVOS;
    }

    @Override
    public List<ObdInfoVO> infoByBoxId(String boxId) {
        List<ObdInfoVO> list = obdDeviceMapper.selectInfoByBoxId(boxId);
        for (ObdInfoVO obdInfo : list) {
            obdInfo.setStatus(changeStatus(obdInfo.getStatus()));
        }
        return list;
    }

    @Override
    public ObdBoxVO selectAllInfoByCode(String code) {
        String boxCode = null;
        String labelCode = null;
        if (code.startsWith("DG")) {
            labelCode = code;
        } else if (code.startsWith("光分纤箱")) {
            boxCode = code;
        } else {
            return null;
        }
        ObdBoxVO obdBoxVO = new ObdBoxVO();
        List<ObdInfoVO> obdInfoVOS = new ArrayList<>();
        List<ObdPortInfoVO> obdPortInfoVOS;
        obdPortInfoVOS = obdDeviceMapper.selectAllInfoByCode(boxCode, labelCode);
        if (obdPortInfoVOS == null || obdPortInfoVOS.size()==0){
            return null;
        }
        HashSet<String> obd = new HashSet<>();
        for (ObdPortInfoVO obdPortInfoVO : obdPortInfoVOS) {
            if (obdPortInfoVO.getId()!=0){
                obdBoxVO.setId(obdPortInfoVO.getId());
            }
            obd.add(obdPortInfoVO.getObdId().toString());
        }
        for (String s : obd) {
            List<ObdPortInfoVO> obdPortInfoVOS1 = new ArrayList<>();
            for (ObdPortInfoVO obdPortInfoVO : obdPortInfoVOS) {
                if (s.equals(obdPortInfoVO.getObdId().toString())){
                    ObdPortInfoVO obdPortInfoVO1 = new ObdPortInfoVO();
                    obdPortInfoVO1.setPortSer(obdPortInfoVO.getPortSer());
                    obdPortInfoVO1.setPortCode(obdPortInfoVO.getPortCode());
                    obdPortInfoVOS1.add(obdPortInfoVO1);
                }
            }
            ObdInfoVO obdInfoVO = new ObdInfoVO();
            obdInfoVO.setId(Integer.parseInt(s));
            obdInfoVO.setObdPortInfoVOList(obdPortInfoVOS1);
            obdInfoVOS.add(obdInfoVO);
        }
        obdBoxVO.setObdInfoVOList(obdInfoVOS);
        return obdBoxVO;
    }

    @Override
    public List<ObdPortInfoVO> portByObdId(String obdId) {
        List<ObdPortInfoVO> list = obdDeviceMapper.selectPortByObdId(obdId);
        for (ObdPortInfoVO port : list) {
            port.setStatus(changeStatus(port.getStatus()));
        }
        return list;
    }

    @Override
    public List<ObdBoxVO> selectBoxByJobNumber(String jobNumber) {
        List<ObdBoxVO> list = obdDeviceMapper.selectBoxByJobNumber(jobNumber);
        for (ObdBoxVO obdBox : list) {
            if ("1".equals(obdBox.getExceptionType())) {
                obdBox.setStatus(changeStatus("1"));
                obdBox.setExceptionType("盒子异常");
            } else if ("2".equals(obdBox.getExceptionType())) {
                obdBox.setStatus(changeStatus("1"));
                obdBox.setExceptionType("obd异常");
            } else {
                obdBox.setStatus(changeStatus("0"));
                obdBox.setExceptionType("正常");
            }
        }
        return list;
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
}
