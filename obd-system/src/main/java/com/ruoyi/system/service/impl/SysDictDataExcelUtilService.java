package com.ruoyi.system.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.constant.DictTypeExcelsConst;
import com.ruoyi.system.domain.DictTypeExcels;
import com.ruoyi.system.domain.SysDictData;
import com.ruoyi.system.service.ISysDictDataService;

/**
 *
 * 
 *
 */
@Service
public class SysDictDataExcelUtilService<T>
{

    @Autowired
    private ISysDictDataService sysDictDataService;

    /**
     * 实体对象
     */
    public Class<T> clazz;

    /**
     * 导入导出数据列表
     */
    private List<T> list;

    /**
     * 注解列表
     */
    private HashMap<Object,Field> fields;



    /**
     *  字典数组
     */
    private ArrayList<DictTypeExcels> dictTypeExcelsList;

    public void inin(Class<T> clazz,List<T> list,ArrayList<DictTypeExcels> dictTypeExcelsList){

        this.clazz = clazz;

        this.list = list;

        this.fields = new HashMap<Object,Field>();

        this.dictTypeExcelsList = dictTypeExcelsList;


        List<Field> tempFields = new ArrayList<>();
        tempFields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        tempFields.addAll(Arrays.asList(clazz.getDeclaredFields()));

        for (Field field : tempFields)
        {
            if (field.isAnnotationPresent(Excel.class))
            {
                this.fields.put(field.getName(), field );
            }
        }
        selectdict();

    }



    /**
     * 查询其他订单列表
     *
     * @param
     * @return 其他订单
     */
    public void selectdict()  {

        HashMap<Object,HashMap> selectDictMap = new HashMap<Object,HashMap>();

        try{

            dictTypeExcelsList.forEach((bean)->{


                Object dictType =  bean.getDictType();
                if(dictType == null){
                    return;
                }

                Field field= fields.get(bean.getDictField());
                if(field == null){
                    return;
                }

                selectdict(selectDictMap,bean);

            });


            for (int i = 0; i < list.size(); i++)
            {
                T vo = (T) list.get(i);

                dictTypeExcelsList.forEach((bean)->{

                    try {

                        Field field = fields.get(bean.getDictField());

                        field.setAccessible(true);

                        HashMap<Object,Object> dictMap = selectDictMap.get(bean.getDictField());

                        Object o = field.get(vo);

                        field.set(vo,dictMap.get(o));


                    } catch (IllegalAccessException e) {
                            e.printStackTrace();
                    }

                });


            }

        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    /**
     * 查询其他订单列表
     *
     * @param
     * @return 其他订单
     */
    public void selectdict(HashMap<Object,HashMap> map,DictTypeExcels dictTypeExcels)
    {

        String key = dictTypeExcels.getDictKey();

        if(key.equals(DictTypeExcelsConst.DICTVALUE )){
            HashMap dictMap = encasementDictMapValue(String.valueOf(dictTypeExcels.getDictType()));
            map.put(dictTypeExcels.getDictField(),dictMap);
        }

        if(key.equals(DictTypeExcelsConst.DICTCODE)){
            HashMap dictMap = encasementDictMapCode(String.valueOf(dictTypeExcels.getDictType()));

            map.put(dictTypeExcels.getDictField(),dictMap);
        }




    }


    /*
     * 1.要求 DictValue() 必须唯一
     * 2.根据dictType 查出对应字典数据。装map
     *
     */
    public HashMap  encasementDictMapValue(String dictType){

        List<SysDictData>  dictList =  sysDictDataService.selectDictDataByType(dictType);
        HashMap map= new HashMap();
        dictList.forEach(data->{
            map.put(data.getDictValue(),data.getDictLabel());
        });
        return map;

    }

    /*
     * 1.要求 DictValue() 必须唯一
     * 2.根据dictType 查出对应字典数据。装map
     *
     */
    public HashMap  encasementDictMapCode(String dictType){

        List<SysDictData>  dictList =  sysDictDataService.selectDictDataByType(dictType);
        HashMap map= new HashMap();
        dictList.forEach(data->{
            map.put(String.valueOf(data.getDictCode()),data.getDictLabel());
        });
        return map;

    }



}
