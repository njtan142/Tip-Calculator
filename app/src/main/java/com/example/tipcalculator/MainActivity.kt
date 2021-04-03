package com.example.tipcalculator

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tipcalculator.databinding.ActivityMainBinding
import java.lang.Exception
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalculate.setOnClickListener() {
            calculateTip()
        }

        binding.etCustomTip.setOnClickListener() {
            binding.rgTips.check(R.id.rbCustomTip)
        }

        binding.etCustomTip.setOnFocusChangeListener { _, _ ->
            binding.rgTips.check(R.id.rbCustomTip)
        }

        binding.etCostOfService.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }
        binding.etCustomTip.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }

    }

    private fun calculateTip() {
        try {
            val tipPercent = when (binding.rgTips.checkedRadioButtonId) {
                R.id.rb15pTip -> 15
                R.id.rb18pTip -> 18
                R.id.rb20pTip -> 20
                else -> binding.etCustomTip.text.toString().toFloat()
            }
            val tip = Tip(
                binding.etCostOfService.text.toString().toInt(),
                tipPercent = tipPercent.toFloat(),
            )
            val amount = tip.getTip(binding.switchRoundTipToggle.isChecked)
            val formattedAmount = NumberFormat.getCurrencyInstance().format(amount)
            binding.tvTipAmount.text = getString(R.string.tip_amount, formattedAmount)
        } catch (e: Exception) {
            // do nothing
        }
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }


}


class Tip(
    var cost: Int,
    var tipPercent: Float,
) {

    fun getTip(rounded: Boolean): Any {
        return if (rounded) {
            kotlin.math.ceil(cost * (tipPercent / 100))
        } else {
            cost * (tipPercent / 100)
        }
    }
}

