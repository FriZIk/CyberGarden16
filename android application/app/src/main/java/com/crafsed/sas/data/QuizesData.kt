package com.crafsed.sas.data

data class QuizesData(
    val questions: List<QuizQuestionData>,
    val quizName: String,
    val seconds: Int,
    val id: String,
    val openingTime: Long
) {
    companion object {
        val TEST = listOf(QuizesData(
            seconds = 10,
            id = "123",
            openingTime = System.currentTimeMillis()+1000*30,
            quizName = "Тест 1",
            questions = listOf(
                QuizQuestionData(
                    number = 1,
                    question = "Как зовут преподавателя",
                    answers = listOf(
                        "Бандергольф Штырьленгит",
                        "Бенадрил Трахтенберг",
                        "Баклажан Овервоч",
                        "Наиль Шавкятович",
                    ),
                    type= QuizQuestionData.QType.ONE,
                ),
                QuizQuestionData(
                    number = 2,
                    question = "Сколько будет 9 умножить на 9",
                    answers = listOf(
                        "81",
                        "91",
                        "72",
                        "99",
                    ),
                    type= QuizQuestionData.QType.ONE,
                ),
                QuizQuestionData(
                    number = 3,
                    question = "Какой набор критериев оценки игры против природы верен",
                    answers = listOf(
                        "MM, BL, S, HV",
                        "HL, G, P, BL",
                        "BM, HW, S, MM",
                        "HW, P, G, BL",
                    ),
                    type= QuizQuestionData.QType.MANY,
                ),
                QuizQuestionData(
                    number = 4,
                    question = "Что такое Гносеология",
                    answers = emptyList(),
                    type= QuizQuestionData.QType.TEXT,
                )
            )
        )
        )
    }
}
