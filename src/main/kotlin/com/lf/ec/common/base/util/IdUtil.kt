package com.lf.ec.common.base.util


/**
 * @author: jim
 * @description: snowflake id
 * @date: created in 下午2:37 2018/6/14
 */
class IdUtil {
    companion object {
        val snowflakeIdWorker = SnowflakeIdWorker(2L, 2L)

        fun getId(): Long {
            return snowflakeIdWorker.getId()
        }
    }
}