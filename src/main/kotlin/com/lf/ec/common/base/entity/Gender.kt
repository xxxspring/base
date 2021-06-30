package com.lf.ec.common.base.entity

enum class Gender(val value: Int) {
    UNKNOWN(0),
    MALE(1),
    FEMALE(2);

    companion object {
        fun fromInt(value: Int) = Gender.values().first { it.value == value }
    }
}