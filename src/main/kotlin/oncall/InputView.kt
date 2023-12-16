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
            require(date.split(',').size == 2) { ERROR_UNVALID_INPUT }
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

    fun getWorkerList(): List<List<String>> {
        val workingList = mutableListOf<List<String>>()
        return try {
            workingList.add(getWeekDayList())
            workingList.add(getWeekendList())
            workingList
        } catch (exception: IllegalArgumentException) {
            println(exception.message)
            getWorkerList()
        }
    }

    fun getWeekDayList(): List<String> {
        printWeekdayInputGuideMention()
        val weekdayList = Console.readLine()
        return try {
            getValidList(nameList = weekdayList)
        } catch (exception: IllegalArgumentException) {
            throw IllegalArgumentException(ERROR_UNVALID_INPUT)
        }
    }

    fun getWeekendList(): List<String> {
        printWeekendInputGuideMention()
        val weekendList = Console.readLine()
        return try {
            getValidList(nameList = weekendList)
        } catch (exception: IllegalArgumentException) {
            throw IllegalArgumentException(ERROR_UNVALID_INPUT)
        }
    }

    fun getValidList(nameList: String): List<String> {
        require(nameList.isNotEmpty() && !nameList.contains(" ")) { ERROR_UNVALID_INPUT }
        try {
            val validNameList = nameList.split(',')
            //입력 인원이 5명 미만 35명 초과인 경우
            require(validNameList.size in 5..35) { ERROR_UNVALID_INPUT }
            //이름 중복인 경우
            require(validNameList.size == validNameList.distinct().size) { ERROR_UNVALID_INPUT }
            require(checkValidNameList(validNameList)) { ERROR_UNVALID_INPUT }
        } catch (exception: Exception) {
            throw IllegalArgumentException(ERROR_UNVALID_INPUT)
        }
        return nameList.split(',')
    }

    private fun checkValidNameList(validNameList: List<String>): Boolean {
        for (i in validNameList.indices)
            if (!checkValidName(validNameList[i])) return false
        return true
    }

    private fun checkValidName(name: String): Boolean = name.length in 1..5

    private fun printWeekdayInputGuideMention() = print("$WEEKDAY_LIST_INPUT_MENTION ")

    private fun printWeekendInputGuideMention() = print("$WEEKEND_LIST_INPUT_MENTION ")


    companion object {
        private val dayList = listOf("월", "화", "수", "목", "금", "토", "일")

        const val ERROR_UNVALID_INPUT = "[ERROR] 유효하지 않은 입력 값입니다. 다시 입력해 주세요."

        const val ON_CALL_MONTH_DAY_INPUT_MENTION = "비상 근무를 배정할 월과 시작 요일을 입력하세요>"
        const val WEEKDAY_LIST_INPUT_MENTION = "평일 비상 근무 순번대로 사원 닉네임을 입력하세요>"
        const val WEEKEND_LIST_INPUT_MENTION = "휴일 비상 근무 순번대로 사원 닉네임을 입력하세요>"
    }

}