package com.darthwithap.calculatordemo.data

interface IValidator {
    suspend fun validateExpression(expr: String): Boolean
}