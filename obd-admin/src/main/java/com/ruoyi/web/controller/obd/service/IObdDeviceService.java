package com.ruoyi.web.controller.obd.service;

import com.ruoyi.web.controller.data.domain.BaseDataVo;
import com.ruoyi.web.controller.data.domain.CheckState;
import com.ruoyi.web.controller.system.domain.WxUser;
import com.ruoyi.web.controller.upload.domain.ObdBoxVO;
import com.ruoyi.web.controller.upload.domain.ObdInfoVO;
import com.ruoyi.web.controller.upload.domain.ObdPortInfoVO;
import com.ruoyi.web.controller.upload.domain.ObdView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IObdDeviceService {

    /**
     * 批量修改OBD审核状态
     * @param checkStates
     * @return
     */
    int updateObdCheckStateData(List<CheckState> checkStates);

    /**
     * 批量修改机箱审核状态
     * @param ids
     * @return
     */
    int updateCheckState(String boxId,String ids,int state);

    /**
     * 根据boxCode机箱查询
     * */
    List<ObdBoxVO> boxSearch(String boxCode);

    /**
     * 机箱搜索通过机箱自增主键查询备注信息
     *
     * @param id 机箱码
     * @return ObdBoxVO
     */
    ObdBoxVO boxRemarksById(String id);

    /**
     * 机箱搜索通过机箱自增主键查询备注信息
     *
     * @param id 机箱码
     * @return ObdInfoVO
     */
    ObdInfoVO ObdRemarksById(String id);

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
     * 指定手机是否存在
     *
     * @return*/
    int isPhoneNumberExist(String newPhone);

    /**
     * 绑定手机号码
     *
     * @return*/
    int bindPhone(String jobNumber, String phone, String newPhone);

    /**
     * 解绑手机号码
     * */
    String unBindPhone(String jobNumber);

    /**
     * 查询微信用户相关信息
     */
    List<WxUser> queryWechatInfo(String jobNumber, String phone, String id);

    /**
     * 查询所有机箱信息
     * */
    List<ObdBoxVO> searchByCondition(String jobNumber, String phone, String code,String status);

    /**
     * 根据obdid查询端口信息
     * */
    List<ObdPortInfoVO> portByObdId(String obdId);


    /**
     * 根据boxid查询obd信息
     * */
    List<ObdInfoVO> infoByBoxId(String boxId);


    /**
     * 根据工号查询机箱信息
     * */
    List<ObdBoxVO> selectBoxByJobNumber(String  jobNumber);

    /**
     * 根据boxCode或者labelCode查询机箱信息
     * */
    ObdBoxVO selectAllInfoByCode(String code);

    /**
     * 根据boxCode或者labelCode查询导入基础数据
     * */
    ObdBoxVO selectBaseDataByCode(String code);

    /**
     * 导出list
     *
     * @param obdView obd视图
     * @return {@link List< ObdView >}
     */
    List<ObdView> selectExportObd(ObdView obdView);

    /**
     * 更新机箱备注信息
     *
     * @param id,remarks 备注信息
     * @return int
     */
    int updateRemakers(String id, String remarks);

    /**
     * 更新OBD备注信息
     *
     * @param id,remarks 备注信息
     * @return int
     */
    int updateObdRemakers(String id, String remarks);
}
