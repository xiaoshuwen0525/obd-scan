package com.ruoyi.web.controller.system.mapper;


import com.ruoyi.web.controller.system.domain.PhoneCode;
import com.ruoyi.web.controller.system.domain.WxUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 登陆 持久层
 *
 */
@Repository
public interface LoginMapper {

    public int insertOpenId(WxUser wxUser);

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
    public PhoneCode selectAuthCode();

    /**
     * 判断手机号
     * @return
     */
//    public List<WxUser> selectPhone();
    public WxUser selectPhone(String phone);

    /**
     * 解绑
     * @return
     */
    public int updateUnbind(@Param("openId") String openId, @Param("phone") String phone);

    public int unbind(String openId);
}
