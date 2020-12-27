package com.ruoyi.web.controller.data;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.interceptor.RepeatSubmitInterceptor;
import com.ruoyi.framework.interceptor.impl.SameUrlDataInterceptor;
import com.ruoyi.web.controller.data.domain.*;
import com.ruoyi.web.controller.data.service.IDataManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础数据管理
 * @author: 曾志伟，小洪
 * @date: 2020-10-23 10:45
 */
@Controller
@RequestMapping("/device/baseData")
public class DataManagementController extends BaseController {

    private final String prefix = "device/baseData";

    private static Long lastVisitTime = 1L;

    private static int visitCount = 1;

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
        derivedEntity.setBoxId(id);
        List<DerivedEntity> derivedEntities = new ArrayList<>();
        try {
            derivedEntities = dataManagementService.selectObdByEntity(derivedEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (derivedEntities == null) {
            throw new BusinessException("该机箱数据异常，请联系管理员查看");
        }
        int count = 0;
        List<PcObdInfo> pcObdInfos = new ArrayList<>();
        for (DerivedEntity entity : derivedEntities) {
            PcObdInfo pcObdInfo = new PcObdInfo();
            if (count == 0) {
                PcObdBox pcObdBox = new PcObdBox();
                BeanUtil.copyProperties(entity, pcObdBox);
                pcObdBox.setId(entity.getId());
                mmap.put("box", pcObdBox);
            }
            if (entity.getObdId() == null) {
                break;
            }
            BeanUtil.copyProperties(entity, pcObdInfo);
            pcObdInfo.setId(entity.getObdId());
            pcObdInfos.add(pcObdInfo);
            count++;
        }
        mmap.put("obd", pcObdInfos);
        mmap.put("obdSize", pcObdInfos.size());
        return prefix + "/baseUpdate";
    }

    @PostMapping("/searchBoxNameList")
    @ResponseBody
    public List<PcObdBox> searchBoxNameList(String boxName) {
        List<PcObdBox> pcObdBoxes = new ArrayList<>();
        if (StringUtils.isNotBlank(boxName)) {
            pcObdBoxes = dataManagementService.selectAllBoxName(boxName);
        }
        //限制接口短时间内的多次访问
        //if (visitCount == 1) {
        //    //第一次调用该接口的时候记录访问时间
        //    lastVisitTime = System.currentTimeMillis();
        //    if (StringUtils.isNotBlank(boxName)) {
        //        pcObdBoxes = dataManagementService.selectAllBoxName(boxName);
        //    }
        //    visitCount = 2;
        //} else {
        //    Long currentVisitTime = System.currentTimeMillis();
        //    Long time = currentVisitTime - lastVisitTime;
        //    if (time <= 300) {
        //        visitCount = 0;
        //    } else {
        //        visitCount = 1;
        //    }
        //}
        return pcObdBoxes;
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
     * 导入端口数据
     */
    @PostMapping("/importPortData")
    @ResponseBody
    public AjaxResult importPortData(MultipartFile file) throws Exception {
        ExcelUtil<ImportPortEntity> util = new ExcelUtil<ImportPortEntity>(ImportPortEntity.class);
        List<ImportPortEntity> userList = util.importExcel(file.getInputStream());
        return dataManagementService.insertPcObdPort(userList);
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

    /**
     * 端口数据导入模板
     */
    @GetMapping("/importPortDataTemplate")
    @ResponseBody
    public AjaxResult importPortDataTemplate() {
        ExcelUtil<ImportPortEntity> util = new ExcelUtil<ImportPortEntity>(ImportPortEntity.class);
        return util.importTemplateExcel("端口数据导入模板");
    }

    /**
     * 基础数据导入模板
     */
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult exportTemplate() {
        ExcelUtil<ImportEntity> util = new ExcelUtil<ImportEntity>(ImportEntity.class);
        return util.importTemplateExcel("基础数据导入模板");
    }

    @PostMapping("/updateBaseData")
    @ResponseBody
    public AjaxResult updateBaseData(BaseDataVo baseDataVo) {
        if (baseDataVo == null) {
            return AjaxResult.error("不允许全部置空");
        }
        if ("".equals(StringUtils.trim(baseDataVo.getBoxCode())) &&
                "".equals(StringUtils.trim(baseDataVo.getLabelCode())) &&
                "".equals(StringUtils.trim(baseDataVo.getCampService())) &&
                "".equals(StringUtils.trim(baseDataVo.getArea())) &&
                "".equals(StringUtils.trim(baseDataVo.getBoxName())) &&
                "".equals(StringUtils.trim(baseDataVo.getBusinessBureau()))) {
            return AjaxResult.error("机箱信息不允许全部置空或输入空格");
        }
        //OBD部分信息为空的情况下需按情况调用方法避免产生空指针错误
        if (CollectionUtils.isEmpty(baseDataVo.getObdName())||CollectionUtils.isEmpty(baseDataVo.getPortCount())){
            baseDataVo.makeBaseUpdateListNull();
        }else{
            baseDataVo.makeBaseUpdateList();
        }
        int i;
        try {
            i = dataManagementService.updateBaseData(baseDataVo);
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
    @RepeatSubmit
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
        startPage();
        return getDataTable(dataManagementService.selectBoxListByEntity(pcObdBox));
    }

    @PostMapping("/selectByBoxId/{id}")
    @ResponseBody
    public TableDataInfo selectPcObdBoxById(@PathVariable("id") int boxId) {
        startPage();
        return getDataTable(dataManagementService.selectByBoxId(boxId));
    }


}
