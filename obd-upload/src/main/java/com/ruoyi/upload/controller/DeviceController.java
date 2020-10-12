package com.ruoyi.upload.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.upload.domain.ViewObd;
import com.ruoyi.upload.service.impl.QueryInfoServiceImpl;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
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
    private QueryInfoServiceImpl queryInfoService;

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
        startPage();
        ViewObd viewObd = new ViewObd();
        viewObd.setJobNumber("80001");
        List<ViewObd> viewObds = queryInfoService.queryInfoByJobId(viewObd);
        return getDataTable(viewObds);
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
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("number", i);
            map.put("id", i + 800000000);
            map.put("status", "正常");
            list.add(map);
        }
        return getDataTable(list);
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
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("number", i);
            map.put("id", i + 700000000);
            map.put("status", "正常");
            list.add(map);
        }
        return getDataTable(list);
    }

}
