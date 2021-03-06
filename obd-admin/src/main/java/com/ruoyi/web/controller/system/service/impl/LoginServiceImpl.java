package com.ruoyi.web.controller.system.service.impl;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.controller.employee.domain.EmployeeUser;
import com.ruoyi.web.controller.employee.domain.ImportUser;
import com.ruoyi.web.controller.system.controller.LoginController;
import com.ruoyi.web.controller.system.domain.PhoneCode;
import com.ruoyi.web.controller.system.domain.WxUser;
import com.ruoyi.web.controller.system.mapper.LoginMapper;
import com.ruoyi.web.controller.system.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 登陆 服务层实现类
 *
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    /**
     * 登陆
     * @param openId
     * @return
     */
    @Override
    public AjaxResult selectOpenId(String openId ){
        WxUser wxUser = loginMapper.selectOpenId(openId);
        if(wxUser!=null && wxUser.getWxOpenId().equals(openId) && StringUtils.isBlank(wxUser.getPhone())) {
            //return AjaxResult.warn("此工号手机号已解绑，请联系管理员重新绑定手机号");
            return AjaxResult.success("104","此工号手机号已解绑，请联系管理员重新绑定手机号",openId);
        }else if(wxUser!=null && wxUser.getWxOpenId().equals(openId) && wxUser.getPhone() != null ){
            return AjaxResult.successOBD(wxUser);
        }else {
            return AjaxResult.success("104","未绑定",openId);
        }
    }

    /**
     * 绑定
     * @param jobNumber
     * @param phone
     * @param wxOpenId
     * @param authCode
     * @return
     */
    @Override
    @Transactional
    public AjaxResult insertUser(String jobNumber,String phone,String wxOpenId,Integer authCode) throws ParseException {

        WxUser wxUser2 = loginMapper.selectJobNumber(jobNumber);
        //解绑后 ，重新绑定更新 openid和手机号
        if(wxUser2!=null && wxUser2.getJobNumber().equals(jobNumber) && StringUtils.isBlank(wxUser2.getPhone())){
            WxUser wxUser = new WxUser();
            wxUser.setJobNumber(jobNumber);
            wxUser.setPhone(phone);
            wxUser.setWxOpenId(wxOpenId);
            loginMapper.updateBinding(wxUser);
            return AjaxResult.successOBD(wxUser);
        }

        EmployeeUser employeeUser = loginMapper.selectEmployee(jobNumber, phone);
        if(employeeUser==null){
            return AjaxResult.success("104","资料库没有此工号或手机号",null);
        }

        //判断手机号是否存在
        WxUser wxUser1 = loginMapper.selectPhone(phone);
        if(wxUser1!=null && wxUser1.getPhone().equals(phone)){
            return AjaxResult.success("104","手机号已绑定,不能重复绑定",null);
        }

        //判断工号是否存在
        if(wxUser2!=null && wxUser2.getJobNumber().equals(jobNumber)){
            return AjaxResult.success("104","此工号已存在",null);
        }

        PhoneCode phoneCode = loginMapper.selectAuthCode(phone);
        //判断验证码是否超时  超过5分钟
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        Date createTime = phoneCode.getCreateTime();
        String startTimeStr = sdf.format(createTime.getTime());
        String endTimeStr = sdf.format(createTime.getTime() + 1000*60*8);

        Date nowDate = new Date();
        String now = sdf.format(nowDate.getTime()+ 1000*60*3);
        Date startTime= sdf.parse(startTimeStr);
        Date endTime= sdf.parse(endTimeStr);
        Date nowTime= sdf.parse(now);

        boolean effectiveDate = isEffectiveDate(nowTime, startTime, endTime);
        if (!effectiveDate) {
            //System.out.println("当前时间不在范围内");
            return AjaxResult.success("104","验证码已超时",null);
        }


        //判断验证码是否正确
        if(phoneCode != null && phoneCode.getAuthCode().equals(authCode)){
            WxUser wxUser = new WxUser();
            wxUser.setJobNumber(jobNumber);
            wxUser.setPhone(phone);
            wxUser.setWxOpenId(wxOpenId);
            loginMapper.insertUser(wxUser);
            return AjaxResult.successOBD(wxUser);
        }else{
            return AjaxResult.success("104","验证码错误",null);
        }

    }

    /**
     * 获取验证码
     * @param phoneCode
     * @return
     */
    @Override
    @Transactional
    public AjaxResult insertPhoneAuthCode(PhoneCode phoneCode) {

        loginMapper.insertPhoneAuthCode(phoneCode);
        Integer authCode = phoneCode.getAuthCode();
        return AjaxResult.successOBD(authCode);
    }




    /**
     *
     * @param nowTime   当前时间
     * @param startTime	开始时间
     * @param endTime   结束时间
     * @return
     * @author sunran   判断当前时间在时间区间内
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }
}
