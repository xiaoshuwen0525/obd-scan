package com.ruoyi.web.controller.obd;

import cn.hutool.http.HttpStatus;
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
import com.ruoyi.web.controller.upload.service.impl.UploadServiceImpl;
import lombok.Synchronized;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
    private UploadServiceImpl uploadService;

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
            obdBoxVOS = uploadService.obdBoxByJobNumber(null);
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
            obdInfoVOS = uploadService.infoByBoxId(id);
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
            obdPortInfoVOS = uploadService.portByObdId(id);
        } catch (Exception ignored) {
        }
        return getDataTable(obdPortInfoVOS);
    }

    @PostMapping("/searchByCondition")
    @ResponseBody
    public TableDataInfo searchByCondition(String jobNumber, String phone, String boxCode, String status) {
        List<ObdBoxVO> obdBoxVOS = null;
        try {
            startPage();
            obdBoxVOS = obdDeviceService.searchByCondition(jobNumber, phone, boxCode, status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getDataTable(obdBoxVOS);
    }

    @GetMapping("/bindPhoneInteface")
    @RepeatSubmit
    @ResponseBody
    public AjaxResult bindPhone(String jobNumber, String phone) {
        if (StringUtils.isBlank(jobNumber) && StringUtils.isBlank(phone)) {
            return AjaxResult.warn("请求参数不正确");
        }
        String regex = "^((13[0-9])|(17[0-1,6-8])|(15[^4,\\\\D])|(18[0-9]))\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(phone).matches()) {
            return AjaxResult.warn("手机号格式不正确");
        }

        String s = "绑定失败";
        lock.lock();
        try {
            s = obdDeviceService.bindPhone(jobNumber, phone);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return AjaxResult.success(s);
    }

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

    @GetMapping("/bindPhone/{id}")
    public String bindPhoneId(@PathVariable("id") String id, ModelMap mmap) {
        mmap.put("id", id);
        return prefix + "/bindPhone";
    }

    /**
     * 根据机箱唯一ID查询端口列表
     */
    @PostMapping("/bindPhone/bind/{id}")
    @ResponseBody
    public AjaxResult bindPhoneList(@PathVariable("id") String id) {
        List<WxUser> wxUsers = null;
        try {
            wxUsers = obdDeviceService.queryWechatInfo(null, null, id);
        } catch (Exception e) {
            return AjaxResult.warn("未能查询到对应信息");
        }
        return AjaxResult.success(wxUsers);
    }

    @PostMapping("/queryWechatInfo")
    @ResponseBody
    public TableDataInfo queryWechatInfo(String jobNumber, String phone, String id) {
        startPage();
        List<WxUser> wxUsers = obdDeviceService.queryWechatInfo(jobNumber, phone, id);
        return getDataTable(wxUsers);
    }


}
