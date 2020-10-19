package com.ruoyi.web.controller.system.service;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.controller.system.domain.PhoneCode;

import java.text.ParseException;

/**
 * 登陆 服务层
 *
 */
public interface LoginService {

    /**
     * 登陆
     * @param openId
     * @return
     */
    public AjaxResult selectOpenId(String openId);

    /**
     * 添加用户
     * @param
     * @return
     */
    public AjaxResult insertUser(String jobNumber,String phone,String wxOpenId,Integer authCode) throws ParseException;

    /**
     * 添加手机号  验证码
     * @return
     */
    public AjaxResult insertPhoneAuthCode(PhoneCode phoneCode);

    /**
     * 解绑
     * @return
     */
    public AjaxResult updateUnbind(String openId,String phone,Integer authCode) throws ParseException;

    public int unbind(String openId);

}
