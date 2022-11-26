package com.crafsed.sas.network

import com.crafsed.sas.data.AuthBody
import com.crafsed.sas.data.AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Интерфейс описывающий доступные в АПИ методы
 * Реализовывается внутри библиотеки Retrofit
 */
interface ServerApi {
    @POST("/token/")
    fun token(@Body body: AuthBody): Call<AuthResponse>



//    @POST("/oborona/password_reset/")
//    fun sendCode(@Body body: EmailUpdatable): Call<Unit>
//
//    @POST("/oborona/password_reset/confirm/")
//    fun resetPassword(@Body body: ResetBody): Call<Unit>
//
//    @PATCH("/oborona/participantupdate/")
//    fun updateUser(@Header("Authorization") token: String, @Body body: UserUpdatable): Call<Unit>
//
//    @POST("/oborona/participantupdateemail/")
//    fun updateEmail(@Header("Authorization") token: String, @Body body: EmailUpdatable): Call<Unit>
//
//    @POST("/oborona/participantchangepassword/")
//    fun updatePassword(@Header("Authorization") token: String, @Body body: PasswordUpdatable): Call<Unit>
//
//    @GET("/oborona/participant/")
//    fun getUser(@Header("Authorization") token: String): Call<User>
//
//    @GET("/oborona/participants/")
//    fun getUsers(): Call<List<User>>
//
//    @POST("/oborona/participantcreate/")
//    fun createUser(@Body regBody: RegisterBody): Call<Unit>
//
//    @POST("/oborona/api-token-auth/")
//    fun auth(@Body loginBody: LoginBody): Call<AuthResponse>
//
//    @GET("/oborona/casualevents/")
//    fun getMainEvents(@Header("Authorization") token: String): Call<List<ShortMainEvent>>
//
//    @GET("/oborona/epicevents/")
//    fun getTopEvents(@Header("Authorization") token: String): Call<List<ShortTopEvent>>
//
//    @GET("/oborona/event/{id}")
//    fun getEvent(@Path("id") id: String, @Header("Authorization") token: String): Call<FullEvent>
//
//    @POST("/oborona/promocodeverify/")
//    fun sendSponsorCode(
//        @Body requestBody: SponsorCodeRequestBody,
//        @Header("Authorization") token: String
//    ): Call<String>
//
//    @POST("/oborona/entrycreate/")
//    fun createEntry(
//        @Body requestBody: EntryRequest,
//        @Header("Authorization") token: String
//    ): Call<String>
//
//    @GET("/oborona/role/{id}")
//    fun getExpandedRole(
//        @Path("id") id: String,
//        @Header("Authorization") token: String
//    ): Call<RoleExpanded>
//
//    @GET("/oborona/listroles/")
//    fun getExpandedRoles(
//        @Header("Authorization") token: String
//    ): Call<List<RoleExpanded>>
//
//    @GET("/oborona/entrys/")
//    fun getEntries(@Header("Authorization") token: String): Call<List<Entry>>
//
//    @GET("/oborona/map/")
//    fun getMapPoints(@Header("Authorization") token: String): Call<MapResponse>
//
//    @POST("/oborona/unsubscribe/")
//    fun unsubscribe(@Header("Authorization") token: String, @Body body: UnsubscribeBody): Call<String>
//
//    @POST("/oborona/costume/")
//    fun sendCostumeForm(@Header("Authorization") token: String, @Body body: CostumeForm): Call<String>
//
//    @GET("/oborona/infowindow/{id}")
//    fun getInfoWindow(@Path("id") id: String, @Header("Authorization") token: String): Call<InfoWindow>
//
//    @GET("/oborona/sexcount/")
//    fun getSexCount(@Header("Authorization") token: String): Call<People>
}