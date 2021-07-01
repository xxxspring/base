package io.github.xxxspring.base.handler

import io.github.xxxspring.base.dto.BaseResponse
import io.github.xxxspring.base.exception.BaseException
import io.github.xxxspring.base.exception.ErrorCode
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletResponse

@ControllerAdvice(basePackages = ["com.lf.ec"])
class ControllerExceptionHandler {

    private val logger by lazy { LoggerFactory.getLogger(ControllerExceptionHandler::class.java) }

    @ExceptionHandler(BaseException::class)
    @ResponseBody
    private fun baseExceptionHandler(ex: BaseException, res: HttpServletResponse): BaseResponse {
        res.status = ex.code.status
        val stack = ex.stackTrace.first()
        logger.error("${stack.className}.${stack.methodName}: ${stack.lineNumber}", ex)
        return BaseResponse(
                code = ex.code.value,
                msg = ex.message
        )
    }

    @ExceptionHandler
    @ResponseBody
    private fun exceptionHandler(ex: Exception, res: HttpServletResponse): BaseResponse {
        res.status = ErrorCode.UNKNOWN.status
        val stack = ex.stackTrace.first()
        logger.error("${stack.className}.${stack.methodName}: ${stack.lineNumber}", ex)
        return BaseResponse(
                code = ErrorCode.UNKNOWN.value,
                msg = "未知错误: ${ex.message}"
        )
    }
}
