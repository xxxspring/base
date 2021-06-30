package com.lf.ec.common.base.fsm

/**
 * 状态节点
 */
data class FSMState(

        /**
         * 名称
         */
        val name: String,

        /**
         * 描述
         */
        val title: String? = null,

        /**
         * 状态值
         */
        val status: Any? = null,

        /**
         * 迁移
         */
        var transitions: List<FSMTransition>? = null
) {

    /**
     * 获取状态支持的events
     */
    fun getEvents(): List<FSMEvent>? {
        return transitions?.map { it.event }
    }

    override fun toString(): String {
        return "fsm state: $name ($title) => $status"
    }
}
