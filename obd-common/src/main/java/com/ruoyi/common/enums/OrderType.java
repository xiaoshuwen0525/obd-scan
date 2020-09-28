package com.ruoyi.common.enums;

/**
 * Created by LCX on 2020/3/22
 * 订单状态枚举
 */
public enum OrderType {
    /**
     * 人工操作标识
     */
    MANPOWER("人工操作"),
    /**
     * 机器人操作标识
     */
    ROBOT("机器人操作"),

    AUTO("自动分配");

    private final String info;

    private OrderType(String info)
    {
        this.info = info;
    }

    public String getInfo()
    {
        return info;
    }
}
