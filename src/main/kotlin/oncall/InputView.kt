package oncall

import camp.nextstep.edu.missionutils.*
import kotlin.NumberFormatException

class InputView {

    fun getDate(): Date {
        printOnCallMonthDayGuideMention()
        val date = Console.readLine()
        return try {
            validateDate(date)
        } catch (exception: IllegalArgumentException) {
            println(exception.message)
            getDate()
        }
    }

    fun printOnCallMonthDayGuideMention() = print("$ON_CALL_MONTH_DAY_INPUT_MENTION ")

    fun validateDate(date: String): Date {
        require(date.isNotEmpty()) { ERROR_UNVALID_INPUT }
        try {
            val (month, day) = date.split(',')
            require(month.isNotEmpty() && day.isNotEmpty()) { ERROR_UNVALID_INPUT }
            require(month.all { it.isDigit() }) { ERROR_UNVALID_INPUT }
            require(checkValidMonth(month)) { ERROR_UNVALID_INPUT }
            require(checkValidDay(day)) { ERROR_UNVALID_INPUT }
        } catch (exception: NumberFormatException) {
            throw IllegalArgumentException(ERROR_UNVALID_INPUT)
        }
        val (month, day) = date.split(',')
        return Date(month.toInt(), day)
    }

    fun checkValidMonth(month: String): Boolean = month.toInt() in 1..12

    fun checkValidDay(day: String): Boolean = dayList.contains(day)



    fun printWeekdayInputGuideMention() = print("$WEEKDAY_LIST_INPUT_MENTION ")

    fun printWeekendInputGuideMention() = print("$WEEKEND_LIST_INPUT_MENTION ")

    enum class DayOfWeek {
    }


    companion object {
        private val dayList = listOf("월", "화", "수", "목", "금", "토", "일")

        const val ERROR_UNVALID_INPUT = "[ERROR] 유효하지 않은 입력 값입니다. 다시 입력해 주세요."

        const val ON_CALL_MONTH_DAY_INPUT_MENTION = "비상 근무를 배정할 월과 시작 요일을 입력하세요>"
        const val WEEKDAY_LIST_INPUT_MENTION = "평일 비상 근무 순번대로 사원 닉네임을 입력하세요>"
        const val WEEKEND_LIST_INPUT_MENTION = "휴일 비상 근무 순번대로 사원 닉네임을 입력하세요>"
    }

}