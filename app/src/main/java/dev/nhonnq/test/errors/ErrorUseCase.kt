package dev.nhonnq.test.errors

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}
