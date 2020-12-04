package com.ruoyi.web.controller.employee.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.controller.employee.domain.EmployeeUser;
import com.ruoyi.web.controller.employee.domain.ImportUser;
import com.ruoyi.web.controller.employee.mapper.EmployeeMapper;
import com.ruoyi.web.controller.employee.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 员工用户资料 服务层 实现
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 查询
     * @param employeeUser
     * @return
     */
    @Override
    public List<EmployeeUser> selectEmployeeList(EmployeeUser employeeUser) {
        return employeeMapper.selectEmployeeList(employeeUser);
    }

    /**
     * 根据id查询单个
     * @param id
     * @return
     */
    @Override
    public EmployeeUser selectByIdEmployee(Integer id) {
        return employeeMapper.selectByIdEmployee(id);
    }

    /**
     * 新增
     * @param employeeUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertEmployee(EmployeeUser employeeUser) {
        EmployeeUser employeeUser2 = employeeMapper.selectJobNumber(employeeUser.getJobNumber());
        if(employeeUser2!=null && employeeUser2.getJobNumber().equals(employeeUser.getJobNumber())){
            return 302;
        }
        EmployeeUser employeeUser3 = employeeMapper.selectPhone(employeeUser.getPhone());
        if(employeeUser3!=null && employeeUser3.getPhone().equals(employeeUser.getPhone())){
            return 303;
        }
        return employeeMapper.insertEmployee(employeeUser);
    }

    /**
     * 修改
     * @param employeeUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateEmployee(EmployeeUser employeeUser) {

        List<EmployeeUser> employeeUsers = employeeMapper.selectEmployee();
        for (EmployeeUser user : employeeUsers) {
            if(StringUtils.isEmpty(employeeUser.getUserName())){
                return 400;
            }else if(StringUtils.isEmpty(employeeUser.getJobNumber())){
                return 401;
            }else if(employeeUser.getJobNumber().equals(user.getJobNumber()) &&
                    !employeeUser.getId().equals(user.getId())){
                return 302;
            }else if(employeeUser.getPhone().equals(user.getPhone()) &&
                    !employeeUser.getId().equals(user.getId())){
                return 303;
            }

        }
        return employeeMapper.updateEmployee(employeeUser);
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteEmployee(List<Integer> ids) {

        int i = 0;
        try {
            i = employeeMapper.deleteEmployee(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;

    }

    /**
     * 导入
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult importUser(List<ImportUser> userList) {

        try {
            if (userList != null && userList.size() != 0) {
                for (ImportUser importUser : userList) {
                    EmployeeUser employeeUser = new EmployeeUser();
                    employeeUser.setJobNumber(importUser.getJobNumber());
                    employeeUser.setUserName(importUser.getUserName());
                    employeeUser.setPhone(importUser.getPhone());

                    EmployeeUser employeeUser2 = employeeMapper.selectJobNumber(importUser.getJobNumber());
                    if(employeeUser2!=null && employeeUser2.getJobNumber().equals(importUser.getJobNumber())){
                        continue;
                    }
                    EmployeeUser employeeUser3 = employeeMapper.selectPhone(importUser.getPhone());
                    if(employeeUser3!=null && employeeUser3.getPhone().equals(importUser.getPhone())){
                        continue;
                    }
                    employeeMapper.importUser(employeeUser);
                }
                return AjaxResult.success("导入成功");

            } else {
                return AjaxResult.warn("excel表格为空");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error("导入失败");
    }
}
