package com.lf.ec.common.base.fsm

/**
 * 状态迁移处理
 */
interface FSMHandler {

    /**
     * 处理id，需唯一
     */
    fun id(): String

    /**
     * 处理逻辑
     */
    fun handleTransition(transition: FSMTransition, data: Any?)
}