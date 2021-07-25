package com.tomtruyen.tipcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity() {
    private lateinit var mAdView: AdView
    private var mTotalBill : Double  = 0.0
    private var mTipPercentage : Int = 15
    private var mSplitCount : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        findViewById<TextInputEditText>(R.id.billTotalInput).addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                val str: String = text?.trim().toString()

                mTotalBill = if(str.isEmpty()) 0.0 else str.toDouble()
                calculate()
            }
        })

        findViewById<Slider>(R.id.tipPercentageSlider).addOnChangeListener { _, value, _ ->
            mTipPercentage = value.toInt()
            findViewById<TextView>(R.id.tipPercentageText).text = "$mTipPercentage%"
            calculate()
        }

        findViewById<Slider>(R.id.splitCountSlider).addOnChangeListener { _, value, _  ->
            mSplitCount = value.toInt()
            findViewById<TextView>(R.id.splitCountText).text = mSplitCount.toString()
            calculate()
        }
    }

    fun calculate() {
        val totalTip : Double = mTotalBill * (mTipPercentage.toDouble() / 100)
        val totalPrice : Double = mTotalBill + totalTip

        val splitTip : Double = totalTip / mSplitCount
        val splitPrice : Double = totalPrice / mSplitCount

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