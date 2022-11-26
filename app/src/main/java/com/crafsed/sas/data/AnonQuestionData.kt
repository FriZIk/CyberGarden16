package com.crafsed.sas.data

data class AnonQuestionData(
    val text: String
) {
    companion object {
        val TEST = listOf(
            AnonQuestionData("Тест"),
            AnonQuestionData("Я хочу пиццы"),
            AnonQuestionData("Почему именно 81 а не 92?"),
            AnonQuestionData("А сколько баллов за контрольную?"),
        )
    }
}