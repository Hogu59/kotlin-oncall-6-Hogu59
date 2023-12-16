package oncall.Model

class Date(val month: Int, var day: String) {
    fun checkIsHoliday(date: Int): Boolean = holidayList.contains(listOf(month, date))

    fun getDayOfTheDate(day: String, dateNum: Int): String {
        val startingDay = dayList.indexOf(day)
        return dayList[(startingDay + dateNum - 1) % dayList.size]
    }

    companion object {
        val holidayList = listOf(
            listOf(1, 1),
            listOf(3, 1),
            listOf(5, 5),
            listOf(5, 5),
            listOf(8, 15),
            listOf(10, 3),
            listOf(10, 9),
            listOf(12, 25),
        )

        private val dayList = listOf("월", "화", "수", "목", "금", "토", "일")
    }
}