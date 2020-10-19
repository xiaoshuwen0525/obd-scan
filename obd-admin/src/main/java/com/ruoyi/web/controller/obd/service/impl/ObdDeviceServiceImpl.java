package com.ruoyi.web.controller.obd.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.controller.obd.mapper.ObdDeviceMapper;
import com.ruoyi.web.controller.obd.service.IObdDeviceService;
import com.ruoyi.web.controller.system.domain.WxUser;
import com.ruoyi.web.controller.upload.domain.ObdBoxVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (wxUser != null){
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
            }else{
                obdBox.setStatus("异常");
                obdBox.setExceptionType("机箱异常");
            }
        }
        return obdBoxVOS;
    }

}
