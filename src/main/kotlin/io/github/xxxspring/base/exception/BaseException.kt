package io.github.xxxspring.base.exception

open class BaseException(val code: ErrorCode, vararg args: Any)
    : RuntimeException(String.format(code.message, *args))
