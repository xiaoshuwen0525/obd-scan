package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;

public class DictTypeExcels {

    /** 参数 key */
    private String dictKey;

    /** 参数 字段名称 */
    private String dictField;

    /** 参数 字典名称 */
    private String dictType;

    public DictTypeExcels(  String dictField, String dictType,String dictKey ) {
        this.dictKey = dictKey;
        this.dictField = dictField;
        this.dictType = dictType;
    }

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getDictField() {
        return dictField;
    }

    public void setDictField(String dictField) {
        this.dictField = dictField;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }
}
