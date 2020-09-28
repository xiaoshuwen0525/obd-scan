package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysDictData;
import com.ruoyi.system.domain.SysDictDataLoadingPerson;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysDictDataLoadingPersonService;
import com.ruoyi.system.service.ISysDictDataService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 揽装人信息
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/dict/data/loadingPerson")
public class SysDictDataLoadingPersonController extends BaseController
{
    private String prefix = "system/dict/data/loadingPerson";

    @Autowired
    private ISysDictDataLoadingPersonService dictDataService;

    private final String DICTTYPE ="const_loading_person";

    @RequiresPermissions("system:loadingPerson:view")
    @GetMapping()
    public String dictData()
    {
        return prefix + "/data";
    }

    @PostMapping("/list")
    @RequiresPermissions("system:loadingPerson:list")
    @ResponseBody
    public TableDataInfo list(SysDictData dictData)
    {
        startPage();
        dictData.setDictType(DICTTYPE);
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        return getDataTable(list);
    }

    @Log(title = "揽装人数据", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:loadingPerson:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysDictData dictData)
    {
        dictData.setDictType(DICTTYPE);
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);

        List<SysDictDataLoadingPerson> newlist = new ArrayList<>();

        list.forEach(bean->{
            SysDictDataLoadingPerson newLoadingPerson = new SysDictDataLoadingPerson();

            BeanUtils.copyBeanProp(newLoadingPerson,bean);
            newlist.add(newLoadingPerson);
        });


        ExcelUtil<SysDictDataLoadingPerson> util = new ExcelUtil<SysDictDataLoadingPerson>(SysDictDataLoadingPerson.class);
        return util.exportExcel(newlist, "揽装人数据");
    }

    /**
     * 新增字典类型
     */
    @GetMapping("/add/{dictType}")
    public String add(@PathVariable("dictType") String dictType, ModelMap mmap)
    {
        mmap.put("dictType", dictType);
        return prefix + "/add";
    }

    /**
     * 新增保存字典类型
     */
    @Log(title = "揽装人数据", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:loadingPerson:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysDictData dict)
    {
        dict.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(dictDataService.insertDictData(dict));
    }

    /**
     * 修改字典类型
     */
    @GetMapping("/edit/{dictCode}")
    public String edit(@PathVariable("dictCode") Long dictCode, ModelMap mmap)
    {
        mmap.put("dict", dictDataService.selectDictDataById(dictCode));
        return prefix + "/edit";
    }

    /**
     * 修改保存字典类型
     */
    @Log(title = "揽装人数据", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:loadingPerson:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysDictData dict)
    {
        dict.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(dictDataService.updateDictData(dict));
    }

    @Log(title = "揽装人数据", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:loadingPerson:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(dictDataService.deleteDictDataByIds(ids));
    }

    @RequiresPermissions("system:loadingPerson:view")
    @GetMapping("/importLoadingPersonTemplate")
    @ResponseBody
    public AjaxResult importPrivateTemplate()
    {
        ExcelUtil<SysDictDataLoadingPerson> util = new ExcelUtil<SysDictDataLoadingPerson>(SysDictDataLoadingPerson.class);
        return util.importTemplateExcel("揽装人模板");
    }

    @Log(title = "揽装人批量导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:loadingPerson:imputdiscounts")
    @PostMapping("/importLoadingPersonData")
    @ResponseBody
    public AjaxResult importDiscountsData(MultipartFile file, boolean updateSupport) throws Exception
    {
        SysUser currentUser = ShiroUtils.getSysUser();

        ExcelUtil<SysDictDataLoadingPerson> util = new ExcelUtil<SysDictDataLoadingPerson>(SysDictDataLoadingPerson.class);
        List<SysDictDataLoadingPerson> loadingPersonList = util.importExcel(file.getInputStream());
        String message = dictDataService.importLoadingPerson(loadingPersonList, currentUser, file);
        return AjaxResult.success(message);
    }
}
