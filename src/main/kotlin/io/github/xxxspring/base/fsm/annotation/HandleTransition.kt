package io.github.xxxspring.base.fsm.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class HandleTransition(val id: String)
