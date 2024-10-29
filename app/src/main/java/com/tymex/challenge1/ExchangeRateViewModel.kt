package com.tymex.challenge1

import ExchangeRateResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ExchangeRateViewModel : ViewModel() {
    private val _exchangeRates = MutableLiveData<ExchangeRateResponse>()
    val exchangeRates: LiveData<ExchangeRateResponse> get() = _exchangeRates

    private val _currencies = MutableLiveData<Map<String, String>>()
    val currencies: LiveData<Map<String, String>> get() = _currencies

    fun fetchExchangeRates(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getLatestRates(apiKey)
                _exchangeRates.postValue(response)
            } catch (e: Exception) {
                // Xử lý lỗi
            }
        }
    }

    fun fetchSupportedCurrencies(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getSupportedCurrencies(apiKey)
                if (response.success) {
                    _currencies.postValue(response.symbols)
                }
            } catch (e: Exception) {
                // Xử lý lỗi
            }
        }
    }
}