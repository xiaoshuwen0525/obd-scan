package com.ruoyi.web.controller.obd;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.exception.base.BaseException;
import com.ruoyi.common.exception.user.UserException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.web.controller.data.domain.CheckState;
import com.ruoyi.web.controller.data.domain.ImportEntity;
import com.ruoyi.web.controller.obd.service.IObdDeviceService;
import com.ruoyi.web.controller.obd.service.impl.ObdDeviceServiceImpl;
import com.ruoyi.web.controller.upload.domain.ObdBoxVO;
import com.ruoyi.web.controller.upload.domain.ObdInfoVO;
import com.ruoyi.web.controller.upload.domain.ObdPortInfoVO;
import com.ruoyi.web.controller.upload.domain.ObdView;
import com.ruoyi.web.controller.upload.service.impl.UploadServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.Subject;
import java.util.ArrayList;
import java.util.List;

/**
 * 整治数据管理
 *
 * @author 曾志伟
 */
@Controller
@RequestMapping("/device/chassis")
public class DeviceController extends BaseController {

    @Autowired
    private IObdDeviceService obdDeviceService;

    @Autowired
    private UploadServiceImpl uploadService;

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
     * 跳转备注编辑页面并携带当前机箱ID
     */
    @GetMapping("/remarks/{id}")
    public String editRemakers(@PathVariable("id") String id, ModelMap mmap) {
        SysUser principal = (SysUser) SecurityUtils.getSubject().getPrincipal();
        List<SysRole> roles = principal.getRoles();
        for (SysRole role : roles) {
            if ("1".equals(role.getDataScope())) {
                ObdBoxVO obdBoxVOS = null;
                try {
                    obdBoxVOS = obdDeviceService.boxRemarksById(id);
                } catch (Exception e) {
                    return prefix + "/remarks";
                }
                mmap.put("id", id);
                if (obdBoxVOS != null) {
                    if (obdBoxVOS.getRemarks() != null) {
                        mmap.put("remarks", obdBoxVOS.getRemarks());
                    } else {
                        mmap.put("remarks", "");
                    }
                }
                return prefix + "/remarks";
            }
        }
        throw new BusinessException("仅支持管理员操作");
    }


    /**
     * 跳转备注编辑页面并携带当前OBD唯一ID
     */
    @GetMapping("obd/remarks/{id}")
    public String editObdRemakers(@PathVariable("id") String id, ModelMap mmap) {
        SysUser principal = (SysUser) SecurityUtils.getSubject().getPrincipal();
        List<SysRole> roles = principal.getRoles();
        for (SysRole role : roles) {
            if ("1".equals(role.getDataScope())) {
                ObdInfoVO obdInfoVO = null;
                try {
                    obdInfoVO = obdDeviceService.ObdRemarksById(id);
                } catch (Exception e) {
                    return prefix + "/remarks";
                }
                mmap.put("id", id);
                if (obdInfoVO != null) {
                    if (obdInfoVO.getRemarks() != null) {
                        mmap.put("remarks", obdInfoVO.getRemarks());
                    } else {
                        mmap.put("remarks", "");
                    }
                }
                return prefix + "/remarks";
            }
        }
        throw new BusinessException("仅支持管理员操作");
    }

    /**
     * 更新备注信息
     */
    @PostMapping("/remarks/update")
    @ResponseBody
    public AjaxResult updateRemakers(String id, String remarks) {
        if (StringUtils.isNotBlank(id) && remarks != null) {
            int i = obdDeviceService.updateRemakers(id, remarks);
            if (i > 0) {
                return AjaxResult.success("更新成功");
            }
        }
        return AjaxResult.error("请求参数有误");
    }


    /**
     * 更新OBD备注信息
     */
    @PostMapping("obd/remarks/update")
    @ResponseBody
    public AjaxResult updateObdRemakers(String id, String remarks) {
        if (StringUtils.isNotBlank(id) && remarks != null) {
            int i = obdDeviceService.updateObdRemakers(id, remarks);
            if (i > 0) {
                return AjaxResult.success("更新成功");
            }
        }
        return AjaxResult.error("请求参数有误");
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
    public TableDataInfo searchByCondition(String jobNumber, String phone, String boxName, String code, String checkState) {
        List<ObdBoxVO> obdBoxVOS = new ArrayList<>();
        if ("undefined".equals(code)) {
            return getDataTable(obdBoxVOS);
        }
        try {
            startPage();
            obdBoxVOS = obdDeviceService.searchByCondition(jobNumber, phone, boxName, code, checkState);
        } catch (Exception e) {
            return getDataTable(obdBoxVOS);
        }
        return getDataTable(obdBoxVOS);
    }

    /**
     * 跳转图片展示页面并携带当前点击的机箱唯一ID
     */
    @GetMapping("/pcShowImg/{id}")
    public String pcShowImg(@PathVariable("id") String id, ModelMap mmap) {
        if (StringUtils.isNotEmpty(id)) {
            ObdBoxVO boxVO = new ObdBoxVO();
            try {
                boxVO = uploadService.selectBoxById(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (StringUtils.isNotEmpty(boxVO.getImgUrl())) {
                mmap.put("image", boxVO.getImgUrl());
            } else {
                mmap.put("image", "static/obdImg/b06045afe09ac9868b1dab8b5ec3108.jpg");
            }
        } else {
            mmap.put("image", "static/obdImg/83bbabd27184ef8eaa3037b1ba9eb22.jpg");
        }
        return prefix + "/image";
    }


    /**
     * 导出数据
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ObdView obdView) {
        List<ObdView> obdViewList = obdDeviceService.selectExportObd(obdView);
        ExcelUtil<ObdView> util = new ExcelUtil<ObdView>(ObdView.class);
        return util.exportExcel(obdViewList, "整治数据");
    }


    /**
     * 批量审核状态为合格
     *
     * @param ids
     * @return
     */
    @PostMapping("obd/checkYes/{id}")
    @ResponseBody
    @RepeatSubmit
    public AjaxResult checkYes(@PathVariable("id") String boxId, String ids) {
        SysUser principal = (SysUser) SecurityUtils.getSubject().getPrincipal();
        List<SysRole> roles = principal.getRoles();
        for (SysRole role : roles) {
            if ("1".equals(role.getDataScope())) {
                if (StringUtils.isBlank(ids)) {
                    return AjaxResult.error("数据有误，请联系管理员");
                }
                int i;
                try {
                    i = obdDeviceService.updateCheckState(boxId, ids, 1);
                    if (i > 0) {
                        return AjaxResult.success("状态更新成功");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return AjaxResult.error("操作失败，请重试");
            }
        }
        return AjaxResult.error("仅支持管理员操作");
    }

    /**
     * 批量审核状态为不合格
     *
     * @param ids
     * @return
     */
    @PostMapping("obd/checkNo/{id}")
    @ResponseBody
    @RepeatSubmit
    public AjaxResult checkNo(@PathVariable("id") String boxId, String ids) {
        SysUser principal = (SysUser) SecurityUtils.getSubject().getPrincipal();
        List<SysRole> roles = principal.getRoles();
        for (SysRole role : roles) {
            if ("1".equals(role.getDataScope())) {
                if (StringUtils.isBlank(ids)) {
                    return AjaxResult.error("数据有误，请联系管理员");
                }
                int i;
                try {
                    i = obdDeviceService.updateCheckState(boxId, ids, 0);
                    if (i > 0) {
                        return AjaxResult.success("状态更新成功");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return AjaxResult.error("操作失败，请重试");
            }
        }
        return AjaxResult.error("仅支持管理员操作");
    }

    /**
     * 导入审核数据
     */
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importChectStateData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<CheckState> util = new ExcelUtil<CheckState>(CheckState.class);
        List<CheckState> userList = util.importExcel(file.getInputStream());
        int i = obdDeviceService.updateObdCheckStateData(userList);
        if (i > 0) {
            return AjaxResult.success("导入审核状态成功");
        } else {
            return AjaxResult.error("导入审核状态失败");
        }
    }

    /**
     * 审核数据导入模板
     */
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult exportTemplate() {
        ExcelUtil<CheckState> util = new ExcelUtil<CheckState>(CheckState.class);
        return util.importTemplateExcel("审核数据导入模板");
    }

}
