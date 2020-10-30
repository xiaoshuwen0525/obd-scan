package com.ruoyi.web.controller.employee.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.controller.employee.domain.EmployeeUser;
import com.ruoyi.web.controller.employee.domain.ImportUser;

import java.util.List;
/**
 * 员工用户资料 服务层
 */
public interface IEmployeeService {
    /**
     * 查询
     * @param employeeUser
     * @return
     */
    List<EmployeeUser> selectEmployeeList(EmployeeUser employeeUser);

    /**
     * 根据id查询单个
     * @param id
     * @return
     */
    EmployeeUser selectByIdEmployee(Integer id);

    /**
     * 新增
     * @param employeeUser
     * @return
     */
    int insertEmployee(EmployeeUser employeeUser);

    /**
     * 修改
     * @param employeeUser
     * @return
     */
    int updateEmployee(EmployeeUser employeeUser);

    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteEmployee(List<Integer> ids);

    /**
     * 导入
     * @return
     */
    public AjaxResult importUser(List<ImportUser> userList);
}
