package io.github.xxxspring.base.fsm

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [FSMTest::class])
@ComponentScan("io.github.xxxspring.base.fsm")
class FSMTest {

    @Autowired
    lateinit var context: ApplicationContext

    @Test
    fun testReadConfig() {
        val fsmMap = FSMReader().read("classpath*:/fsm/*.xml", context)
        assertNotNull(fsmMap["multi_value_state"])
        assertNotNull(fsmMap["single_value_state"])
        assertNull(fsmMap["null_state"])
    }

    @Test
    fun testTransition() {
        val fsmMap = FSMReader().read("classpath*:/fsm/single_value_state.xml", context)
        val fsm = fsmMap["single_value_state"]!!
        assertEquals("state1", fsm.entry!!.name)
        assertEquals("state2", fsm.getState("state2")!!.name)
        assertNull(fsm.getState("state3"))
        var nextState = fsm.sendEvent("state1", "evt1", "data")
        assertNotNull(nextState)
        assertEquals("state2", nextState!!.name)
        nextState = fsm.sendEvent("state2", "evt1", null)
        assertNull(nextState)
    }

    @Test
    fun testMultiValueStates() {
        val fsmMap = FSMReader().read("classpath*:/fsm/multi_value_state.xml", context)
        val fsm = fsmMap["multi_value_state"]!!
        val value1 = mapOf("value1" to "value1", "value2" to "value2")
        val value2 = mapOf("value1" to "value3", "value2" to "value4")
        assertEquals("state1", fsm.entry!!.name)
        assertEquals(value1, fsm.entry!!.status)
        assertNotNull(fsm.getState(value1))
        assertNull(fsm.getState(mapOf("value1" to "value1", "value2" to "value3")))
        var nextState = fsm.sendEvent(value1, "evt1", "data")
        assertNotNull(nextState)
        assertEquals(value2, nextState!!.status)
        nextState = fsm.sendEvent(value2, "evt1", "data")
        assertNull(nextState)
    }
}
