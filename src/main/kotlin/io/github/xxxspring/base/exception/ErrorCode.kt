package io.github.xxxspring.base.exception

/**
 * 错误码定义
 */
enum class ErrorCode(val value: Int, val message: String, val status: Int = 500) {

    /**
     * 通用
     */

    UNKNOWN(10000, "未知错误"),
    INVOKE_SERVICE_ERROR(10001, "调用服务失败，错误码：%d，错误信息：%s"),
    GATE_WAY_SERVICE_ERROR(10002, "网关服务异常"),
    EXT_FIELD_TYPE_ERROR(10003, "扩展字段类型%s错误"),
    LACK_PARAM_EXCEPTION(10004, "缺少参数%s错误"),
    PARAM_WRONG_EXCEPTION(10005, "参数%s错误"),
    NOT_EACH_EXCEPTION(10006, "不能互为父级%s错误"),


    /**
     * 订单
     */
    ORDER_NOT_EXIST(20000, "订单%s不存在"),
    REFUND_ORDER_NOT_EXIST(20001, "售后单%s不存在"),
    DELIVERY_ORDER_NOT_EXIST(20002, "物流单%s不存在"),
    INVALID_ORDER_OPERATION(20003, "订单%s无法%s"),
    INVALID_REFUND_ORDER_OPERATION(20004, "售后单%s无法%s"),
    INVALID_DELIVERY_ORDER_OPERATION(20005, "物流单%s无法%s"),
    ORDER_UPDATE_DISALLOWED(20006, "订单%s不允许修改"),
    REFUND_ORDER_UPDATE_DISALLOWED(20007, "售后单%s不允许修改"),
    DELIVERY_DIST_NOT_EXIST(20008, "物流包裹%s不存在"),
    DELIVERY_ORDER_UPDATE_DISALLOWED(20009, "物流单%s不允许修改"),
    INSUFFICIENT_INVENTORY(20010, "sku:%s库存不足"),
    INVENTORY_NOT_EXIST(20011, "sku:%s没有库存信息"),
    UPDATE_ORDER_STATUS_DISALLOWED(20012, "订单%s不允许直接修改状态"),
    ORDER_ITEM_NOT_EXIST(20013, "订单物品%s不存在"),
    REFUND_ITEM_QUANTITY_MISMATCH(20014, "退款商品%s数量超过购买数量"),
    RETURN_ITEM_QUANTITY_MISMATCH(20015, "退货商品%s数量超过发货数量"),
    DELIVERY_ITEM_QUANTITY_MISMATCH(20016, "发货商品%s数量超过购买数量"),
    LACK_RETURN_ITEMS(20017, "缺少退货物品"),
    LACK_DELIVERY_ITEMS(20018, "缺少发货物品"),
    LACK_REFUND_ID(20019, "缺少退货单号"),
    DELIVERY_WRONG_TYPE(20020, "错误的发货类型"),
    INVALID_ORDER_PROMOTION(20021, "非法的优惠信息"),
    UPDATE_REFUND_ADDRESS_EXCEPTION(20022, "更新售后单地址%s错误"),
    ORDER_NOT_PAY(20023, "订单%s没有成功支付"),
    ORDER_GROUP_NOT_EXIST(20024, "订单组%s不存在"),
    DELIVERY_TEMPLATE_NOT_EXIST(20008, "物流模版%s不存在"),
    CUSTOMER_SOURCE_DISTRIBUTOR_NOT_EXIST(20009, "分润客户%s不存在"),
    DISTRIBUTOR_NOT_EXIST(20010, "推广员%s不存在"),
    PRODUCT_VARIENT_ROYALTY_TEMPLATE_NOT_EXIST(20011, "商品分佣配置模版%s不存在"),
    SHOP_DISTRIBUTOR_SETTING_NOT_EXIST(20012, "店铺推广员设置%s不存在"),
    SHOP_ROYALTY_TEMPLATE_NOT_EXIST(20013, "店铺分佣配置模版%s不存在"),
    ROYALTY_BONUS_EXCEPTION(20014, "邀请奖励与奖金累计不得超过百分之九十%s"),


    /**
     * 商品
     */
    TAG_NOT_EXIST(30001, "商品标签%s不存在"),
    BRAND_NOT_EXIST(30002, "商品品牌%s不存在"),
    CATEGORY_NOT_EXIST(30004, "商品类目%s不存在"),
    DELETE_PARENT_CATEGORY(30005, "删除非叶子类目%s"),
    CATEGORY_PROP_NOT_EXIST(30006, "类目%s属性项%s不存在"),
    PROP_NOT_EXIST(30008, "属性%d不存在"),
    DELETE_CATEGORY_PROP_WITH_VALS(30009, "类目属性存在属性值，无法删除"),
    PROP_VAL_NOT_EXIST(30010, "属性值%s不存在"),
    PRODUCT_NOT_EXIST(30011, "商品%s不存在"),
    PRODUCT_LACK_SKU(30012, "商品缺少sku信息"),
    PRODUCT_MULTI_SPEC_MEDIA_PROP(30013, "商品规格图片不属于同一个规格项"),
    PRODUCT_SPEC_MEDIA_MISMATCH(30014, "商品规格图片的规格信息与sku不匹配"),
    DELETE_CATEGORY_WITH_PRODUCTS(30015, "类目%s存在关联商品,无法删除"),
    BATCH_CREATE_CATEGORY_TYPE_MISMATCH(30016, "批量创建类目类型必须一致"),
    BATCH_CREATE_CATEGORY_SHOP_MISMATCH(30017, "批量创建类目店铺必须一致"),
    SKU_NOT_EXIST(30018, "sku:%s不存在"),
    LACK_OF_SHOP_OR_TENANT(30019, "缺少店铺id或租户id"),
    INVALID_CATEGORY(30020, "非法的类目"),
    BRAND_MAPPING_EXIST(30021, "该品牌映射已存在"),
    BRAND_MAPPING_NOT_EXIST(30022, "该品牌映射不存在"),
    DELETE_CATEGORY_WITH_MAPPINGS(30023, "类目%s存在关联店铺,无法删除"),
    CATEGORY_MAPPING_EXIST(30024, "该商品分类映射已存在"),
    CATEGORY_MAPPING_NOT_EXIST(30025, "该商品分类映射不存在"),
    PRODUCT_SPEC_INVALID(30026, "商品规格不合法"),
    SHOP_PRODUCT_MAPPING_NOT_EXIST(30028, "该店铺商品不存在"),
    SKU_MAPPING_INVALID(30029, "该SKU关联不合法"),
    DELETE_PRODUCT_WITH_MAPPINGS(30030, "商品%s存在店铺关联,无法删除"),
    PRODUCT_SPEC_ERROR(30031, "规格信息错误"),
    CATEGORY_CIRCUIT(30032, "分类树结构异常"),
    PRODUCT_MAPPING_EXIST(30033, "该品牌映射已存在"),
    PRODUCT_MAPPING_NOT_EXIST(30033, "该品牌映射不存在"),
    CATEGORY_SORT_INVALID_EXCEPTION(30034, "类目sort值%s无效"),


    /**
     * 店铺
     */
    SHOP_CATEGORY_NOT_EXIST(40001, "店铺类目%d不存在"),
    SHOP_DELETE_PARENT_CATEGORY(40002, "删除非店铺叶子类目%s"),
    SHOP_NOT_EXIST(40003, "店铺%s不存在异常"),
    CONFIG_ALREADY_EXIST(40004, "店铺%s存在%s的相关配置"),
    WRONG_CONFIG_KEY(40005, "店铺%s不存在%s的相关配置"),
    WRONG_TEMPLATE_KEY(40005, "店铺%s不存在%s的相关配置模版"),
    TEMPLATE_NOT_EXIST(40006, "不存在%s配置模版"),
    CONFIG_NOT_EXIST(40007, "不存在%s配置"),
    TEMPLATE_ALREADY_EXIST(40008, "店铺%s存在%s的相关配置模版"),
    ADDRESS_NOT_EXIST(40009, "店铺地址%s不存在"),

    /**
     * 客户
     */
    CUSTOMER_NOT_EXIST(50001, "客户%s不存在异常"),
    CUSTOMER_TAG_NOT_EXIST(50002, "客户标签%s不存在异常"),
    CUSTOMER_EVENT_NOT_EXIST(50002, "客户行为事件%s不存在异常"),

    /**
     * 流水
     */
    PAYMENT_NOT_EXIST(60001, "支付流水%s不存在"),
    PAYMENT_STATUS_ERROR(60002, "支付流水%s状态异常"),
    TRANSFER_NOT_EXIST(60003, "企业支付流水%s不存在"),
    TRANSFER_STATUS_ERROR(60004, "企业支付流水%s状态异常"),
    REFUND_NOT_EXIST(60005, "退款流水%s不存在"),
    REFUND_STATUS_ERROR(60006, "退款流水%s状态异常"),


    /**
     * 权限
     */
    GROUP_NOT_EXIST(70001, "用户组%s不存在"),
    PERMISSION_NOT_EXIST(70002, "权限%s不存在"),
    RESOURCE_NOT_EXIST(70003, "资源%s不存在"),
    ROLE_NOT_EXIST(70004, "角色%s不存在"),
    GROUP_MISMATCH_TENANT(70005, "用户组%s与租户%s不匹配"),
    ROLE_MISMATCH_GROUP(70006, "角色%s与用户组%s不匹配"),
    PERMISSION_MISMATCH_ROLE(70007, "权限%s与角色%s不匹配"),
    RESOURCE_MISMATCH_PERMISSION(70008, "资源%s与权限%s不匹配"),
    DELETE_PARENT_GROUP(70009, "删除非叶子用户组%s"),
    DELETE_GROUP_WITH_ROLE(70010, "用户组%s存在关联角色，无法删除"),
    DELETE_GROUP_WITH_USER(70011, "用户组%s存在关联用户，无法删除"),
    DELETE_RESOURCE_WITH_PERMISSION(70012, "资源%s存在关联权限，无法删除"),
    DELETE_PERMISSION_WITH_ROLE(70013, "权限%s存在关联角色，无法删除"),
    DELETE_ROLE_WITH_GROUP(70014, "角色%s存在关联用户组，无法删除"),
    DELETE_ROLE_WITH_USER(70015, "角色%s存在关联用户，无法删除"),
    PERMISSION_IS_NOT_GLOBAL_PERMISSION(70016, "权限%s非全局权限"),
    ROLE_TEMPLATE_NOT_EXIST(70017, "角色模版%s不存在"),
    DELETE_ROLE_TEMPLATE_WITH_PERMISSION(70018, "角色模版%s存在关联权限，无法删除"),
    DELETE_ROLE_TEMPLATE_WITH_ROLE(70019, "角色模版%s存在关联角色，无法删除"),

    /**
     * erp
     */
    DEPARTMENT_NOT_EXIST(80001, "部门%s不存在"),
    EMPLOYEE_NOT_EXIST(80002, "员工%s不存在"),
    DEPARTMENT_NOT_EMPTY(80003, "部门%s存在关联员工"),
    OTHER_TENANT_EMPLOYEE(80004, "员工%s属于其它租户"),
    USER_IS_NOT_EMPLOYEE_OF_TENANT(80005, "用户%s不属于租户%s"),
    EMPLOYEE_NOT_EXIST_BY_USER_ID(80006, "用户%s没有关联的员工"),
    EMPLOYEE_ALREADY_IN_DEPARTMENT(80007, "员工%s已经加入部门%s"),

    /**
     * 优惠券
     */
    COUPON_TEMPLATE_NOT_EXIST(90001, "优惠券活动%s不存在"),
    COUPON_TEMPLATE_OUT_OF_STOCK(90002, "优惠券%s库存不足"),
    COUPON_TEMPLATE_EXPIRE(90003, "优惠券%s已过领取日期"),
    COUPON_TEMPLATE_NOT_REACH_TAKE_TIME(90004, "优惠券%s未到开始领取时间"),
    COUPON_NOT_EXIST(90005, "优惠券%s不存在"),
    COUPON_EXPIRE(90006, "优惠券%s已过使用日期"),
    COUPON_TEMPLATE_INVALID(90007, "优惠券活动不合法"),
    TAKE_COUPON_FAIL(90008, "领取优惠券失败"),
    TAKE_COUPON_NOT_SATISFIED(90009, "不满足领取条件"),
    TAKE_COUPON_TIME_ERROR(90010, "领取优惠券%s时间错误"),
    COUPON_CONSUME_EXCEPTION(90011, "优惠券使用异常"),
    COUPON_TEMPLATE_INVALID_TIME_RULE(90012, "优惠券时间规则错误"),
    COUPON_TEMPLATE_INVALID_TAKE_RULE(90013, "优惠券领取规则错误"),
    COUPON_TEMPLATE_INVALID_CONSUME_RULE(90014, "优惠券使用规则错误"),
    COUPON_TEMPLATE_INVALID_MUTEX_RULE(90015, "优惠券互斥规则错误"),
    COUPON_TEMPLATE_INVALID_BENEFIT_RULE(90016, "优惠券优惠规则错误"),
    COUPON_TEMPLATE_INVALID_RANGE(90017, "优惠券关联错误"),
    COUPON_TEMPLATE_INVALID_TOTAL(90018, "优惠券数量错误"),
    COUPON_TEMPLATE_FILTER_NOT_SUPPORT(90019, "不支持此优惠券过滤条件"),
    COUPON_TEMPLATE_RULE_NOT_EXIST_EXCEPTION(90020, "不存在此规则"),
    COUPON_RECOVER_EXCEPTION(90021, "不存在此规则"),
    COUPON_RANGES_EMPTY(90022, "优惠券范围不能为空"),
    COUPON_INVALID_COMPOSE(90023, "不合法的优惠券组合方案"),
    COUPON_TEMPLATE_DISABLED(90024, "优惠券已停用"),

    /**
     * 积分
     */
    UNFROZEN_INCREMENT_NOT_ENOUGTH(100001, "余额不足"),
    UNFROZEN_DECREMENT_NOT_ENOUGTH(100002, "余额不足"),
    TRANSACTION_NOT_EXIST(100003, "交易%s-%s不存在"),
    POINT_EVENT_TRANSACTION_INVAILD(100004, "积分事件关联的交易非法"),

    /*
    * 内购券
    * */
    PRODUCT_NOT_EXIST_EXCEPTION(100021, "不存在内购商品%s"),
    SKU_NOT_EXIST_EXCEPTION(100022, "不存在内购Sku: %s"),
    LACK_COUPON_EXCEPTION(100023, "缺少%s张内购券"),
    INTRENAL_COUPON_PRODUCT_EXIST_EXCEPTION(100023, "已经存在%s内购券产品"),

    /*
    * 福袋
    * */
    LUCKY_BAG_SKU_NOT_EXIST_EXCEPTION(100041, "不存在%s福袋Sku"),
    LUCKY_BAG_SKU_EXIST_EXCEPTION(100042, "已经存在%s福袋Sku"),
    LUCKY_BAG_NOT_EXIST_EXCEPTION(100043, "不存在%s福袋"),
    LUCKY_BAG_EXIST_EXCEPTION(100044, "已经存在%s福袋"),
    NOT_SET_LUCKY_BAG_SKU_EXCEPTION(100045, "未设置福袋价%s福袋"),

    /*
   * 任务系统
   * */
    TRIGGER_NOT_EXIST_EXCEPTION(100061, "不存在%s触发器"),
    TASK_NOT_EXIST_EXCEPTION(100062, "不存在%s任务"),
    TASK_TEMPLATE_NOT_EXIST_EXCEPTION(100063, "不存在%s任务模板"),
    TASK_INSTANCE_NOT_EXIST_EXCEPTION(100064, "不存在%s任务实例"),
    TASK_EXECUTION_NOT_EXIST_EXCEPTION(100065, "不存在%s任务执行日志"),
    TASK_TEMPLATE_EXIST_EXCEPTION(100063, "已存在%s任务模板"),
    INVALID_TRIGGER_OPTIONS(100064, "非法的触发器配置%s"),
    TASK_TEMPLATE_TYPE_NOT_EXIST_EXCEPTION(100065, "不存在%s任务类型模板"),
    TASK_TEMPLATE_REPLICATE_NOT_EXIST_EXCEPTION(100065, "该%s任务类型模板重复"),

    /*
    * 用户关系 金字塔服务
    * */
    RELATOIN_NOT_EXIST_EXCEPTION(100081, "不存在%s关系"),
    RELATION_EXIST_EXCEPTION(100082, "已存在%s关系"),
    REFERRER_NOT_EXIST_EXCEPTION(100083, "推荐人%s不存在"),
    REFERRER_NOT_EXIST_BY_SOURCE_EXCEPTION(100084, "%s关联的推荐人不存在"),
    CHILDREN_RELATION_EXIST_EXCEPTION(100085, "推荐人%s存在下级"),


    /*
    * 开放平台
    **/
    WRONG_PRODUCT_SPECS_EXCEPTION(110001, "商品规格错误"),
    UN_AUTHORIZED_EXCEPTION(110002, "店铺未授权"),
    UPDATE_STOCK_LACK_SKUID_EXCEPTION(110003, "更新商品%s库存缺少相关skuId"),
    CREATE_PRODUCT_LACK_AVATAR_EXCEPTION(110004, "创建的商品缺少主图"),
    CREATE_ORDER_ITEM_PROMOTION_PRICE_EXCEPTION(110005, "创建订单商品优惠价错误"),
    UPDATE_ORDER_EXIST_INVALID_FIELD_EXCEPTION(110006, "更新订单存在非法的字段"),
    NOT_EXIST_ORDER_ITEM_EXCEPTION(110007, "订单不包含该商品"),
    REFUND_ITEM_INVALID_EXCEPTION(110008, "申请售后的商品非法"),
    /*
    * 搜索
    * */
    ES_ORDER_NOT_EXIST_EXCEPTION(120001, "不存在%s商品"),
    ES_ORDER_EXIST_EXCEPTION(120002, "已经存在%s商品"),
    ES_PRODUCT_NOT_EXIST_EXCEPTION(120003, "不存在%s订单"),
    ES_PRODUCT_EXIST_EXCEPTION(120004, "已经存在%s订单"),
    ES_CUSTOMER_NOT_EXIST_EXCEPTION(120003, "不存在%s客户"),
    ES_CUSTOMER_EXIST_EXCEPTION(120004, "已经存在%s客户"),

    /*
    * 网红
    * */
    KOL_USER_NOT_EXIST_EXCEPTION(130001, "不存在%s网红"),
    KOL_USER_EXIST_EXCEPTION(130002, "已经存在%s网红"),
    CATEGORY_NOT_EXIST_EXCEPTION(130003, "不存在%s类目"),
    DELETE_PARENT_CATEGORY_EXCEPTION(130004, "删除父类目%s异常"),
    POST_NOT_EXIST_EXCEPTION(130005, "不存在%s作品"),
    PROMOTION_NOT_EXIST_EXCEPTION(130006, "不存在%s橱窗商品"),
    VIDEO_NOT_EXIST_EXCEPTION(130007, "不存在%s视频"),


    /*
    * 网红搜索
    * */
    KOL_USER_VIEW_NOT_EXIST_EXCEPTION(140001, "不存在%s网红View"),
    KOL_USER_VIEW_EXIST_EXCEPTION(140002, "已经存在%s网红view"),
    POST_VIEW_NOT_EXIST_EXCEPTION(140003, "不存在%s作品"),
    PROMOTION_VIEW_NOT_EXIST_EXCEPTION(140004, "不存在%s橱窗商品"),
    VIDEO_VIEW_NOT_EXIST_EXCEPTION(140005, "不存在%s视频"),

    /*
    * 网红 market
    * */
    INVITATION_NOT_EXIST_EXCEPTION(150001, "不存在%s邀请"),
    INVITATION_EXIST_EXCEPTION(150002, "已经存在%s邀请"),
    KOL_TASK_NOT_EXIST_EXCEPTION(150003, "不存在%s任务"),
    KOL_TASK_EXIST_EXCEPTION(150004, "已经存在%s任务"),
    TASK_PARTICIPANT_EXIST_EXCEPTION(150005, "已经存在%s任务参与者"),
    TASK_PARTICIPANT_NOT_EXIST_EXCEPTION(150006, "不存在%s任务参与者"),
    QUOTA_ORDER_NOT_EXIST_EXCEPTION(150007, "不存在%s配额订单"),
    QUOTA_NOT_EXIST_EXCEPTION(150008, "不存在%s配额"),
    QUOTA_ORDER_WRONG_STATUS_EXCEPTION(150009, "%s配额订单状态有误"),
    INVITATION_IDS_IS_EMPTY_EXCEPTION(150010, "%s邀请列表为空"),

    /*
    * 网红 api
    * */
    PARAMETER_LIST_IS_EMPTY_EXCEPTION(160001, "%s参数列表为空"),
    CONFIG_IS_NOT_EXIST_EXCEPTION(160002, "%配置参数列表为空"),

    WITHDRAWAL_NOT_EXIST(170001, "提现流水%s不存在"),
    WITHDRAWAL_STATUS_ERROR(170002, "提现流水%s状态异常"),
    WITHDRAWAL_NOT_ENOUGH(170003, "余额不足,提现失败"),

    /**
     * 客户搜索 customer-search
     */
    CUSTOMER_VIEW_NOT_EXIST(180001, "客户%s不存在异常"),

    /**
     * Stats Api
     */
    GRANULARITY_ERROR(190001, "granularity:%s error"),
}
