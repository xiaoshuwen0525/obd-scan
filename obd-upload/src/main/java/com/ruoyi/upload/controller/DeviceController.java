package com.ruoyi.upload.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.upload.domain.ObdBoxVO;
import com.ruoyi.upload.domain.ObdInfoVO;
import com.ruoyi.upload.domain.ObdPortInfoVO;
import com.ruoyi.upload.service.impl.UploadServiceImpl;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
    private UploadServiceImpl uploadService;

    private String prefix = "device/chassis";

    @RequiresPermissions("device:chassis:view")
    @GetMapping()
    public String chassis() {
        return prefix + "/chassis";
    }

    /**
     * 查询机箱列表
     */
    @RequiresPermissions("device:chassis:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list() {
        //获取当前登录用户相关信息的两种方式
        //Subject subject = SecurityUtils.getSubject();
        //SysUser principal = (SysUser) subject.getPrincipal();
        String loginName = null;
        try {
            loginName = ShiroUtils.getSysUser().getLoginName();
        } catch (Exception ignored) {
        }
        List<ObdBoxVO> obdBoxVOS = null;
        //如果当前登录用户是管理员，则查询所有机箱信息
        if ("admin".equals(loginName)){
            startPage();
            obdBoxVOS = uploadService.obdBoxByJobNumber(null);
        }else{
            //如果不是管理员，PC端页面则不显示任何信息。
            return getDataTable(null);
        }
        return getDataTable(obdBoxVOS);
    }

    @RequiresPermissions("device:chassis:obd:view")
    @GetMapping("/obd/{id}")
    public String obdList(@PathVariable("id") String id, ModelMap mmap) {
        mmap.put("id", id);
        return prefix + "/obd";
    }

    @GetMapping("/obd")
    public String obd() {
        return prefix + "/obd";
    }

    /**
     * 查询OBD列表
     */
    @RequiresPermissions("device:chassis:obd:list")
    @PostMapping("/obd/list/{id}")
    @ResponseBody
    public TableDataInfo queryObdList(@PathVariable("id") String id) {
        startPage();
        List<ObdInfoVO> obdInfoVOS = uploadService.infoByBoxId(id);
        return getDataTable(obdInfoVOS);
    }

    @RequiresPermissions("device:chassis:obd:port:view")
    @GetMapping("/obd/port/{id}")
    public String port(@PathVariable("id") String id, ModelMap mmap) {
        mmap.put("id", id);
        return prefix + "/port";
    }

    /**
     * 查询端口列表
     */
    @RequiresPermissions("device:chassis:obd:port:list")
    @PostMapping("/obd/port/list/{id}")
    @ResponseBody
    public TableDataInfo portList(@PathVariable("id") String id) {
        startPage();
        List<ObdPortInfoVO> obdPortInfoVOS = uploadService.portByObdId(id);
        return getDataTable(obdPortInfoVOS);
    }

}
