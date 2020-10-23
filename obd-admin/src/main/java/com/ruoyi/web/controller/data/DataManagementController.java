package com.ruoyi.web.controller.data;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.web.controller.data.service.IDataManagementService;
import com.ruoyi.web.controller.obd.domain.ImportEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 曾志伟，小洪
 * @date: 2020-10-23 10:45
 */
@Controller
@RequestMapping("/device/baseData")
public class DataManagementController {

    private final String prefix = "device/baseData";

    @Autowired
    private IDataManagementService dataManagementService;

    /**
     * 跳转基础数据页面
     */
    @GetMapping()
    public String chassis() {
        return prefix + "/baseData";
    }

    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<ImportEntity> util = new ExcelUtil<ImportEntity>(ImportEntity.class);
        List<ImportEntity> userList = util.importExcel(file.getInputStream());
        dataManagementService.insertPcObd(userList);
        return AjaxResult.success("导入成功");
    }

    // 导出数据
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ImportEntity importEntity)
    {
        ImportEntity importEntity1 = new ImportEntity();
        importEntity1.setArea("1");
        importEntity1.setBoxBelong("2");
        importEntity1.setBoxCode("3");
        importEntity1.setBusinessBureau("4");
        importEntity1.setCampService("5");
        importEntity1.setLabelCode("6");
        importEntity1.setObdName("7");
        importEntity1.setPortCount(8);
        List<ImportEntity> list = new ArrayList<>();
        list.add(importEntity1);
        ExcelUtil<ImportEntity> util = new ExcelUtil<ImportEntity>(ImportEntity.class);
        return util.exportExcel(list, "基础数据");
    }
}
