package com.crafsed.sas.data

data class CreateCodeBody(
    val token: String,
    val lectureId: String = "1",
    val code: String
)