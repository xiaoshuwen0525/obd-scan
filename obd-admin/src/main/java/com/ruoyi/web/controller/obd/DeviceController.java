package com.ruoyi.web.controller.obd;

import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.web.controller.obd.service.impl.ObdDeviceServiceImpl;
import com.ruoyi.web.controller.system.domain.WxUser;
import com.ruoyi.web.controller.upload.domain.ObdBoxVO;
import com.ruoyi.web.controller.upload.domain.ObdInfoVO;
import com.ruoyi.web.controller.upload.domain.ObdPortInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

/**
 * 设备 机箱操作处理
 *
 * @author 曾志伟
 */
@Controller
@RequestMapping("/device/chassis")
public class DeviceController extends BaseController {

    @Autowired
    private ObdDeviceServiceImpl obdDeviceService;

    private static final Lock lock = new ReentrantLock();

    private String prefix = "device/chassis";

    /**
     * 跳转用户页面
     */
    @GetMapping("/obdUser")
    public String obdUser() {
        return prefix + "/obdUser";
    }

    /**
     * 跳转机箱页面
     */
    @GetMapping()
    public String chassis() {
        return prefix + "/chassis";
    }

    /**
     * 管理员用户查询所有机箱列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list() {
        String loginName = null;
        try {
            loginName = ShiroUtils.getSysUser().getLoginName();
        } catch (Exception ignored) {
        }
        List<ObdBoxVO> obdBoxVOS = null;
        //如果当前登录用户是管理员，则查询所有机箱信息
        if ("admin".equals(loginName)) {
            startPage();
            obdBoxVOS = obdDeviceService.selectBoxByJobNumber(null);
        } else {
            //如果不是管理员，PC端页面则不显示任何信息。
            return getDataTable(null);
        }
        return getDataTable(obdBoxVOS);
    }

    /**
     * 跳转ODB页面并携带当前点击的机箱ID
     */
    @GetMapping("/obd/{id}")
    public String obdList(@PathVariable("id") String id, ModelMap mmap) {
        mmap.put("id", id);
        return prefix + "/obd";
    }

    /**
     * 根据机箱唯一ID查询端口列表
     */
    @PostMapping("/obd/list/{id}")
    @ResponseBody
    public TableDataInfo queryObdList(@PathVariable("id") String id) {
        List<ObdInfoVO> obdInfoVOS = null;
        try {
            startPage();
            obdInfoVOS = obdDeviceService.infoByBoxId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getDataTable(obdInfoVOS);
    }

    /**
     * 跳转端口页面并携带当前点击的OBD唯一ID
     */
    @GetMapping("/obd/port/{id}")
    public String port(@PathVariable("id") String id, ModelMap mmap) {
        mmap.put("id", id);
        return prefix + "/port";
    }

    /**
     * 根据obd唯一ID查询端口列表
     */
    @PostMapping("/obd/port/list/{id}")
    @ResponseBody
    public TableDataInfo portList(@PathVariable("id") String id) {
        List<ObdPortInfoVO> obdPortInfoVOS = null;
        try {
            startPage();
            obdPortInfoVOS = obdDeviceService.portByObdId(id);
        } catch (Exception ignored) {
        }
        return getDataTable(obdPortInfoVOS);
    }

    /**
     * 条件查询
     */
    @PostMapping("/searchByCondition")
    @ResponseBody
    public TableDataInfo searchByCondition(String jobNumber, String phone, String boxCode, String status) {
        List<ObdBoxVO> obdBoxVOS = null;
        String loginName;
        try {
            loginName = ShiroUtils.getSysUser().getLoginName();
        } catch (Exception e) {
            return getDataTable(obdBoxVOS);
        }
        //如果当前登录用户是管理员，则查询所有机箱信息
        if ("admin".equals(loginName)) {
            jobNumber = null;
        }
        try {
            startPage();
            obdBoxVOS = obdDeviceService.searchByCondition(jobNumber, phone, boxCode, status);
        } catch (Exception e) {
            return getDataTable(obdBoxVOS);
        }
        return getDataTable(obdBoxVOS);
    }


    /**
     * 手机号解绑接口
     */
    @GetMapping("/unBindPhoneInteface")
    @RepeatSubmit
    @ResponseBody
    public AjaxResult unBindPhone(String id) {
        if (StringUtils.isBlank(id)) {
            return AjaxResult.warn("请求参数不正确");
        }
        String s;
        lock.lock();
        try {
            s = obdDeviceService.unBindPhone(id);
        } catch (Exception e) {
            return AjaxResult.error("解绑失败");
        } finally {
            lock.unlock();
        }
        return AjaxResult.success(s);
    }

    /**
     * 给前端页面传值
     */
    @GetMapping("/bindPhone/{id}")
    public String bindPhoneId(@PathVariable("id") String id, ModelMap mmap) {
        mmap.put("id", id);
        List<WxUser> wxUsers = null;
        try {
            wxUsers = obdDeviceService.queryWechatInfo(null, null, id);
            mmap.put("jobNumber", wxUsers.get(0).getJobNumber());
            mmap.put("phone", wxUsers.get(0).getPhone());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prefix + "/bindPhone";
    }

    /**
     * 绑定新的手机号码
     */
    @PostMapping("/bindPhone/bind")
    @ResponseBody
    public AjaxResult bindPhoneList(String jobNumber, String phone, String newPhone) {
        if (StringUtils.isBlank(jobNumber) && "undefined".equals(jobNumber)) {
            return AjaxResult.warn("该员工信息有误，请联系管理员处理");
        }
        if (phone.equals(newPhone)) {
            return AjaxResult.warn("新的手机号码与原手机号码一致");
        }
        String regex = "^((13[0-9])|(17[0-1,6-8])|(15[^4,\\\\D])|(18[0-9]))\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(newPhone).matches()) {
            return AjaxResult.warn("新手机号格式不正确");
        }
        int phoneNumberExist = obdDeviceService.isPhoneNumberExist(newPhone);
        if (phoneNumberExist > 0) {
            return AjaxResult.warn("所填新手机号码已被使用");
        }
        int i = obdDeviceService.bindPhone(jobNumber, phone, newPhone);
        if (i == 0) {
            return AjaxResult.warn("绑定失败");
        }
        return AjaxResult.success("绑定成功");
    }

    /**
     * 条件查询微信用户信息
     */
    @PostMapping("/queryWechatInfo")
    @ResponseBody
    public TableDataInfo queryWechatInfo(String jobNumber, String phone, String id) {
        startPage();
        List<WxUser> wxUsers = obdDeviceService.queryWechatInfo(jobNumber, phone, id);
        return getDataTable(wxUsers);
    }

    /**
     * 通过机箱串码或者倒灌二维码查询所需信息
     */
    @GetMapping("/selectAllInfoByCode")
    public AjaxResult selectAllInfoByCode(String code) {
        if (StringUtils.isBlank(code) && "undefined".equals(code)) {
            return AjaxResult.warn("未能识别到有效机箱串码或标签二维码");
        }
        ObdBoxVO obdBoxVO = obdDeviceService.selectAllInfoByCode(code);
        if (obdBoxVO == null) {
            return AjaxResult.warn("该二维码未能查询到对应数据");
        }
        return AjaxResult.success(obdBoxVO);
    }

}
