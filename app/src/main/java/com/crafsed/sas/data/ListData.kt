package com.crafsed.sas.data

data class ListData(
    val obj: String,
    val lector: String,
    val room: String,
    val status: String,
    val time: String,
    val isOnline: Boolean = false
) {
    companion object {
        val TEST = listOf(
            ListData(
                obj = "пр.Безопасность жизнедеятельности",
                lector = "Булыга Ф. С.",
                room = "LMS",
                status = "(прошла)",
                time = "08:00 - 09:35",
                isOnline = true
            ),
            ListData(
                obj = "пр.Философия",
                lector = "Лысак И. В.",
                room = "Д-312",
                status = "прошла",
                time = "09:50 - 11:25",
                isOnline = false
            ),
            ListData(
                obj = "лаб.Программирование компьтерной графики Г-425",
                lector = "Гуляев Н. А.",
                room = "LMS",
                status = "(идёт)",
                time = "11:55 - 13:30",
                isOnline = false
            ),
            ListData(
                obj = "лаб.Программирование компьтерной графики Г-425",
                lector = "Гуляев Н. А.",
                room = "Г-425",
                status = "(да начала 10 минут)",
                time = "13:45 - 15:20",
                isOnline = false
            ),
            ListData(
                obj = "пр.Безопасность жизнедеятельности",
                lector = "Булыга Ф. С.",
                room = "А-101",
                status = "до начала 2 часа",
                time = "15:50 - 17:25",
                isOnline = true
            ),
            ListData(
                obj = "пр.Безопасность жизнедеятельности",
                lector = "Булыга Ф. С.",
                room = "LMS",
                status = "до начала 3 часа 30 минут",
                time = "17:40 - 19:15",
                isOnline = true
            ),
        )
    }
}