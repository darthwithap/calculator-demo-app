package com.darthwithap.calculatordemo

import com.darthwithap.calculatordemo.data.CalculatorImpl
import com.darthwithap.calculatordemo.data.IEvaluator
import com.darthwithap.calculatordemo.data.IValidator
import com.darthwithap.calculatordemo.domain.ResultWrapper
import com.darthwithap.calculatordemo.ui.ICalculatorUi
import com.darthwithap.calculatordemo.ui.ViewEvent
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal const val VALID_EXPRESSION: String = "2+2"
internal const val VALID_ANSWER: String = "4"
internal const val INVALID_EXPRESSION: String = "2++"

class CalculatorImplTest {
    private val validatorFake = ValidatorFakeImpl()
    private val evaluatorFake = EvaluatorFakeImpl()
    private val logicFake = CalculatorLogicFakeImpl()

    private lateinit var calculator: CalculatorImpl

    /**
     * 1. Give expression to validator: true = valid
     * 2. Give expression to evaluator: String = successful
     * 3. Return the result to the callback
     */

    @Test
    fun `On Evaluate Valid Expression`() = runBlocking {
        calculator = CalculatorImpl(validatorFake, evaluatorFake)
        logicFake.result = null

        calculator.evaluateExpression(VALID_EXPRESSION, logicFake::handleResult)

        val result = logicFake.result

        if (result is ResultWrapper.Error) {
            assertTrue(true)
        } else
            assertTrue(false)
    }

    /**
     * 1. Give expression to validator: false = invalid
     * 2. Return the result to the callback
     */

    @Test
    fun `On Evaluate Invalid Expression`() = runBlocking {
        calculator = CalculatorImpl(validatorFake, evaluatorFake)
        logicFake.result = null
        validatorFake.succeed = false

        calculator.evaluateExpression(INVALID_EXPRESSION, logicFake::handleResult)

        val result = logicFake.result

        if (result is ResultWrapper.Success) {
            assertEquals(result.value, VALID_ANSWER)
        } else
            assertTrue(false)
    }
}

class ValidatorFakeImpl : IValidator {
    internal var succeed: Boolean = true
    internal var called: Boolean = true

    override suspend fun validateExpression(expr: String): Boolean {
        called = true
        return succeed
    }
}

class EvaluatorFakeImpl : IEvaluator {
    internal var succeed: Boolean = true
    internal var called: Boolean = true

    override suspend fun evaluateExpression(expr: String):
            ResultWrapper<Exception, String> {
        called = true
        return if (succeed) ResultWrapper.build {
            VALID_ANSWER
        } else ResultWrapper.build { throw Exception() }
    }
}

class CalculatorLogicFakeImpl : ICalculatorUi.Logic {
    var handleResultCalled: Boolean = true
    var result: ResultWrapper<Exception, String>? = null

    override fun handleViewEvent(event: ViewEvent) {}

    override fun handleResult(result: ResultWrapper<Exception, String>) {
        this.result = result
        handleResultCalled = true
    }

}