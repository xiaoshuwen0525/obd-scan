package com.ruoyi.common.annotation;

import com.ruoyi.common.enums.OrderModuleType;

import java.lang.annotation.*;

/**
 * 修改订单状态产生的日志
 *
 * @author lcx
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoAllotOrder
{
    /**
     * 功能
     */
    public OrderModuleType businessType() default OrderModuleType.DdRenewalOrder;

}
