package com.ruoyi.web.controller.system.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.controller.system.domain.PhoneCode;
import com.ruoyi.web.controller.system.service.LoginService;
import com.ruoyi.web.controller.system.util.GetOpenIdUtil;
import com.ruoyi.web.controller.system.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

/**
 * 登陆 控制层
 *
 */
@RestController
@RequestMapping("/wechat/login")
public class LoginController extends BaseController {
    @Autowired
    private LoginService loginService;


    @PostMapping("/visitorLogin")
    public AjaxResult visitorLogin(String code){
        /*if(code == null || code == ""){
            return AjaxResult.success("105","code不能为空",null);
        }*/
        System.out.println( System.currentTimeMillis());
        //获取openid
        /*String openId = null;
        try {
            openId = GetOpenIdUtil.getOpenId(code);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String openId = loginService.visitorLogin(code);

        if(openId == null || openId == ""){
            return AjaxResult.success("105","openId不能为空",openId);
        }else{
            return AjaxResult.successOBD(openId);
        }*/
        return null;
    }


    /**
     * 登陆
     * @param code
     * @return
     * @throws IOException
     */
    @PostMapping("/selectOpenid")
    @ResponseBody
    public AjaxResult selectOpenid(String code) {

        if(code == null || code == ""){
            return AjaxResult.success("105","code不能为空",null);
        }

        //获取openid
        String openId = null;
        try {
            openId = GetOpenIdUtil.getOpenId(code);
        } catch (IOException e) {
            e.printStackTrace();
        }

        AjaxResult ajaxResult = loginService.selectOpenId(openId);
        return ajaxResult;
    }

    /**
     * 绑定
     * @param jobNumber
     * @param phone
     * @param authCode
     * @param
     * @return
     * @throws IOException
     */
    @PostMapping("/bindingSuccess")
    @ResponseBody
    public AjaxResult bindingSuccess(String jobNumber,String phone,String openId,Integer authCode)  {

        AjaxResult ajaxResult = null;
        try {
            ajaxResult = loginService.insertUser(jobNumber, phone, openId, authCode);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ajaxResult;
    }

    /**
     * 获取验证码
     * @param phone
     * @return
     * @throws
     */
    @GetMapping("/getAuthCode")
    public AjaxResult getAuthCode(String phone){
        if(phone == null || phone == ""){
            return AjaxResult.success("105","手机号不能为空",null);
        }

        Integer authCode = (int)((Math.random()*9+1)*1000);
        PhoneCode phoneCode = new PhoneCode();
        phoneCode.setPhone(phone);
        phoneCode.setAuthCode(authCode);
        SmsUtil.phoneAuthCode(phone,authCode);

        AjaxResult ajaxResult = loginService.insertPhoneAuthCode(phoneCode);
        return ajaxResult;
    }

    /**
     * 解绑
     * @return
     */
    @PostMapping("/updateUnbind")
    @ResponseBody
    public AjaxResult updateUnbind(String openId,String phone,Integer authCode) {
        AjaxResult ajaxResult = null;
        try {
            ajaxResult = loginService.updateUnbind(openId, phone, authCode);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ajaxResult;
    }

    @PostMapping("/unbind")
    public AjaxResult unbind(String openId){
        int unbind = loginService.unbind(openId);
        return AjaxResult.success(unbind);
    }
}
