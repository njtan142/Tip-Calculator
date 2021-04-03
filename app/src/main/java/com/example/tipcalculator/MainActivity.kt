package com.example.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalculate.setOnClickListener(){
            calculateTip()
        }
        binding.etCustomTip.setOnClickListener() {
            var rgTips = binding.rgTips
            rgTips.check(R.id.rbCustomTip)
        }
    }

    private fun calculateTip()
    {
        val tipPercent = when(binding.rgTips.checkedRadioButtonId){
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
    }

}

class Tip(
    var cost : Int,
    var tipPercent : Float,
){

    fun getTip(rounded: Boolean) : Any
    {
        return if(rounded) {
            kotlin.math.ceil(cost * (tipPercent/100))
        } else {
            cost * (tipPercent/100)
        }
    }

}