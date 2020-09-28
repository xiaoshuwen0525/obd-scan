package com.ruoyi.common.constant;

/**
 * @Author lcx
 * @Date 2017/5/24 16:05
 * 常量类
 */
public class Const {
    public static final String DISPOSE = ":处理";
    public static final String ALLOT = ":分配";
    public static final String CREATE = ":新建";
    public static final String SUBMIT = ":归档";
    public static final String AMEND = ":修改";
    public static final String CHARGEBACK = ":退单";
    public static final String INVALID = ":作废";
    public static final String EDEPLOY = ":转派";
    public static final String EXCEPTION = ":异常订单";
    public static final String SUCCESS = ":操作成功";
    public static final String LOCK = ":锁定";

    public static final String NOTHAVECOMMISSION = "NOTHAVECOMMISSION";
    public static final String HAVECOMMISSION = "HAVECOMMISSION";

    public static final String IMPORT_DD_BATCH_ACCOUNT_DISCOUNTS = "新建政企优惠受理批量开户";

    public static final String IMPORT_DD_BATCH_ACCOUNT_PRIVATE = "新建政企私人名批量开户";

    public static final String OTHER_ORDERS = "其他订单";

    public static final String RENEWAL_ORDERS = "续约订单";

    public static final String Auto_Allot_Order = "自动分配订单";

    public static final String PARTNER_ORDERS = "合伙人订单";

    public static final String ORDERNOCM = "CRM订单编号:";

    public static final String ENTERINGGOVERNMENTDEPT = "208";

    public static final String ENTERINGSERVICEDEPT = "209";

    public static final String DISPOSEDEPT = "205";

    public static final String RPA = "RPA";

    public static final String CONST_YES = "是";

    public static final String CONST_NO = "否";

    public static final Long ROBOTID = 114L;

    public static final String RENEWAL_ORIGINAL_PACKAGES = "原续约套餐";

    public static final String RENEWAL_NOT_PACKAGES = "非原续约套餐";


    public enum OrderStatusEnum{
        CREATE(1,"已创建"),
        LOCK(5,"已锁定"),
        MODIFICATION(10,"已修改"),
        ALLOCATION (20,"已分配"),
        EXCEPTION_ORDER(25,"异常订单"),
        SUBMIT(30,"已归档"),
        CHARGE_BACK(40,"已退单"),
        SUCCESS(45,"操作成功"),
        CANCELLATION(50,"已作废");

        OrderStatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        public static OrderStatusEnum codeOf(int code){
            for(OrderStatusEnum orderStatusEnum : values()){
                if(orderStatusEnum.getCode() == code){
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("么有找到对应的枚举");
        }
    }
    public enum BatchAccountStatusEnum{
        BATCH_ACCOUNT_PRIVATE("batch_account_private","政企私人名批量开户"),
        BATCH_ACCOUNT_DISCOUNTS("batch_account_discounts","政企优惠受理批量开户");

        BatchAccountStatusEnum(String code,String value){
            this.code = code;
            this.value = value;
        }
        private String value;
        private String code;

        public String getValue() {
            return value;
        }

        public String getCode() {
            return code;
        }
    }


}
