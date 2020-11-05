package com.ruoyi.web.controller.employee.mapper;

import com.ruoyi.web.controller.employee.domain.EmployeeUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工用户资料 持久层
 */
public interface EmployeeMapper {
    /**
     * 查询
     * @param employeeUser
     * @return
     */
    List<EmployeeUser> selectEmployeeList(EmployeeUser employeeUser);

    List<EmployeeUser> selectEmployee();

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
    int importUser(List<EmployeeUser> employeeUsers);

    EmployeeUser selectUserName(String userName);
    EmployeeUser selectJobNumber(String jobNumber);
    EmployeeUser selectPhone(String phone);
}
