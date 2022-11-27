package com.crafsed.sas.data

import com.google.gson.annotations.SerializedName

data class PrepodWiFiBody(
    val token: String,
    val lectureId: String = "1",
    @SerializedName("SSID")
    val ssidd: String
)