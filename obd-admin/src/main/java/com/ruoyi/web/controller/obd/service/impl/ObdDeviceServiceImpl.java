package com.ruoyi.web.controller.obd.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.web.controller.data.domain.BaseDataVo;
import com.ruoyi.web.controller.data.domain.BaseUpdate;
import com.ruoyi.web.controller.data.domain.CheckState;
import com.ruoyi.web.controller.data.domain.DerivedEntity;
import com.ruoyi.web.controller.obd.mapper.ObdDeviceMapper;
import com.ruoyi.web.controller.obd.service.IObdDeviceService;
import com.ruoyi.web.controller.system.domain.WxUser;
import com.ruoyi.web.controller.upload.domain.ObdBoxVO;
import com.ruoyi.web.controller.upload.domain.ObdInfoVO;
import com.ruoyi.web.controller.upload.domain.ObdPortInfoVO;
import com.ruoyi.web.controller.upload.domain.ObdView;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * obd设备服务impl
 *
 * @author Administrator
 * @author: 曾志伟
 * @date: 2020-10-17 12:13
 * @date 2020/10/22
 */
@Service
public class ObdDeviceServiceImpl implements IObdDeviceService {

    @Autowired
    private ObdDeviceMapper obdDeviceMapper;

    private static final Lock lock = new ReentrantLock();

    /**
     * 批量修改审核状态
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCheckState(String boxId, String ids, int state) {
        Long[] obdIds = Convert.toLongArray(ids);
        if (obdIds.length == 0) {
            return 0;
        }
        int i = obdDeviceMapper.updateCheckState(obdIds, state);
        int j = updateBoxState(boxId);
        return i + j;
    }

    public int updateBoxState(String boxId) {
        List<ObdInfoVO> obdInfoVOS = obdDeviceMapper.selectInfoByBoxId(boxId);
        if (CollectionUtils.isEmpty(obdInfoVOS)) {
            return 0;
        }
        int count = obdInfoVOS.size();
        int yes = 0;
        for (ObdInfoVO obdInfoVO : obdInfoVOS) {
            if (("1").equals(obdInfoVO.getCheckState())) {
                yes += 1;
            }
        }
        int state = 0;
        //全部合格
        if (yes == count) {
            state = 1;
        }
        return obdDeviceMapper.updateBoxCheckState(boxId, state);
    }

    /**
     * 机箱搜索通过boxCode
     *
     * @param boxCode 机箱码
     * @return {@link List<ObdBoxVO>}
     */
    @Override
    public List<ObdBoxVO> boxSearch(String boxCode) {
        if (StringUtils.isNotBlank(boxCode)) {
            return obdDeviceMapper.boxSearch(boxCode);
        }
        return null;
    }

    /**
     * 机箱搜索通过机箱自增主键查询备注信息
     *
     * @param id 机箱码
     * @return {@link List<ObdBoxVO>}
     */
    @Override
    public ObdBoxVO boxRemarksById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return obdDeviceMapper.boxRemarksById(id);
        }
        return null;
    }

    /**
     * OBD搜索通过OBD自增主键查询备注信息
     *
     * @param id 机箱码
     * @return {@link List<ObdInfoVO>}
     */
    @Override
    public ObdInfoVO ObdRemarksById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return obdDeviceMapper.ObdRemarksById(id);
        }
        return null;
    }

    /**
     * 员工号搜索
     *
     * @param jobNumber 员工编号
     * @return {@link List<ObdBoxVO>}
     */
    @Override
    public List<ObdBoxVO> jobNumberSearch(String jobNumber) {
        if (StringUtils.isNotBlank(jobNumber)) {
            return obdDeviceMapper.jobNumberSearch(jobNumber);
        }
        return null;
    }

    /**
     * 手机搜索
     *
     * @param phone 电话
     * @return {@link List<ObdBoxVO>}
     */
    @Override
    public List<ObdBoxVO> phoneSearch(String phone) {
        if (StringUtils.isNotBlank(phone)) {
            return obdDeviceMapper.phoneSearch(phone);
        }
        return null;
    }

    /**
     * 所有机箱信息搜索
     *
     * @return {@link List<ObdBoxVO>}
     */
    @Override
    public List<ObdBoxVO> allSearch() {
        return obdDeviceMapper.allSearch();
    }

    /**
     * 电话号码是否存在
     *
     * @param newPhone 新电话
     * @return int
     */
    @Override
    public int isPhoneNumberExist(String newPhone) {
        //核实前新手机号码是否存在
        WxUser wxUser = obdDeviceMapper.isPhoneNumberExist(newPhone);
        if (wxUser != null) {
            return 1;
        }
        return 0;
    }

    /**
     * 绑定手机
     *
     * @param jobNumber 员工编号
     * @param phone     电话
     * @param newPhone  新电话
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int bindPhone(String jobNumber, String phone, String newPhone) {
        //绑定新手机号码
        return obdDeviceMapper.bindPhone(jobNumber, phone, newPhone);
    }

    /**
     * 解绑手机
     *
     * @param id id
     * @return {@link String}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String unBindPhone(String id) {
        int i = 0;
        //解绑手机号
        try {
            i = obdDeviceMapper.unBindPhone(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i != 0 ? "解绑成功" : "解绑失败";
    }

    /**
     * 微信信息查询
     *
     * @param jobNumber 员工编号
     * @param phone     电话
     * @param id        id
     * @return {@link List<WxUser>}
     */
    @Override
    public List<WxUser> queryWechatInfo(String jobNumber, String phone, String id) {
        WxUser wxUser = new WxUser();
        if (StringUtils.isNotBlank(jobNumber)) {
            wxUser.setJobNumber(jobNumber);
        }
        if (StringUtils.isNotBlank(phone)) {
            wxUser.setPhone(phone);
        }
        if (StringUtils.isNotBlank(id)) {
            wxUser.setId(Integer.parseInt(id));
        }
        List<WxUser> wxUsers = new ArrayList<>();
        //查询微信用户信息
        try {
            wxUsers = obdDeviceMapper.queryWechatInfo(wxUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wxUsers;
    }

    /**
     * 搜索条件
     *
     * @param jobNumber  员工编号
     * @param phone      电话
     * @param code       串码
     * @param checkState 状态
     * @return {@link List<ObdBoxVO>}
     */
    @Override
    public List<ObdBoxVO> searchByCondition(String jobNumber, String phone, String code, String checkState) {
        //查询机箱信息
        List<ObdBoxVO> obdBoxVOS = new ArrayList<>();
        try {
            obdBoxVOS = obdDeviceMapper.searchByCondition(jobNumber, phone, code, checkState);
        } catch (Exception e) {
            return obdBoxVOS;
        }
        for (ObdBoxVO obdBox : obdBoxVOS) {
            if ("1".equals(obdBox.getCheckState())) {
                obdBox.setCheckState("合格");
            } else if ("0".equals(obdBox.getCheckState())) {
                obdBox.setCheckState("不合格");
            } else {
                obdBox.setCheckState("-");
            }
        }
        ////根据状态码转换为字符
        //for (ObdBoxVO obdBox : obdBoxVOS) {
        //    if ("0".equals(obdBox.getStatus())) {
        //        if ("0".equals(obdBox.getExceptionType())) {
        //            obdBox.setStatus("正常");
        //            obdBox.setExceptionType("正常");
        //        } else if ("1".equals(obdBox.getExceptionType())) {
        //            obdBox.setStatus("异常");
        //            obdBox.setExceptionType("OBD异常");
        //        } else if ("2".equals(obdBox.getExceptionType())) {
        //            obdBox.setStatus("异常");
        //            obdBox.setExceptionType("端口异常");
        //        }
        //    } else {
        //        obdBox.setStatus("异常");
        //        obdBox.setExceptionType("机箱异常");
        //    }
        //}
        return obdBoxVOS;
    }

    /**
     * 机箱信息通过盒子id
     *
     * @param boxId 框标识
     * @return {@link List<ObdInfoVO>}
     */
    @Override
    public List<ObdInfoVO> infoByBoxId(String boxId) {
        List<ObdInfoVO> list = obdDeviceMapper.selectInfoByBoxId(boxId);
        for (ObdInfoVO obdInfoVO : list) {
            if ("1".equals(obdInfoVO.getCheckState())) {
                obdInfoVO.setCheckState("合格");
            } else if ("0".equals(obdInfoVO.getCheckState())) {
                obdInfoVO.setCheckState("不合格");
            } else {
                obdInfoVO.setCheckState("-");
            }
        }
        return list;
    }

    /**
     * 根据扫描识别出来的code进行查询基础信息
     *
     * @param code 识别码
     * @return ObdBoxVO
     */
    @Override
    public ObdBoxVO selectAllInfoByCode(String code) {
        ObdBoxVO obdBoxVO = new ObdBoxVO();
        if (StringUtils.isEmpty(code)) {
            return obdBoxVO;
        }
        List<ObdInfoVO> obdInfoVOS = new ArrayList<>();
        List<ObdPortInfoVO> obdPortInfoVOS;
        obdPortInfoVOS = obdDeviceMapper.selectAllInfoByCode(code);
        if (obdPortInfoVOS == null || obdPortInfoVOS.size() == 0) {
            return null;
        }
        HashSet<String> obd = new HashSet<>();
        for (ObdPortInfoVO obdPortInfoVO : obdPortInfoVOS) {
            if (obdPortInfoVO.getId() != 0) {
                //这里的id取值为机箱的唯一id
                obdBoxVO.setId(obdPortInfoVO.getId());
            }
            obd.add(obdPortInfoVO.getObdId().toString());
        }
        for (String s : obd) {
            List<ObdPortInfoVO> obdPortInfoVOS1 = new ArrayList<>();
            for (ObdPortInfoVO obdPortInfoVO : obdPortInfoVOS) {
                if (s.equals(obdPortInfoVO.getObdId().toString())) {
                    ObdPortInfoVO obdPortInfoVO1 = new ObdPortInfoVO();
                    //这里的seq取值为port唯一id
                    obdPortInfoVO1.setSeq(obdPortInfoVO.getSeq());
                    obdPortInfoVO1.setPortSer(obdPortInfoVO.getPortSer());
                    obdPortInfoVO1.setPortCode(obdPortInfoVO.getPortCode());
                    obdPortInfoVOS1.add(obdPortInfoVO1);
                }
            }
            ObdInfoVO obdInfoVO = new ObdInfoVO();
            obdInfoVO.setId(Integer.parseInt(s));
            obdInfoVO.setObdPortInfoVOList(obdPortInfoVOS1);
            obdInfoVOS.add(obdInfoVO);
        }
        obdBoxVO.setObdInfoVOList(obdInfoVOS);
        return obdBoxVO;
    }

    /**
     * 根据扫描识别出来的code进行判定并查询机箱以及其所属信息--微信前端扫描用
     *
     * @param code 识别码
     * @return ObdBoxVO
     */
    @Override
    public ObdBoxVO selectBaseDataByCode(String code) {
        ObdBoxVO obdBoxVO = new ObdBoxVO();
        if (StringUtils.isEmpty(code)) {
            return obdBoxVO;
        }
        List<ObdInfoVO> obdInfoVOS = new ArrayList<>();
        List<DerivedEntity> derivedEntities;
        derivedEntities = obdDeviceMapper.selectBaseDataByCode(code);
        if (derivedEntities == null || derivedEntities.size() == 0) {
            return null;
        }
        for (DerivedEntity derivedEntitie : derivedEntities) {
            if (derivedEntitie.getBoxId() != 0) {
                //这里的id取值为机箱的唯一id
                obdBoxVO.setId(derivedEntitie.getBoxId());
                obdBoxVO.setBoxUniqueId(derivedEntitie.getBoxUniqueId());
            }
            //此处用于添加obd相关属性
            ObdInfoVO obdInfoVO = new ObdInfoVO();
            obdInfoVO.setId(derivedEntitie.getObdId());
            obdInfoVO.setPortCount(derivedEntitie.getPortCount());
            obdInfoVO.setBoxBelong(derivedEntitie.getBoxBelong());
            obdInfoVO.setObdName(derivedEntitie.getObdName());
            obdInfoVO.setObdUniqueId(derivedEntitie.getObdUniqueId());
            obdInfoVO.setBoxUniqueId(derivedEntitie.getBoxUniqueId());
            obdInfoVOS.add(obdInfoVO);
            List<ObdPortInfoVO> obdPortInfoVOS1 = new ArrayList<>();
            int portCount = derivedEntitie.getPortCount();
            if (portCount != 0) {
                for (int i = 1; i <= portCount; i++) {
                    //此处用于添加端口相关属性
                    ObdPortInfoVO obdPortInfoVO1 = new ObdPortInfoVO();
                    obdPortInfoVO1.setPortSer(i);
                    //特殊需求置空
                    obdPortInfoVO1.setPortCode("");
                    obdPortInfoVOS1.add(obdPortInfoVO1);
                }
            } else {
                obdPortInfoVOS1.add(null);
            }
            obdInfoVO.setObdPortInfoVOList(obdPortInfoVOS1);
        }
        obdBoxVO.setObdInfoVOList(obdInfoVOS);
        return obdBoxVO;
    }

    @Override
    public List<ObdView> selectExportObd(ObdView obdView) {
        List<ObdView> obdViews = new ArrayList<>();
        try {
            obdViews = obdDeviceMapper.selectExportObd(obdView);
            for (ObdView obd : obdViews) {
                if ("1".equals(obd.getCheckState())) {
                    obd.setCheckState("合格");
                } else if ("0".equals(obd.getCheckState())) {
                    obd.setCheckState("不合格");
                } else {
                    obd.setCheckState("-");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obdViews;
    }

    /**
     * 更新机箱备注信息
     *
     * @param id,remarks 备注信息
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRemakers(String id, String remarks) {
        return obdDeviceMapper.updateRemarks(id, remarks);
    }


    /**
     * 更新OBD备注信息
     *
     * @param id,remarks 备注信息
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateObdRemakers(String id, String remarks) {
        return obdDeviceMapper.updateObdRemakers(id, remarks);
    }

    /**
     * 端口信息通过obd id
     *
     * @param obdId obd id
     * @return {@link List<ObdPortInfoVO>}
     */
    @Override
    public List<ObdPortInfoVO> portByObdId(String obdId) {
        return obdDeviceMapper.selectPortByObdId(obdId);
    }

    /**
     * 选择机箱通过员工工号
     *
     * @param jobNumber 工作数量
     * @return {@link List<ObdBoxVO>}
     */
    @Override
    public List<ObdBoxVO> selectBoxByJobNumber(String jobNumber) {
        return obdDeviceMapper.selectBoxByJobNumber(jobNumber);
    }

    /**
     * 批量更新审核状态
     *
     * @param checkStates 审核状态
     * @return int
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateObdCheckStateData(List<CheckState> checkStates) {
        if (CollectionUtils.isEmpty(checkStates)) {
            return 0;
        }
        //整理有效数据
        List<CheckState> checkStateList = new ArrayList<>();
        ArrayList<String> boxIds = new ArrayList<>();
        for (CheckState checkState : checkStates) {
            if (StringUtils.isNotBlank(checkState.getObdUniqueId())) {
                if ("合格".equals(checkState.getCheckState())) {
                    checkState.setCheckState("1");
                    checkState.setRemarks("");
                } else if ("不合格".equals(checkState.getCheckState())) {
                    checkState.setCheckState("0");
                } else {
                    checkState.setCheckState(null);
                }
                boxIds.add(checkState.getObdUniqueId());
                checkStateList.add(checkState);
            }
        }
        lock.lock();
        try {
            obdDeviceMapper.updateObdCheckStateData(checkStateList);
            List<String> boxIdList = obdDeviceMapper.selectBoxIdByObdUniqueId(boxIds);
            if (CollectionUtils.isEmpty(boxIdList)) {
                return 0;
            }
            for (String boxID : boxIdList) {
                updateBoxState(boxID);
            }
        } finally {
            lock.unlock();
        }
        return 1;
    }

}
