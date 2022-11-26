package com.crafsed.sas.data

data class QuestionsData(
    val questions: List<QuestionData>,
    val quizName: String,
    val seconds: Int,
    val openingTime: String
) {
    companion object {
        val TEST = QuestionsData(
            seconds = 150,
            openingTime = "09:00",
            quizName = "Тест 1",
            questions = listOf(
                QuestionData(
                    number = 4,
                    question = "Как зовут преподавателя",
                    answers = listOf(
                        "Бандергольф Штырьленгит",
                        "Бенадрил Трахтенберг",
                        "Баклажан Овервоч",
                        "Наиль Шавкятович",
                    ),
                    type= QuestionData.QType.ONE,
                ),
                QuestionData(
                    number = 1,
                    question = "Сколько будет 9 умножить на 9",
                    answers = listOf(
                        "81",
                        "91",
                        "72",
                        "99",
                    ),
                    type= QuestionData.QType.ONE,
                ),
                QuestionData(
                    number = 2,
                    question = "Какой набор критериев оценки игры против природы верен",
                    answers = listOf(
                        "MM, BL, S, HV",
                        "HL, G, P, BL",
                        "BM, HW, S, MM",
                        "HW, P, G, BL",
                    ),
                    type= QuestionData.QType.MANY,
                ),
                QuestionData(
                    number = 3,
                    question = "Что такое Гносеология",
                    answers = emptyList(),
                    type= QuestionData.QType.TEXT,
                )
            )
        )
    }
}
