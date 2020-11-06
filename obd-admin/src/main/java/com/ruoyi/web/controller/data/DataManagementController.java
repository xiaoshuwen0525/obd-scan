package com.ruoyi.web.controller.data;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.web.controller.data.domain.*;
import com.ruoyi.web.controller.data.service.IDataManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 曾志伟，小洪
 * @date: 2020-10-23 10:45
 */
@Controller
@RequestMapping("/device/baseData")
public class DataManagementController extends BaseController {

    private final String prefix = "device/baseData";

    @Autowired
    private IDataManagementService dataManagementService;

    /**
     * 跳转基础数据页面
     */
    @GetMapping()
    public String baseData() {
        return prefix + "/baseData";
    }

    /**
     * 跳转基础数据修改页面
     */
    @GetMapping("/baseUpdate/{id}")
    public String baseUpdateById(@PathVariable("id") int id, ModelMap mmap) {
        DerivedEntity derivedEntity = new DerivedEntity();
        List<PcObdBox> pcObdBoxes = new ArrayList<>();
        derivedEntity.setBoxId(id);
        List<DerivedEntity> derivedEntities = new ArrayList<>();
        try {
            derivedEntities = dataManagementService.selectObdByEntity(derivedEntity);
            pcObdBoxes = dataManagementService.selectAllBoxName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (derivedEntities == null) {
            return prefix + "/baseUpdate";
        }
        int count = 0;
        List<PcObdInfo> pcObdInfos = new ArrayList<>();
        for (DerivedEntity entity : derivedEntities) {
            PcObdInfo pcObdInfo = new PcObdInfo();
            if (count == 0) {
                PcObdBox pcObdBox = new PcObdBox();
                BeanUtil.copyProperties(entity, pcObdBox);
                pcObdBox.setId(entity.getBoxId());
                mmap.put("box", pcObdBox);
            }
            BeanUtil.copyProperties(entity, pcObdInfo);
            pcObdInfo.setId(entity.getObdId());
            pcObdInfos.add(pcObdInfo);
            count++;
        }
        mmap.put("obd", pcObdInfos);
        mmap.put("boxNameList", pcObdBoxes);
        mmap.put("obdSize", pcObdInfos.size());
        return prefix + "/baseUpdate";
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
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<ImportEntity> util = new ExcelUtil<ImportEntity>(ImportEntity.class);
        List<ImportEntity> userList = util.importExcel(file.getInputStream());
        return dataManagementService.insertPcObd(userList);
    }

    /**
     * 导出数据
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DerivedEntity derivedEntity) {
        List<DerivedEntity> derivedEntities = dataManagementService.selectObdByEntity(derivedEntity);
        ExcelUtil<DerivedEntity> util = new ExcelUtil<DerivedEntity>(DerivedEntity.class);
        return util.exportExcel(derivedEntities, "基础数据");
    }

    @PostMapping("/updateBaseData")
    @ResponseBody
    public AjaxResult updateBaseData(BaseDataVo baseDataVo) {
        if (baseDataVo == null) {
            return AjaxResult.error("不允许全部置空");
        }
        baseDataVo.makeBaseUpdateList();
        int i;
        try {
            i = dataManagementService.updateBaseData(baseDataVo.getBaseUpdateList());
            if (i > 0) {
                return AjaxResult.success("更新成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error("更新失败");
    }

    @PostMapping("/deletePcObdBoxById")
    @ResponseBody
    public AjaxResult deletePcObdBoxByIds(String ids) {
        if (StringUtils.isBlank(ids)) {
            return AjaxResult.error("数据有误，请联系管理员");
        }
        int i;
        try {
            i = dataManagementService.deletePcObdBoxByIds(ids);
            if (i > 0) {
                return AjaxResult.success("成功删除");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error("数据有误，请联系管理员");
    }

    @PostMapping("/selectBoxListByEntity")
    @ResponseBody
    public TableDataInfo selectBoxListByEntity(PcObdBox pcObdBox) {
        return getDataTable(dataManagementService.selectBoxListByEntity(pcObdBox));
    }

    @PostMapping("/selectByBoxId/{id}")
    @ResponseBody
    public TableDataInfo selectPcObdBoxById(@PathVariable("id") int boxId) {
        return getDataTable(dataManagementService.selectByBoxId(boxId));
    }


}
