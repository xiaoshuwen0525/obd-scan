package com.ruoyi.web.controller.employee.controller;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.web.controller.data.domain.DerivedEntity;
import com.ruoyi.web.controller.data.domain.ImportEntity;
import com.ruoyi.web.controller.employee.domain.EmployeeUser;
import com.ruoyi.web.controller.employee.domain.ImportUser;
import com.ruoyi.web.controller.employee.service.IEmployeeService;
import com.ruoyi.web.controller.system.domain.WxUser;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 员工用户资料 控制层
 */
@Controller
@RequestMapping("/device/employee")
public class EmployeeController extends BaseController {
    @Autowired
    private IEmployeeService employeeService;

    private String prefix = "device/employee";

    /**
     * 跳转员工用户页面
     */
    @GetMapping()
    public String employeeUser() {
        return prefix + "/employeeUser";
    }
    @GetMapping("addEmployee")
    public String addEmployee() {
        return prefix + "/addEmployee";
    }

    @GetMapping("editEmployee/{id}")
    public String editEmployee(@PathVariable("id") int id, ModelMap mmap) {
        EmployeeUser employeeUser = employeeService.selectByIdEmployee(id);
        employeeUser.setId(id);
        mmap.put("employee",employeeUser);
        return prefix + "/editEmployee";
    }

    /**
     * 查询员工用户
     * @param employeeUser
     * @return
     */
    @PostMapping("/selectEmployeeList")
    @ResponseBody
    public TableDataInfo selectEmployeeList(EmployeeUser employeeUser){

        startPage();
        List<EmployeeUser> employeeUsers = employeeService.selectEmployeeList(employeeUser);
        return getDataTable(employeeUsers);
    }

    /**
     * 新增员工用户
     * @param employeeUser
     * @return
     */
    @PostMapping("/insertEmployee")
    @ResponseBody
    public AjaxResult insertEmployee(EmployeeUser employeeUser){
        if(employeeUser.getPhone().length() != 11){
            return AjaxResult.warn("手机号格式不正确");
        }
        int i;
        try {
            i = employeeService.insertEmployee(employeeUser);
            if (i == 302) {
                return AjaxResult.warn("工号已存在");
            }else if (i == 303) {
                return AjaxResult.warn("手机号已存在");
            }
            if (i > 0) {
                return AjaxResult.success("新增成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error("新增失败");
    }

    /**
     * 修改员工用户
     * @param employeeUser
     * @return
     */
    @PostMapping("/updateEmployee")
    @ResponseBody
    public AjaxResult updateEmployee(EmployeeUser employeeUser){
        if(employeeUser.getPhone().length() != 11){
            return AjaxResult.warn("手机号格式不正确");
        }

        int i;
        try {
            i = employeeService.updateEmployee(employeeUser);
            if (i == 302) {
                return AjaxResult.warn("工号已存在");
            }else if (i == 303) {
                return AjaxResult.warn("手机号已存在");
            }else if(i == 400){
                return AjaxResult.warn("用户名不能为空");
            }else if(i == 401){
                return AjaxResult.warn("工号不能为空");
            }
            if (i > 0) {
                return AjaxResult.success("更新成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error("更新失败");

    }

    /**
     * 删除员工用户
     * @param ids
     * @return
     */
    @PostMapping("/deleteEmployee")
    @ResponseBody
    public AjaxResult deleteEmployee(@RequestParam(value = "ids",required = false) Integer[] ids){
        List<Integer> idList = Arrays.asList(ids);

        int i;
        try {
            i = employeeService.deleteEmployee(idList);
            if (i > 0) {
                return AjaxResult.success("删除成功");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return AjaxResult.error("删除失败");
    }

    /**
     * 导入用户
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/importUser")
    @ResponseBody
    public AjaxResult importUser(MultipartFile file) throws Exception {
        ExcelUtil<ImportUser> util = new ExcelUtil<ImportUser>(ImportUser.class);
        List<ImportUser> userList = util.importExcel(file.getInputStream());
        return employeeService.importUser(userList);

    }

    /**
     * 导出数据
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(EmployeeUser employeeUser) {
        List<EmployeeUser> employeeUsers = employeeService.selectEmployeeList(employeeUser);
        ExcelUtil<EmployeeUser> util = new ExcelUtil<EmployeeUser>(EmployeeUser.class);
        return util.exportExcel(employeeUsers, "一线员工资料管理");
    }
}
