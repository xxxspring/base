package io.github.xxxspring.base.aspect

import com.alibaba.fastjson.JSON
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

/**
 * @author: jim
 * @description: request 请求日志
 * @date: created in 下午5:23 2018/3/27
 */
@Aspect
@Order(5)
@Component
class WebLogAspect {
    private val logger = LoggerFactory.getLogger(javaClass)
    var startTime = ThreadLocal<Long>()

    @Pointcut("execution(public * com.lf.micro.product..*.*(..)) || execution(public * com.lf.micro.order..*.*(..))")
    fun webLog() {}

    @Before("webLog()")
    @Throws(Throwable::class)
    fun doBefore(joinPoint: JoinPoint) {
        startTime.set(System.currentTimeMillis())
        // 接收到请求，记录请求内容
        if (RequestContextHolder.getRequestAttributes() != null) {
            val attributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
            val request = attributes.getRequest()
            // 记录下请求内容
            logger.info("URL : " + request.getRequestURL().toString())
            logger.info("HTTP_METHOD : " + request.getMethod())
            logger.info("IP : " + request.getRemoteAddr())
            logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "" + joinPoint.getSignature().getName())
            logger.info("ARGS : " + JSON.toJSON(joinPoint.args).toString())
        }
    }
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    @Throws(Throwable::class)
    fun doAfterReturning(ret:Any?) {
        // 处理完请求，返回内容
        if (ret != null) {
            logger.info("RESPONSE : " + ret)
            logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()))
        }
    }
}
