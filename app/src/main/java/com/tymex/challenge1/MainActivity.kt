package com.tymex.challenge1

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    private val viewModel: ExchangeRateViewModel by viewModels()
    private lateinit var amountEditText: EditText
    private lateinit var currencyFromSpinner: Spinner
    private lateinit var currencyToSpinner: Spinner
    private lateinit var convertButton: Button
    private lateinit var resultTextView: TextView

    private val apiKey = "dd79a59b71d8f5f1c643d39fa017e24e" // Thay thế bằng API Key của bạn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        amountEditText = findViewById(R.id.amountEditText)
        currencyFromSpinner = findViewById(R.id.currencyFromSpinner)
        currencyToSpinner = findViewById(R.id.currencyToSpinner)
        convertButton = findViewById(R.id.convertButton)
        resultTextView = findViewById(R.id.resultTextView)

        // Thiết lập Spinner với các đơn vị tiền tệ
        val currencies = arrayOf("EUR", "USD", "GBP", "JPY", "AUD") // Thêm các đơn vị khác nếu cần
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        currencyFromSpinner.adapter = adapter
        currencyToSpinner.adapter = adapter

        // Quan sát dữ liệu từ ViewModel
        viewModel.exchangeRates.observe(this, Observer { response ->
            if (response != null && response.success) {
                val rates = response.rates
                convertButton.setOnClickListener {
                    val amount = amountEditText.text.toString().toDoubleOrNull()
                    val selectedFromCurrency = currencyFromSpinner.selectedItem.toString()
                    val selectedToCurrency = currencyToSpinner.selectedItem.toString()
                    if (amount != null && rates.containsKey(selectedFromCurrency) && rates.containsKey(selectedToCurrency)) {
                        val convertedAmount = amount * (rates[selectedToCurrency]!! / rates[selectedFromCurrency]!!)
                        resultTextView.text = "Kết quả: $convertedAmount $selectedToCurrency"
                    } else {
                        resultTextView.text = "Vui lòng nhập số tiền hợp lệ."
                    }
                }
            }
        })

        // Gọi API để lấy tỷ giá hối đoái
        viewModel.fetchExchangeRates(apiKey)
    }
}
