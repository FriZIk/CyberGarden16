package com.crafsed.sas.network

import com.crafsed.sas.data.AuthBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerRepo() {
    fun provideRetrofit(): ServerApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val retrofit = Retrofit.Builder().baseUrl("http://83.221.202.194:2500").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofit.create(ServerApi::class.java)
    }


    fun login(email: String, password: String): Pair<String?, String?> {
        return             Pair(null, null)
        val response = provideRetrofit().token(AuthBody(email.orEmpty(), password.orEmpty())).execute()

        return if (response.isSuccessful) {
            val respToken = response.body()?.access
            val accessToken = response.body()?.refresh
            Pair(respToken, accessToken)
        } else {
            Pair(null, null)
        }
    }
}