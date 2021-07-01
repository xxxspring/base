package io.github.xxxspring.base.dto

enum class ApiCode(var code: Int, var message: String) {
    SUCCESS(0, "请求成功"),
    FAIL(-1, "请求失败"),

    // 前三位服务code, 后两位具体业务code
    // common service code
    FIELD_NOT_NULL(10001, "必填字段为空"),
    TENANT_ID_NOT_NULL(10002, "租户id为空"),
    PLATFORM_ID_NOT_NULL(10003, "平台id为空"),
    MERCHANT_ID_NOT_NULL(10004, "商户id为空"),
    // object
    ID_NOT_NULL(10005, "对象id为空"),
    NAME_NOT_NULL(10006, "对象名称为空"),
    NAME_EXIST(10007, "对象名称已存在"),
    NOT_FIND(10008, "对象不存在"),
    OBJECT_FIND(10009, "对象已存在"),
    CODE_FIND(10010, "对象code已存在"),
    SN_FIND(10011, "对象sn已存在"),

    // brand service code
    BRAND_ID_NOT_NULL(10101, "品牌id为空"),
    BRAND_NAME_NOT_NULL(10102, "品牌名称为空"),
    BRAND_NAME_EXIST(10103, "品牌名称以存在"),
    BRAND_NOT_FIND(10104, "品牌不存在"),

    // category service code
    CATEGORY_PARENT_NOT_FIND(10201, "父分类不存在"),
    CATEGORY_NOT_EQUAL_PARENT(10202, "当前父分类不能等于自己的分类"),
    CATEGORY_AND_CHILDREN_NOT_FIND(10202, "没有找到对应的分类及子分类"),
    CATEGORY_NOT_FIND(10203, "分类不存在"),

    // product service code
    PRODUCT_SN_EXIST(10301, "商品sn已存在"),
    PRODUCT_NOT_FIND(10302, "商品不存在"),
    PRODUCT_SKU_NOT_FIND(10303, "sku不存在"),
    SKU_INVENTORY_NOT_FIND(10304, "库存不存在"),

    // sku service code
    SKU_NOT_FIND(10401, "商品sku不存在"),

    // product_spec service code
    PRODUCT_SPEC_EXIST(10501, "商品规格已存在"),
    PRODUCT_SPEC_NOT_EXIST(10502, "商品规格不存在"),

    // spec service code
    SPEC_NOT_EXIST(10601, "规格不存在"),

    // supplier service code
    SUPPLIER_NOT_FIND(10701, "供应商不存在"),

    // warehouse service code
    WAREHOUSE_NOT_FIND(10801, "仓库不存在"),

    // order service code
    ORDER_NOT_EXIST(10901, "订单不存在"),
    ORDER_ITEM_AMOUNT_INVALID(10902, "订单商品项金额校验不通过"),
    ORDER_PRODUCT_AMOUNT_INVALID(10903, "订单商品总金额校验不通过"),
    ORDER_PAYED_CANNOT_UPDATE_ITEM(10904, "已支付订单不能修改订单项"),
    ORDER_SHIPPED_CANNOT_UPDATE_ITEM(10905, "已发货订单不能修改订单项"),
    ORDER_TOTAL_AMOUNT_INVALID(10906, "订单总支付金额校验不通过"),
    ORDER_TOTAL_AMOUNT_CANNOUT_UPDATE(10907, "订单商品总金额不允许修改"),
    ORDER_HAS_DELETED(10908, "订单已删除"),
    REFUND_SKU_AMOUNT_ILLEGAL(10909, "退货单的退款金额不合法"),
    REFUND_ORDER_ITEM_ILLEGAL(10910,"退货单子项不合法"),
    REFUND_SKU_FUND_AMOUNT_ILLEGAL(10911,"退款退货金额与货品金额不匹配"),
    REFUND_AMOUNT_GREATER_ORDER_AMOUNT(10911,"退款退货金额大于订单金额"),
    REFUND_QUANTITY_GREATER_ORDER_QUANTITY(10912,"退款Item的数量不正确"),
    REFUND_SKU_NOT_EXIST(10912,"退款的SKU订单中不存在"),
    REFUND_NOT_ALLOWED(10913,"订单已经发货不允许退款,请申请退款退货"),
    REFUND_NOT_ALLOWED_BY_STATUS(10913,"只有待审核的订单,待发货的订单允许退款"),
    REFUND_SKU_NOT_ALLOWED(10914,"该订单状态下不允许退款退货"),
    ORDER_EXIST(10908, "订单已存在"),

    // inventory service code
    INVENTORY_INCREASE_FIAL(11001, "添加库存失败"),
    INVENTORY_DECREASE_FAIL(11002, "扣减库存失败"),

    // customer service code
    CUSTOMER_PLATFORMUID_EXIST(11003, "平台用户id已存在"),
    CUSTOMER_NOT_EXIST(11004, "客户不存在"),
}
