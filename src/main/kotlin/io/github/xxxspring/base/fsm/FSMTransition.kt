package io.github.xxxspring.base.fsm

import kotlin.reflect.KFunction


/**
 * 状态迁移
 */
data class FSMTransition(
        var event: FSMEvent,
        var source: FSMState,
        var target: FSMState,
        var handlers: List<Handler>? = null
) {

    class Handler(private val callable: KFunction<*>, private val target: Any?) {
        fun call(transition: FSMTransition, data: Any?) {
            if (target == null) {
                callable.call(transition, data)
            } else {
                callable.call(target, transition, data)
            }
        }
    }

    fun apply(data: Any?) {
        handlers?.forEach { it.call(this, data) }
    }

    override fun toString(): String {
        return "fsm transition $source to $target on $event"
    }
}
