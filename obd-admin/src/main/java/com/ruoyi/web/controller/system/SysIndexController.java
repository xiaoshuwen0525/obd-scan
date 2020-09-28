package com.ruoyi.web.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruoyi.common.config.Global;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysMenu;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysMenuService;
import com.ruoyi.system.service.ISysNoticeService;
import com.ruoyi.system.service.ISysUserService;

/**
 * 首页 业务处理
 *
 * @author ruoyi
 */
@Controller
public class SysIndexController extends BaseController {
    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysNoticeService noticeService;

    @Autowired
    private ISysDeptService deptService;


    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap) {
        // 取身份信息
        SysUser user = ShiroUtils.getSysUser();
        // 根据用户id取出菜单
        List<SysMenu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", Global.getCopyrightYear());
        mmap.put("demoEnabled", Global.isDemoEnabled());
        return "index";
    }

    // 切换主题
    @GetMapping("/system/switchSkin")
    public String switchSkin(ModelMap mmap) {
        return "skin";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap) {
        mmap.put("version", Global.getVersion());
        return "main";
    }


    @PostMapping("/sysUser/autoOrder")
    @ResponseBody
    public SysUser acquireSysUser() {
        return ShiroUtils.getSysUser();
    }

    @PostMapping("/system/notice/showList")
    @ResponseBody
    public List<SysNotice> showList(SysNotice notice) {
        return noticeService.selectNoticeList(notice);
    }

    @GetMapping("/system/notice/checkNotice/{noticeId}")
    public String checkNotice(@PathVariable("noticeId") Long noticeId, ModelMap mmap) {
        SysNotice sysNotice = noticeService.selectNoticeById(noticeId);
        mmap.put("notice", sysNotice);
        return "system/notice/singleNotice";
    }

    /**
     * 根据用户id更改“自动接单”数据
     *
     * @param userId
     * @return
     */
    @PostMapping("/user/info/autoOrder")
    @ResponseBody
    public AjaxResult updateAutoOrder(String userId, String sign) {
        return toAjax(sysUserService.updateAutoOrderStatus(userId, sign));
    }

    /**
     * 获取 部门 祖级列表
     *
     * @return
     */
    @PostMapping("/acqAncestors")
    @ResponseBody
    public SysDept acqAncestors() {
        SysUser sysUser = ShiroUtils.getSysUser();
        return deptService.selectDeptById(sysUser.getDeptId());
    }

}
