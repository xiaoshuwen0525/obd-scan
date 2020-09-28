package com.ruoyi.system.service.impl;

import com.ruoyi.common.constant.Const;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.order.OrderNoUtils;
 import com.ruoyi.system.domain.SysDictData;
import com.ruoyi.system.domain.SysDictDataLoadingPerson;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.system.service.ISysDictDataLoadingPersonService;
import com.ruoyi.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 字典 业务层处理
 * 
 * @author ruoyi
 */
@Service
public class SysDictDataLoadingPersonServiceImpl implements ISysDictDataLoadingPersonService
{
    @Autowired
    private SysDictDataMapper dictDataMapper;

    private final String DICTTYPE ="const_loading_person";

    /**
     * 根据条件分页查询字典数据
     * 
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData)
    {
        return dictDataMapper.selectDictDataList(dictData);
    }

    /**
     * 根据字典类型查询字典数据
     * 
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataByType(String dictType)
    {
        return dictDataMapper.selectDictDataByType(dictType);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     * 
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue)
    {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息 多条数据
     *
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public List<SysDictData> selectDictLabelAll(String dictType, String dictValue)
    {
        return dictDataMapper.selectDictLabelAll(dictType, dictValue);
    }

    /**
     * 根据字典数据ID查询信息
     * 
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    @Override
    public SysDictData selectDictDataById(Long dictCode)
    {
        return dictDataMapper.selectDictDataById(dictCode);
    }

    /**
     * 通过字典ID删除字典数据信息
     * 
     * @param dictCode 字典数据ID
     * @return 结果
     */
    @Override
    public int deleteDictDataById(Long dictCode)
    {
        return dictDataMapper.deleteDictDataById(dictCode);
    }

    /**
     * 批量删除字典数据
     * 
     * @param ids 需要删除的数据
     * @return 结果
     */
    @Override
    public int deleteDictDataByIds(String ids)
    {
        return dictDataMapper.deleteDictDataByIds(Convert.toStrArray(ids));
    }

    /**
     * 新增保存字典数据信息
     * 
     * @param dictData 字典数据信息
     * @return 结果
     */
    @Override
    public int insertDictData(SysDictData dictData)
    {
        return dictDataMapper.insertDictData(dictData);
    }

    /**
     * 修改保存字典数据信息
     * 
     * @param dictData 字典数据信息
     * @return 结果
     */
    @Override
    public int updateDictData(SysDictData dictData)
    {
        return dictDataMapper.updateDictData(dictData);
    }

    @Override
    @Transient
    public String importLoadingPerson(List<SysDictDataLoadingPerson> loadingPersonList, SysUser sysUser, MultipartFile file) throws Exception{

        if (StringUtils.isNull(loadingPersonList) || loadingPersonList.size() == 0) {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        StringBuilder orederNumber = new StringBuilder();
        ArrayList<SysDictDataLoadingPerson> saveList = new ArrayList<SysDictDataLoadingPerson>();
        HashMap<String,String> validateBean = new HashMap();

        for (SysDictDataLoadingPerson bean : loadingPersonList) {
            try {
                StringBuffer msg = new StringBuffer();
                 //判断筛选条件
                if (bean.getDictValue()==null || bean.getDictValue().equals("")){
                    failureNum++;
                    msg.append("<br/>").append(failureNum).append("、揽装人键值").append(" 为NULL ");
                }
//
                if (bean.getDictLabel()==null || bean.getDictLabel().equals("")){
                    failureNum++;
                    msg.append("<br/>").append(failureNum).append("、揽装人名称:").append(" 为NULL ");
                }

                String validata =  validateBean.get(bean.getDictValue());

                if(validata != null) {
                    failureNum++;
                    msg.append("<br/>").append(failureNum).append("、揽装人名称:").append(bean.getDictLabel()+"" ).append("、揽装人键值:").append(bean.getDictValue()).append(",表格中揽装人键值已存在重复");

                }else{
                    String dictLabel =selectDictLabel(DICTTYPE,bean.getDictValue());

                    if(dictLabel!=null){
                        failureNum++;
                        msg.append("<br/>").append(failureNum).append("、揽装人名称:").append(bean.getDictLabel()+"" ).append("、揽装人键值:").append(bean.getDictValue()).append(",数据库中揽装人键值已存在重复");

                    }
                }

                validateBean.put(bean.getDictValue(),bean.getDictLabel());

                bean.setDictType(DICTTYPE);
                saveList.add(bean);
                successNum++;
                failureMsg.append(msg);
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、揽装人：" + bean.getDictValue() + " 未知错误";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条, 点击确定查看");
        }

        for (SysDictDataLoadingPerson data : saveList) {
            SysDictData dictData = new SysDictData();
            BeanUtils.copyBeanProp(dictData,data);
            dictDataMapper.insertDictData(dictData);
        }

        return successMsg.toString();
    }

}
