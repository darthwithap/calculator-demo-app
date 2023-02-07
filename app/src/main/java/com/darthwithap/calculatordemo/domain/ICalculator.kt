package com.darthwithap.calculatordemo.domain

interface ICalculator {
    suspend fun evaluateExpression(
        expr: String, callback: (ResultWrapper<Exception, String>) -> Unit
    )
}