package com.ruoyi.web.controller.obd.mapper;

import com.ruoyi.web.controller.data.domain.DerivedEntity;
import com.ruoyi.web.controller.system.domain.WxUser;
import com.ruoyi.web.controller.upload.domain.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObdDeviceMapper {

    /**
    * 根据boxCode机箱查询
    * */
    List<ObdBoxVO> boxSearch(String boxCode);

    /**
    * 根据boxCode机箱查询备注信息
    * */
    ObdBoxVO boxRemarksById(String id);

    /**
     * 根据jobNumber机箱查询
     * */
    List<ObdBoxVO> jobNumberSearch(String jobNumber);

    /**
     * 根据phone机箱查询
     * */
    List<ObdBoxVO> phoneSearch(String phone);

    /**
     * 查询所有机箱信息
     * */
    List<ObdBoxVO> allSearch();

    /**
     * 查询指定手机号是否存在
     * */
    WxUser isPhoneNumberExist(String newPhone);

    /**
     * 绑定手机号码
     * */
    int bindPhone(@Param("jobNumber") String jobNumber, @Param("phone")String phone, @Param("newPhone")String newPhone);

    /**
     * 更新机箱备注信息
     *
     * @param id,remarks 备注信息
     * @return int
     */
    int updateRemarks(@Param("id") String id, @Param("remarks") String remarks);

    /**
     * 解绑手机号码
     * */
    int unBindPhone(@Param("id") String id);

    /**
     * 查询所有机箱信息
     */
    List<ObdBoxVO> searchByCondition(@Param("jobNumber") String jobNumber, @Param("phone")String phone, @Param("code")String code,  @Param("checkState")String checkState);

    /**
     * 查询微信用户相关信息
     */
    List<WxUser> queryWechatInfo(WxUser wxUser);

    /**
     * 根据盒子唯一ID查询obd
     */
    List<ObdInfoVO> selectInfoByBoxId(@Param("boxId") String  boxId);

    /**
     * 根据obdId查询端口
     */
    List<ObdPortInfoVO> selectPortByObdId(@Param("obdId") String  obdId);

    /**
     * 根据工号查询机箱信息
     * */
    List<ObdBoxVO> selectBoxByJobNumber(@Param("jobNumber") String  jobNumber);

    /**
     * 根据boxCode或者labelCode查询机箱信息
     * */
    List<ObdPortInfoVO> selectAllInfoByCode(@Param("code") String code);

    /**
     * 根据boxCode或者labelCode查询基础信息
     * */
    List<DerivedEntity> selectBaseDataByCode(@Param("code") String code);

    /**
     * 选择export obd
     *
     * @param obdView obd视图
     * @return {@link List<ObdView>}
     */
    List<ObdView> selectExportObd(ObdView obdView);
}
