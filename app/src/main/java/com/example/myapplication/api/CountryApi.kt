package com.example.myapplication.api

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.random.Random

interface CountryApi {
    // https://api.first.org/data/v1/countries
    @GET("data/v1/countries")
    suspend fun getCountries(
        @Query("limit") limit: Int? = null
    ): CountryResponse
}

@Serializable
data class FirstApiResponse<T>(val data: T)

typealias CountryResponse = FirstApiResponse<Map<String, Country>>

@Serializable
data class Country(
    val country: String,
    val region: String
)

val json by lazy {
    Json {
        isLenient = true
        ignoreUnknownKeys = true
    }
}

val countryApi: CountryApi by lazy {
    Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(
            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    // Random delay to simulate network latency
                    Thread.sleep(Random.nextLong(1500, 3000))

                    chain.proceed(chain.request())
                }
                .build()
        )
        .baseUrl("https://api.first.org/")
        .build()
        .create()
}
