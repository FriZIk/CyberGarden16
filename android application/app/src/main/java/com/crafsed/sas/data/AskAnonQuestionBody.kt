package com.crafsed.sas.data

import com.google.gson.internal.bind.DefaultDateTypeAdapter.DateType
import java.sql.Timestamp

data class AskAnonQuestionBody (
    val token: String,
    val question: String,
    val lectureId: String = "1",
    val datetime: String = "2022-11-27 14:00"
)