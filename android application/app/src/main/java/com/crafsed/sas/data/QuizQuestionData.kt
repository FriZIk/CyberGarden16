package com.crafsed.sas.data

data class QuizQuestionData(
    val number: Int,
    val question: String,
    val answers: List<String>,
    val type: QType
) {
    enum class QType {
        ONE, MANY, TEXT
    }
}
