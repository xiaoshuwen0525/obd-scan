package com.ruoyi.web.controller.obd;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.web.controller.obd.domain.ImportEntity;
import com.ruoyi.web.controller.obd.service.impl.ObdDeviceServiceImpl;
import com.ruoyi.web.controller.upload.domain.ObdBoxVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 微信前端查询数据对接接口
 *
 * @author 曾志伟
 */
@Controller
@RequestMapping("/wechat")
public class WeChatController extends BaseController {

    @Autowired
    private ObdDeviceServiceImpl obdDeviceService;

    /**
     * 通过机箱串码或者倒灌二维码查询所需信息
     */
    @PostMapping("/selectAllInfoByCode")
    @ResponseBody
    public AjaxResult selectAllInfoByCode(String code) {
        if (StringUtils.isBlank(code) || "undefined".equals(code)) {
            return AjaxResult.warn("识别到的串码无效");
        }
        ObdBoxVO obdBoxVO = obdDeviceService.selectAllInfoByCode(code);
        if (obdBoxVO == null) {
            return AjaxResult.warn("该串码未能查询到对应数据");
        }
        return AjaxResult.success("200","查询成功",obdBoxVO);
    }

    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<ImportEntity> util = new ExcelUtil<ImportEntity>(ImportEntity.class);
        List<ImportEntity> userList = util.importExcel(file.getInputStream());
        return AjaxResult.success("message");
    }

}
