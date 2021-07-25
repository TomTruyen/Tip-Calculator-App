package com.tomtruyen.tipcalcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity() {
    private var totalBill : Double  = 0.0
    private var tipPercentage : Int = 15
    private var splitCount : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextInputEditText>(R.id.billTotalInput).addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                val str: String = text?.trim().toString()

                totalBill = if(str.isEmpty()) 0.0 else str.toDouble()
                calculate()
            }
        })

        findViewById<Slider>(R.id.tipPercentageSlider).addOnChangeListener { _, value, _ ->
            tipPercentage = value.toInt()
            findViewById<TextView>(R.id.tipPercentageText).text = "$tipPercentage%"
            calculate()
        }

        findViewById<Slider>(R.id.splitCountSlider).addOnChangeListener { _, value, _  ->
            splitCount = value.toInt()
            findViewById<TextView>(R.id.splitCountText).text = splitCount.toString()
            calculate()
        }
    }

    fun calculate() {
        val totalTip : Double = totalBill * (tipPercentage.toDouble() / 100)
        val totalPrice : Double = totalBill + totalTip

        val splitTip : Double = totalTip / splitCount
        val splitPrice : Double = totalPrice / splitCount

        val totalTipText : TextView = findViewById(R.id.totalTipText)
        val totalPriceText : TextView = findViewById(R.id.totalText)
        val splitTipText : TextView = findViewById(R.id.splitTipText)
        val splitTotalText : TextView = findViewById(R.id.splitTotalText)

        totalTipText.text = String.format("%.2f", totalTip)
        totalPriceText.text = String.format("%.2f", totalPrice)
        splitTipText.text = String.format("%.2f", splitTip)
        splitTotalText.text = String.format("%.2f", splitPrice)
    }
}