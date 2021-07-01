package io.github.xxxspring.base.entity

enum class FilterType {
    EQ,
    GT,
    GTE,
    LT,
    LTE,
    LIKE,
    NE,
    NOT_IN,
    NOT_LIKE,
    IN,
    IS_NULL,
    AND,
    OR,
    NOR,
    MATCH,
    MULTI_MATCH,
    WILD_CARD,
    TERMS,
    TERM,
    BOOLEAN,
    // 左闭右闭
    RANGE,
    // 左闭右开
    RANGEL,
    // 左开右闭
    RANGER,


    /*
    * 以下作为过滤使用
    * */
    GT_FILTER,
    GTE_FILTER,
    LT_FILTER,
    LTE_FILTER,
    // 左闭右闭
    RANGE_FILTER,
    // 左闭右开
    RANGEL_FILTER,
    // 左开右闭
    RANGER_FILTER,
    TERMS_FILTER,
    TERM_FILTER,

    /*
    * 以下为权重查询使用
    * */
    EQ_SCORE,
    TERMS_SCORE,

    // 嵌套查询
    NESTED
}

