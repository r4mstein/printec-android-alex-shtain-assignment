package com.android_alex_shtain_assignment.core.network.http_client

import androidx.viewbinding.BuildConfig
import com.android_alex_shtain_assignment.core.network.api.FinancialApi
import com.android_alex_shtain_assignment.core.preferences.PreferencesApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HttpClientImpl @Inject constructor(
    private val preferences: PreferencesApi,
) : HttpClientApi {

    private var baseUrl: String = ""
    private lateinit var financialApi: FinancialApi

    /**
     * @return [FinancialApi] for making network requests
     */
    override fun getFinancialApi(): FinancialApi {
        val url = preferences.getHost()

        /**
         * init [financialApi] if it wasn't initialized or a user changed url in the settings fragment
         */
        if (this::financialApi.isInitialized.not() || baseUrl != url) {
            baseUrl = url
            financialApi = getApi(FinancialApi::class.java)
        }
        return financialApi
    }

    private fun <Api> getApi(_class: Class<Api>): Api {
        return getRetrofit().create(_class)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(getOkHttpBuilder().build())
            .baseUrl(baseUrl)
            .build()
    }

    private fun getOkHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).also { builder ->
                if (BuildConfig.DEBUG) {
                    builder.addInterceptor(HttpLoggingInterceptor().apply {
                        this.level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }
    }
}