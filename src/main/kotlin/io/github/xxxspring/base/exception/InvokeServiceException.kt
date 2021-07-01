package io.github.xxxspring.base.exception

/**
 * 调用服务失败
 */
class InvokeServiceException(errorCode: Int? = 0, errorMsg: String? = "")
    : BaseException(ErrorCode.INVOKE_SERVICE_ERROR, "$errorCode", "$errorMsg") {
}
