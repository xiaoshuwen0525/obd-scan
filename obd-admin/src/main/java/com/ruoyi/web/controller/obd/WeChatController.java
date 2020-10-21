package com.ruoyi.web.controller.obd;

import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.web.controller.obd.service.impl.ObdDeviceServiceImpl;
import com.ruoyi.web.controller.system.domain.WxUser;
import com.ruoyi.web.controller.upload.domain.ObdBoxVO;
import com.ruoyi.web.controller.upload.domain.ObdInfoVO;
import com.ruoyi.web.controller.upload.domain.ObdPortInfoVO;
import com.ruoyi.web.controller.upload.service.impl.UploadServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

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
        return AjaxResult.success(obdBoxVO);
    }

}
