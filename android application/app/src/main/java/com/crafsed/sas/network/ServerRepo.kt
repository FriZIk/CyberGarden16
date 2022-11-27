package com.crafsed.sas.network

import com.crafsed.sas.data.*
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


    fun login(email: String, password: String): AuthResponse {
        val response =
            provideRetrofit().token(AuthBody(email.orEmpty(), password.orEmpty())).execute()

        return if (response.isSuccessful) {
            response.body()!!
        } else {
            AuthResponse("", "", false)
        }
    }

    fun getSchedule(token: String): ListData? {
        val response = provideRetrofit().schedule(ScheduleBody(token)).execute()

        return if (response.isSuccessful) {
            response.body()!!
        } else {
            null
        }
    }

    fun codeCheckIn(token: String, id: String, code: String): Boolean {
        val response = provideRetrofit().checkinCode(StudentCheckinBody(token, id, code)).execute()

        return if (response.isSuccessful) {
            response.body() == "201"
        } else {
            false
        }
    }

    fun studentWiFi(token: String, id: String, ssidds: List<String>): Boolean {
        val response = provideRetrofit().studWiFi(StudentWiFiBody(token, id, ssidds)).execute()

        return if (response.isSuccessful) {
            response.body() == "201"
        } else {
            false
        }
    }

    fun prepodWiFi(token: String, id: String, ssid: String): Boolean {
        val response = provideRetrofit().prepodWiFi(PrepodWiFiBody(token, id, ssid)).execute()

        return if (response.isSuccessful) {
            response.body() == "201"
        } else {
            false
        }
    }

    fun setCode(token: String, id: String, code: String): Boolean {
        val response = provideRetrofit().setCode(CreateCodeBody(token, id, code)).execute()

        return if (response.isSuccessful) {
            response.body() == "201"
        } else {
            false
        }
    }

    fun askQuestion(token: String, id: String, question: String): Boolean {
        val response = provideRetrofit().askAnonQuestion(AskAnonQuestionBody(token, question, id)).execute()

        return if (response.isSuccessful) {
            response.body() == "201"
        } else {
            false
        }
    }

    fun getQuestions(token: String, id: String): List<AnonQuestionData>? {
        val response = provideRetrofit().getQuestions().execute()

        return if (response.isSuccessful) {
            return response.body()!!
        } else {
            null
        }
    }
}