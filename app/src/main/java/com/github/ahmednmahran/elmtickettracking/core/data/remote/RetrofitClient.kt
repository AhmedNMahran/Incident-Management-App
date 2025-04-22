package com.github.ahmednmahran.elmtickettracking.core.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * `RetrofitClient` is a singleton object responsible for providing a configured instance of `ApiService`
 * for making network requests. It encapsulates the setup of Retrofit, OkHttpClient, and related components.
 *
 * **Key Features:**
 *   - **Base URL:** Defines the base URL for all API requests (`BASE_URL`).
 *   - **OkHttpClient Configuration:** Sets up an `OkHttpClient` instance with:
 *      - **Logging Interceptor:** Logs HTTP request and response details for debugging.
 *      - **Timeout Settings:** Configures connection and read timeouts for network operations.
 *   - **Retrofit Instance:** Creates a Retrofit instance configured with:
 *      - **Base URL:** The defined `BASE_URL`.
 *      - **OkHttpClient:** The configured `okHttpClient`.
 *      - **Gson Converter:** Uses `GsonConverterFactory` to handle JSON serialization/deserialization.
 *   - **ApiService Instance:** Provides a lazily initialized instance of `ApiService` that can be used to interact with the API.
 *
 * **Usage:**
 *
 *   To obtain an instance of the `ApiService`, you can directly access the `apiService` property:
 *   ```kotlin
 *   val myApiService = RetrofitClient.apiService
 *   myApiService.getSomeData().enqueue(...)
 *   ```
 *
 * **Dependencies:**
 * - OkHttp
 * - OkHttp Logging Interceptor
 * - Retrofit
 * - Gson Converter Factory
 */
object RetrofitClient {
    private const val BASE_URL = ApiConstants.BASE_URL

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}