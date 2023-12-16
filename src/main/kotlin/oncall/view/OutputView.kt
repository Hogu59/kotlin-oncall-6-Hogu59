package oncall.view

import oncall.model.Date
import oncall.model.getMaxDateOfMonth

class OutputView {
    fun printWorkingList(date: Date, dutyRoster: List<String>) {
        val month = date.month
        val day = date.day
        for (i in 1..getMaxDateOfMonth(month)) {
            println("${month}월 ${i}일 ${date.getDayOfTheDate(day, i)}${getHolidayMark(date, i)} ${dutyRoster[i-1]}")
        }
    }

    private fun getHolidayMark(date: Date, dateNum: Int): String {
        if(date.getDayOfTheDate(date.day, dateNum) != "토" && date.getDayOfTheDate(date.day, dateNum) != "일" && date.checkIsHoliday(dateNum)) return "(휴일)"
        return ""
    }
}