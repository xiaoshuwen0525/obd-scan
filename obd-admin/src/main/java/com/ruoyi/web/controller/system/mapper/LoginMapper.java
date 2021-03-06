package com.ruoyi.web.controller.system.mapper;


import com.ruoyi.web.controller.employee.domain.EmployeeUser;
import com.ruoyi.web.controller.system.domain.PhoneCode;
import com.ruoyi.web.controller.system.domain.WxUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 登陆 持久层
 *
 */
@Repository
public interface LoginMapper {



    /**
     * 查询openid是否存在
     * @param openId
     * @return
     */
    public WxUser selectOpenId(String openId);

    /**
     * 添加用户
     * @param
     * @return
     */
    public int insertUser(WxUser wxUser);

    /**
     * 添加手机号  验证码
     * @return
     */
    public int insertPhoneAuthCode(PhoneCode phoneCode);

    /**
     * 判断验证码
     * @param
     * @return
     */
    public PhoneCode selectAuthCode(String phone);

    /**
     * 判断手机号
     * @return
     */
    public WxUser selectPhone(String phone);

    /**
     * 判断工号
     * @return
     */
    public WxUser selectJobNumber(String jobNumber);


    public int updateBinding(WxUser wxUser);


    EmployeeUser selectEmployee(@Param("jobNumber") String jobNumber,@Param("phone") String phone);


}
