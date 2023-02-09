package com.darthwithap.calculatordemo.ui

import android.provider.ContactsContract.CommonDataKinds.StructuredName
import com.darthwithap.calculatordemo.domain.ResultWrapper

/*
A contract between the frontend of the feature of the application
*/

interface ICalculatorUi {
    interface View {
        // display of the calculator
        var display: String

        // display the error via a toast
        fun showError(value: String)
    }

    // Presentation Logic
    interface Logic {
        fun handleViewEvent(event: ViewEvent)

        /*
        view isn't directly going to call this function so can also isolate this in a separate
        abstract class
         */
        fun handleResult(result: ResultWrapper<Exception, String>)
    }
}

const val GENERIC_ERROR_MESSAGE = "Error Occurred"

sealed class ViewEvent {
    object Evaluate : ViewEvent()
    object Delete : ViewEvent()
    object DeleteAll : ViewEvent()
    data class Input(val input: String) : ViewEvent()
}