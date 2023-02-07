package com.darthwithap.calculatordemo.data

import com.darthwithap.calculatordemo.domain.ResultWrapper

interface IEvaluator {
    suspend fun evaluateExpression(expr: String): ResultWrapper<Exception, String>
}