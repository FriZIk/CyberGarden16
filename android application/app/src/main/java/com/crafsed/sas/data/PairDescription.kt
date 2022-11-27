package com.crafsed.sas.data

import com.google.gson.annotations.SerializedName

data class PairDescription (
    @SerializedName("timestart")
    val timeStart: String,
    @SerializedName("timeend")
    val timeEnd: String,
    @SerializedName("object")
    val obj: String,
    val place: String,
    val lector: String,
    @SerializedName("discription")
    val description: String
)