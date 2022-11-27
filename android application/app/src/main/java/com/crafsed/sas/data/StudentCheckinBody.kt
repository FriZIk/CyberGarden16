package com.crafsed.sas.data

data class StudentCheckinBody (
    val token: String,
    val lectureId: String = "1",
    val code: String
)