package com.ruoyi.upload.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;

import com.ruoyi.upload.domain.PhoneCode;
import com.ruoyi.upload.domain.WxUser;
import com.ruoyi.upload.mapper.LoginMapper;
import com.ruoyi.upload.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


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
    public AjaxResult selectOpenId(String openId) { ;
        WxUser wxUser = loginMapper.selectOpenId(openId);

        if(wxUser!=null && wxUser.getWxOpenId().equals(openId) && wxUser.getPhone() != null ){
            return AjaxResult.success(wxUser);
        }else{
            return AjaxResult.success("104","没有绑定",openId);
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
    public AjaxResult insertUser(String jobNumber,String phone,String wxOpenId,Integer authCode) throws ParseException {

        PhoneCode phoneCode = loginMapper.selectAuthCode();
        //判断手机号是否存在
        WxUser wxUser1 = loginMapper.selectPhone(phone);

        if(wxUser1!=null && wxUser1.getPhone().equals(phone)){
            return AjaxResult.success("104","手机号已绑定,不能重复绑定",null);
        }

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
            return AjaxResult.success("104","验证码已超时，请重新获取",null);
        }

        //判断验证码是否正确
        if(phoneCode != null && phoneCode.getAuthCode().equals(authCode)){
            WxUser wxUser = new WxUser();
            wxUser.setJobNumber(jobNumber);
            wxUser.setPhone(phone);
            wxUser.setWxOpenId(wxOpenId);
            loginMapper.insertUser(wxUser);
            return AjaxResult.success(wxUser);
        }else{
            return AjaxResult.success("104","验证码不对,请重新输入",null);
        }
    }

    /**
     * 获取验证码
     * @param phoneCode
     * @return
     */
    @Override
    public AjaxResult insertPhoneAuthCode(PhoneCode phoneCode) {

        loginMapper.insertPhoneAuthCode(phoneCode);
        Integer authCode = phoneCode.getAuthCode();
        return AjaxResult.success(authCode);
    }

    /**
     * 解绑
     * @return
     */
    @Override
    public AjaxResult updateUnbind(String openId, String phone, Integer authCode) throws ParseException {

        //判断手机号是否存在
        WxUser wxUser1 = loginMapper.selectPhone(phone);

        if(wxUser1!=null && wxUser1.getPhone().equals(phone)){
            return AjaxResult.success("104","手机号已绑定,不能重复绑定",null);
        }

        //判断验证码是否超时  超过5分钟
        PhoneCode phoneCode = loginMapper.selectAuthCode();
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
            return AjaxResult.success("104","验证码已超时，请重新获取",null);
        }

        //判断验证码是否正确
        if(phoneCode != null && phoneCode.getAuthCode().equals(authCode)){
            int unbind = loginMapper.updateUnbind(openId, phone);
            return AjaxResult.success(unbind);
        }else{
            return AjaxResult.success("104","验证码不对,请重新输入",null);
        }
    }

    @Override
    public int unbind(String openId) {
        int unbind = loginMapper.unbind(openId);
        return unbind;
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
