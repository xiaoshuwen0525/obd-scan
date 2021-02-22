package com.ruoyi.web.controller.obd;

import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.controller.data.domain.BaseDataVo;
import com.ruoyi.web.controller.obd.service.impl.ObdDeviceServiceImpl;
import com.ruoyi.web.controller.system.domain.WxUser;
import com.ruoyi.web.controller.upload.domain.ObdBoxVO;
import com.ruoyi.web.controller.upload.domain.ObdInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("device/wechat")
public class WeChatController extends BaseController {

    @Autowired
    private ObdDeviceServiceImpl obdDeviceService;

    private static final Lock lock = new ReentrantLock();

    private String prefix = "device/wechat";

    /**
     * 跳转用户页面
     */
    @GetMapping("/obdUser")
    public String obdUser() {
        return prefix + "/obdUser";
    }

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
        return AjaxResult.success("200", "查询成功", obdBoxVO);
    }

    /**
     * 通过机箱串码或者倒灌二维码查询基础信息--前端特定数据返回专用
     */
    @PostMapping("/selectBaseDataByCode")
    @ResponseBody
    public AjaxResult selectBaseDataByCode(String code) {
        if (StringUtils.isBlank(code) || "undefined".equals(code)) {
            return AjaxResult.warn("识别到的串码无效");
        }
        ObdBoxVO obdBoxVO = obdDeviceService.selectBaseDataByCode(code);
        if (obdBoxVO == null) {
            return AjaxResult.warn("该串码未能查询到对应数据");
        }
        return AjaxResult.success("200", "查询成功", obdBoxVO);
    }


    /**
     * 手机号解绑接口
     */
    @PostMapping("/unBind/{id}")
    @RepeatSubmit
    @ResponseBody
    public AjaxResult unBindPhone(@PathVariable("id") String id) {
        if (StringUtils.isBlank(id)) {
            return AjaxResult.warn("请求参数不正确");
        }
        String s;
        lock.lock();
        try {
            s = obdDeviceService.unBindPhone(id);
        } catch (Exception e) {
            return AjaxResult.error("解绑失败");
        } finally {
            lock.unlock();
        }
        return AjaxResult.success(s);
    }

    /**
     * 给前端页面传值
     */
    @GetMapping("/bindPhone/{id}")
    public String bindPhoneId(@PathVariable("id") String id, ModelMap mmap) {
        mmap.put("id", id);
        List<WxUser> wxUsers = new ArrayList<>();
        try {
            wxUsers = obdDeviceService.queryWechatInfo(null, null, id);
            mmap.put("jobNumber", wxUsers.get(0).getJobNumber());
            mmap.put("phone", wxUsers.get(0).getPhone());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prefix + "/bindPhone";
    }

    /**
     * 绑定新的手机号码
     */
    @PostMapping("/bindPhone/bind")
    @ResponseBody
    public AjaxResult bindPhoneList(String jobNumber, String phone, String newPhone) {
        if (StringUtils.isBlank(jobNumber) && "undefined".equals(jobNumber)) {
            return AjaxResult.warn("该员工信息有误，请联系管理员处理");
        }
        if (phone.equals(newPhone)) {
            return AjaxResult.warn("新的手机号码与原手机号码一致");
        }
        String regex = "^1[3-9]\\d{9}$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(newPhone).matches()) {
            return AjaxResult.warn("手机号格式不正确");
        }
        int phoneNumberExist = obdDeviceService.isPhoneNumberExist(newPhone);
        if (phoneNumberExist > 0) {
            return AjaxResult.warn("所填新手机号码已被使用");
        }
        int i = obdDeviceService.bindPhone(jobNumber, phone, newPhone);
        if (i == 0) {
            return AjaxResult.warn("绑定失败");
        }
        return AjaxResult.success("绑定成功");
    }

    /**
     * 条件查询微信用户信息
     */
    @PostMapping("/queryWechatInfo")
    @ResponseBody
    public TableDataInfo queryWechatInfo(String jobNumber, String phone, String id) {
        startPage();
        List<WxUser> wxUsers = obdDeviceService.queryWechatInfo(jobNumber, phone, id);
        return getDataTable(wxUsers);
    }

    /**
     * 根据机箱唯一ID查询端口列表
     */
    @PostMapping("/obd/list")
    @ResponseBody
    public TableDataInfo queryObdList(String id) {
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
     * 根据OBD自增ID查询备注对应信息
     */
    @GetMapping("/obd/obdRemarksById")
    @ResponseBody
    public AjaxResult obdRemarksById(String id) {
        ObdInfoVO obdInfoVO = obdDeviceService.ObdRemarksById(id);
        if (obdInfoVO == null || "".equals(obdInfoVO.getRemarks())) {
            return AjaxResult.error("无备注信息");
        }
        return AjaxResult.success(obdInfoVO);
    }


}
