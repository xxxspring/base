package com.lf.ec.common.base.fsm

/**
 * 状态机
 */
class FSM(private val states: List<FSMState>, var entry: FSMState? = null) {

    private val stateNameMap: Map<String, FSMState> = this.states.associateBy { it.name }
    private val stateStatusMap: Map<Any, FSMState> = this.states.asSequence().filter { it.status != null }.associateBy { it.status!! }

    /**
     * 状态迁移
     */
    fun sendEvent(stateName: String, eventName: String, data: Any?): FSMState? {
        val state = stateNameMap[stateName] ?: return null
        val transition = state.transitions?.find { it.event.name == eventName }
        transition?.apply(data)
        return transition?.target
    }

    fun sendEvent(status: Any, eventName: String, data: Any?): FSMState? {
        val state = stateStatusMap[status] ?: return null
        val transition = state.transitions?.find { it.event.name == eventName }
        transition?.apply(data)
        return transition?.target
    }

    /**
     * 状态查询
     */
    fun getState(stateName: String): FSMState? {
        return stateNameMap[stateName] ?: return null
    }

    fun getState(status: Any): FSMState? {
        return stateStatusMap[status] ?: return null
    }
}