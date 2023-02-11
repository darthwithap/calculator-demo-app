package com.darthwithap.calculatordemo.data

import com.darthwithap.calculatordemo.domain.ICalculator
import com.darthwithap.calculatordemo.domain.ResultWrapper
import kotlin.math.exp

class CalculatorImpl(
    val validator: IValidator,
    val evaluator: IEvaluator
) : ICalculator {
    override suspend fun evaluateExpression(
        expr: String,
        callback: (ResultWrapper<Exception, String>) -> Unit
    ) {
        if (validator.validateExpression(expr))
            callback.invoke(evaluator.evaluateExpression(expr))
        else callback.invoke(ResultWrapper.build { throw Exception() })
    }

}