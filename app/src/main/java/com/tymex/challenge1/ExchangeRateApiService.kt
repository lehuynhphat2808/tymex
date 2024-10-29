import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApiService {
    @GET("latest")
    suspend fun getLatestRates(
        @Query("access_key") apiKey: String,
        @Query("base") baseCurrency: String = "EUR",
        @Query("symbols") symbols: String? = null
    ): ExchangeRateResponse

    @GET("symbols")
    suspend fun getSupportedCurrencies(
        @Query("access_key") apiKey: String
    ): SupportedCurrenciesResponse
}