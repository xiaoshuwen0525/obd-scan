package com.ruoyi.web.controller.system.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.web.controller.employee.domain.ImportUser;
import com.ruoyi.web.controller.system.domain.PhoneCode;
import com.ruoyi.web.controller.system.service.LoginService;
import com.ruoyi.web.controller.system.util.GetOpenIdUtil;
import com.ruoyi.web.controller.system.util.SmsUtil;
import com.ruoyi.web.controller.upload.utils.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 登陆 控制层
 *
 */
@RestController
@RequestMapping("/wechat/login")
public class LoginController extends BaseController {
    @Autowired
    private LoginService loginService;

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    /**
     * 登陆
     * @param code
     * @return
     * @throws IOException
     */
    @PostMapping("/selectOpenid")
    @ResponseBody
    public AjaxResult selectOpenid(String code, HttpServletRequest request) throws IOException {
        log.info("成功进入【"+request.getRequestURI()+"】接口"+ "GET请求参数:"+ RequestUtil.getMapParams(request)+"Post请求参数:"+RequestUtil.getRequestBody(request));
        if(StringUtils.isBlank(code)){
            return AjaxResult.success("104","code不能为空",null);
        }

        //获取openid
        String openId = null;
        try {
            openId = GetOpenIdUtil.getOpenId(code);
        } catch (Exception e) {
            return AjaxResult.error("登陆失败");
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
    public AjaxResult bindingSuccess(String jobNumber,String phone,String openId,Integer authCode, HttpServletRequest request) throws IOException {
        log.info("成功进入【"+request.getRequestURI()+"】接口"+ "GET请求参数:"+RequestUtil.getMapParams(request)+"Post请求参数:"+RequestUtil.getRequestBody(request));

        if(StringUtils.isBlank(jobNumber) && "undefined".equals(jobNumber)){
            return AjaxResult.success("104","工号不能为空",null);
        }
        if(StringUtils.isBlank(phone)){
            return AjaxResult.success("104","手机号不能为空",null);
        }
        if(StringUtils.isBlank(openId)){
            return AjaxResult.success("104","openId不能为空",null);
        }
        if(authCode == null){
            return AjaxResult.success("104","验证码不能为空",null);
        }
        String regex = "^((13[0-9])|(17[0-1,6-8])|(15[^4,\\\\D])|(18[0-9]))\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        if(!pattern.matcher(phone).matches()){
            return AjaxResult.success("104","手机号格式不正确",null);
        }

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
    public AjaxResult getAuthCode(String phone, HttpServletRequest request) throws IOException {
        log.info("成功进入【"+request.getRequestURI()+"】接口"+ "GET请求参数:"+RequestUtil.getMapParams(request)+"Post请求参数:"+RequestUtil.getRequestBody(request));
        if(StringUtils.isBlank(phone)){
            return AjaxResult.success("104","手机号不能为空",null);
        }
        String regex = "^((13[0-9])|(17[0-1,6-8])|(15[^4,\\\\D])|(18[0-9]))\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        if(!pattern.matcher(phone).matches()){
            return AjaxResult.success("104","手机号格式不正确",null);
        }

        Integer authCode = (int)((Math.random()*9+1)*1000);
        PhoneCode phoneCode = new PhoneCode();
        phoneCode.setPhone(phone);
        phoneCode.setAuthCode(authCode);
        SmsUtil.phoneAuthCode(phone,authCode);

        AjaxResult ajaxResult = loginService.insertPhoneAuthCode(phoneCode);
        return ajaxResult;
    }

  /*  *//**
     * 解绑
     * @return
     *//*
    @PostMapping("/updateUnbind")
    @ResponseBody
    public AjaxResult updateUnbind(String jobNumber,String openId,String phone,Integer authCode, HttpServletRequest request) throws IOException {
        log.info("成功进入【"+request.getRequestURI()+"】接口"+ "GET请求参数:"+RequestUtil.getMapParams(request)+"Post请求参数:"+RequestUtil.getRequestBody(request));
        if(StringUtils.isBlank(jobNumber) && "undefined".equals(jobNumber)){
            return AjaxResult.success("104","工号不能为空",null);
        }
        if(StringUtils.isBlank(phone)){
            return AjaxResult.success("104","手机号不能为空",null);
        }
        if(StringUtils.isBlank(openId)){
            return AjaxResult.success("104","openId不能为空",null);
        }
        if(authCode == null){
            return AjaxResult.success("104","验证码不能为空",null);
        }

        AjaxResult ajaxResult = null;
        try {
            ajaxResult = loginService.updateUnbind(jobNumber,openId, phone, authCode);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ajaxResult;
    }

    @PostMapping("/unbind")
    public AjaxResult unbind(String openId, HttpServletRequest request) throws IOException {
        log.info("成功进入【"+request.getRequestURI()+"】接口"+ "GET请求参数:"+RequestUtil.getMapParams(request)+"Post请求参数:"+RequestUtil.getRequestBody(request));
        if(StringUtils.isBlank(openId)){
            return AjaxResult.success("104","openId不能为空",null);
        }
        int unbind = loginService.unbind(openId);
        return AjaxResult.success(unbind);
    }*/
}
