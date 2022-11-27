package com.crafsed.sas.data

data class StudentWiFiBody(
    val token: String,
    val lectureId: String = "1",
    val ssids: List<String>
)