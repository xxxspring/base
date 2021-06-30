package com.lf.ec.common.base.exception

open class BaseException(val code: ErrorCode, vararg args: Any)
    : RuntimeException(String.format(code.message, *args))