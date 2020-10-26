package com.ruoyi.web.controller.data;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.web.controller.data.domain.DerivedEntity;
import com.ruoyi.web.controller.data.domain.PcObdBox;
import com.ruoyi.web.controller.data.domain.PcObdInfo;
import com.ruoyi.web.controller.data.service.IDataManagementService;
import com.ruoyi.web.controller.data.domain.ImportEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: 曾志伟，小洪
 * @date: 2020-10-23 10:45
 */
@Controller
@RequestMapping("/device/baseData")
public class DataManagementController extends BaseController {

    private final String prefix = "device/baseData";

    private static final Lock lock = new ReentrantLock();

    @Autowired
    private IDataManagementService dataManagementService;

    /**
     * 跳转基础数据页面
     */
    @GetMapping()
    public String chassis() {
        return prefix + "/baseData";
    }

    /**
     * 基础数据管理--跳转ODB页面并携带当前点击的机箱ID
     */
    @GetMapping("/selectByBoxId/{id}")
    public String obdList(@PathVariable("id") int id, ModelMap mmap) {
        mmap.put("id", id);
        return prefix + "/baseObdData";
    }

    /**
     * 导入数据
     */
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<ImportEntity> util = new ExcelUtil<ImportEntity>(ImportEntity.class);
        List<ImportEntity> userList = util.importExcel(file.getInputStream());
        AjaxResult ajaxResult = dataManagementService.insertPcObd(userList);
        return ajaxResult;
    }

    /**
     * 导出数据
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DerivedEntity derivedEntity)
    {
        List<DerivedEntity> derivedEntities = dataManagementService.selectObdByEntity(derivedEntity);
        ExcelUtil<DerivedEntity> util = new ExcelUtil<DerivedEntity>(DerivedEntity.class);
        return util.exportExcel(derivedEntities, "基础数据");
    }


    @PostMapping("/updatePcObdBox")
    @ResponseBody
    public AjaxResult updatePcObdBox(PcObdBox pcObdBox)
    {
        String s = "更新失败" ;
        try {
            int i = dataManagementService.updatePcObdBox(pcObdBox);
            if(i>0){
                s = "更新成功";
            }
        }catch (Exception e){
            AjaxResult.error(s);
        }
        return AjaxResult.success(s);
    }

    @PostMapping("/updatePcObdInfo")
    @ResponseBody
    public AjaxResult updatePcObdInfo(PcObdInfo pcObdInfo)
    {
        String s = "更新失败" ;
        try {
            int i = dataManagementService.updatePcObdInfo(pcObdInfo);
            if(i>0){
                s = "更新成功";
            }
        }catch (Exception e){
            AjaxResult.error(s);
        }
        return AjaxResult.success(s);
    }

    @PostMapping("/deletePcObdInfoById")
    @ResponseBody
    public AjaxResult deletePcObdInfoById(Integer id)
    {
        String s = "删除失败" ;
        try {
            int i = dataManagementService.deletePcObdInfoById(id);
            if(i>0){
                s = "删除成功";
            }
        }catch (Exception e){
            AjaxResult.error(s);
        }
        return AjaxResult.success(s);
    }

    @PostMapping("/selectBoxListByEntity")
    @ResponseBody
    public TableDataInfo selectBoxListByEntity(PcObdBox pcObdBox)
    {
        return getDataTable(dataManagementService.selectBoxListByEntity(pcObdBox));
    }

    @PostMapping("/selectByBoxId/{id}")
    @ResponseBody
    public TableDataInfo deletePcObdBoxById(@PathVariable("id") int boxId)
    {
        return getDataTable(dataManagementService.selectByBoxId(boxId));
    }


}
