package com.ruoyi.web.controller.obd;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.web.controller.obd.service.impl.ObdDeviceServiceImpl;
import com.ruoyi.web.controller.upload.domain.ObdBoxVO;
import com.ruoyi.web.controller.upload.domain.ObdInfoVO;
import com.ruoyi.web.controller.upload.domain.ObdPortInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    private final String prefix = "device/chassis";


    /**
     * 跳转机箱页面
     */
    @GetMapping()
    public String chassis() {
        return prefix + "/chassis";
    }

    /**
     * 管理员用户查询所有机箱列表--该方法暂停使用
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list() {
        String loginName = null;
        try {
            loginName = ShiroUtils.getSysUser().getLoginName();
        } catch (Exception ignored) {
        }
        List<ObdBoxVO> obdBoxVOS = new ArrayList<>();
        //如果当前登录用户是管理员，则查询所有机箱信息
        if ("admin".equals(loginName)) {
            startPage();
            obdBoxVOS = obdDeviceService.selectBoxByJobNumber(null);
        } else {
            //如果不是管理员，PC端页面则不显示任何信息。
            return getDataTable(obdBoxVOS);
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
        List<ObdInfoVO> obdInfoVOS = new ArrayList<>();
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
        List<ObdPortInfoVO> obdPortInfoVOS = new ArrayList<>();
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
    public TableDataInfo searchByCondition(String jobNumber, String phone, String code, String status) {
        List<ObdBoxVO> obdBoxVOS = new ArrayList<>();
        if ("undefined".equals(code)) {
            return getDataTable(obdBoxVOS);
        }
        try {
            startPage();
            obdBoxVOS = obdDeviceService.searchByCondition(jobNumber, phone, code, status);
        } catch (Exception e) {
            return getDataTable(obdBoxVOS);
        }
        return getDataTable(obdBoxVOS);
    }

}
