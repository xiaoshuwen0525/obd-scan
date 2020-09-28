package com.ruoyi.framework.web.service;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.constant.EnteringTypeConst;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysDictData;
import com.ruoyi.system.service.ISysDictDataService;

/**
 * RuoYi首创 html调用 thymeleaf 实现字典读取
 *
 * @author ruoyi
 */
@Service("dict")
public class DictService
{
    @Autowired
    private ISysDictDataService dictDataService;
    @Autowired
    private ISysUserService sysUserService;

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType 字典类型
     * @return 参数键值
     */
    public List<SysDictData> getType(String dictType)
    {
        return dictDataService.selectDictDataByType(dictType);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    public String getLabel(String dictType, String dictValue)
    {
        return dictDataService.selectDictLabel(dictType, dictValue);
    }


    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    public List<SysDictData> getLabelAll(String dictType, String dictValue)
    {
        return dictDataService.selectDictLabelAll(dictType, dictValue);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     * 查所有用户
     * @param
     * @param
     * @return 字典标签
     */
    public List<SysUser> getUserList()
    {
        return sysUserService.acquireIdAndName(new SysUser());
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     * 根据部门id查所有用户
     * @param
     * @param
     * @return 字典标签
     */
    public List<SysUser> getUserList(Long dept)
    {
        SysUser sysUser = new SysUser();
        sysUser.setDeptId(dept);
        return sysUserService.acquireIdAndName(sysUser);
    }


    public List<SysUser> getEnteringUserList(String enteringType)
    {

        List<SysUser> sysUserList = new ArrayList<>();

        if(enteringType.equals(EnteringTypeConst.ALLEENTERINGDEPT)){

            List<SysDictData>  dictDeptList =  dictDataService.selectDictDataByType("other_order_entering_dept");
            dictDeptList.forEach(bean->{
                String deptId =  sysUserService.selectDeptChild(Long.valueOf(bean.getDictValue()));
                SysUser sysUser = new SysUser();
               // sysUser.setDeptId(Long.valueOf(bean.getDictValue()));
                sysUser.setDeptChild(deptId);
                sysUserList.addAll(sysUserService.selectIdAndNameByChildDeptId(sysUser));
            });

        }else if (enteringType.equals(EnteringTypeConst.SINGLEENTERINGDEPT)){


            SysUser sysUser = new SysUser();
            sysUser.setDeptId(ShiroUtils.getSysUser().getDeptId());
            sysUserList.addAll(sysUserService.acquireIdAndName(sysUser));
        }


        return sysUserList;
    }



}
