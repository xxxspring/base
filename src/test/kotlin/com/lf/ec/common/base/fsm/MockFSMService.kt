package io.github.xxxspring.base.fsm

import io.github.xxxspring.base.fsm.annotation.EnableFSMHandler
import io.github.xxxspring.base.fsm.annotation.HandleTransition
import org.springframework.boot.test.context.TestComponent

@TestComponent
@EnableFSMHandler
class MockFSMService {

    @HandleTransition("mock_fsm_service")
    fun handleTransition(transition: FSMTransition, data: Any?) {
        println("MockFSMService::handleTransition $data")
    }

}
