package com.darthwithap.calculatordemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.darthwithap.calculatordemo.ui.ICalculatorUi
import com.darthwithap.calculatordemo.ui.ViewEvent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener,
    ICalculatorUi.View {

    lateinit var logic: ICalculatorUi.Logic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TODO("Implement handmade dependency injection class for logic")

        bindUserInterface()
    }

    private fun bindUserInterface() {
        button_equals.setOnClickListener(this)
        button_decimal.setOnClickListener(this)
        button_delete.setOnClickListener(this)
        button_delete.setOnLongClickListener(this)

        button_one.setOnClickListener(this)
        button_two.setOnClickListener(this)
        button_three.setOnClickListener(this)
        button_four.setOnClickListener(this)
        button_five.setOnClickListener(this)
        button_six.setOnClickListener(this)
        button_seven.setOnClickListener(this)
        button_eight.setOnClickListener(this)
        button_nine.setOnClickListener(this)
        button_zero.setOnClickListener(this)

        button_add.setOnClickListener(this)
        button_subtract.setOnClickListener(this)
        button_multiply.setOnClickListener(this)
        button_divide.setOnClickListener(this)

    }

    companion object {
        val DISPLAY_STATE = "STATE"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(DISPLAY_STATE, label_text_view.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        label_text_view.text = savedInstanceState.getString(DISPLAY_STATE, "")
    }

    /* ------------ Interface Functions --------- */

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.button_equals -> logic.handleViewEvent(ViewEvent.Evaluate)
            R.id.button_delete -> logic.handleViewEvent(ViewEvent.Delete)
            else -> {
                if (view is Button) {
                    logic.handleViewEvent(
                        ViewEvent.Input(
                            view.text.toString()
                        )
                    )
                }
            }
        }
    }

    override fun onLongClick(view: View?): Boolean {
        when (view?.id) {
            R.id.button_delete -> logic.handleViewEvent(ViewEvent.DeleteAll)
        }

        // return true if we don't want to call the onClick next
        return true
    }

    override var display: String
        get() = label_text_view.text.toString()
        set(value) {
            label_text_view.text = value
        }

    override fun showError(value: String) {
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
    }
}