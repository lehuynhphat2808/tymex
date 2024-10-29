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
}