package io.github.xxxspring.base.fsm

import org.springframework.boot.test.context.TestComponent

@TestComponent
class MockFSMHandler : FSMHandler {

    override fun id(): String {
        return "mock_fsm_handler"
    }

    override fun handleTransition(transition: FSMTransition, data: Any?) {
        println("MockFSMHandler::handleTransition $data")
    }
}
