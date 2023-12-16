package oncall.Controller

import oncall.Model.Date
import oncall.Model.Month
import oncall.View.InputView
import oncall.View.OutputView

class SchedulingSerivce {

    private val inputView = InputView()
    private val outputView = OutputView()

    init {
        weekdayCount = 0
        weekendCount = 0
    }

    fun run() {
        date = inputView.getDate()
        workerList = inputView.getWorkerList()

        outputView.printWorkingList(date, getDutyRoster(date, workerList))
    }

    fun getDutyRoster(date: Date, workerList: List<List<String>>): List<String> {
        val month = date.month
        val dutyList = mutableListOf<String>()
        for (i in 1..getMaxDateOfMonth(month)) {
            dutyList.add(getWorkerName(date, workerList, i))
        }
        return getValidDutyRoster(date, dutyList)
    }

    fun checkNoDoubleWorkingDay(dutyList: List<String>): Boolean {
        for (i in 0..<dutyList.size - 1) {
            if (dutyList[i] == dutyList[i + 1]) return false
        }
        return true
    }

    fun getValidDutyRoster(date: Date, dutyList: List<String>): List<String> {
        var validDutyList = dutyList.toMutableList()
        while (!checkNoDoubleWorkingDay(validDutyList)) {
            validDutyList = getModifiedDutyList(date, validDutyList).toMutableList()
        }
        return validDutyList.toList()
    }

    fun getModifiedDutyList(date: Date, dutyList: List<String>): List<String> {
        var modifiedDutyList = dutyList.toMutableList()
        val dateNum: Int = getDateOfDoubleWorkingDay(dutyList)
        if (checkIsHolidayOrWeekend(date, dateNum+1)) {
            modifiedDutyList = getModifiedListForWeekend(dutyList, dateNum).toMutableList()
        } else {
            modifiedDutyList = getModifiedListForWeekday(dutyList, dateNum).toMutableList()
        }
        return modifiedDutyList
    }

    fun getModifiedListForWeekend(dutyList: List<String>, dateNum: Int) : List<String> {
        val nextPerson = workerList[1][(workerList[1].indexOf(dutyList[dateNum])+1)% workerList[1].size]
        val modifiedDutyListForWeekend = dutyList.toMutableList()
        modifiedDutyListForWeekend[dateNum] = nextPerson
        for(i in dateNum+1 ..< getMaxDateOfMonth(date.month)) {
            if(checkIsHolidayOrWeekend(date, i) && dutyList[i] == nextPerson){
                modifiedDutyListForWeekend[i] = dutyList[dateNum]
                break
            }
        }
        return modifiedDutyListForWeekend.toList()
    }

    fun getModifiedListForWeekday(dutyList: List<String>, dateNum: Int) : List<String> {
        val nextPerson = workerList[0][(workerList[0].indexOf(dutyList[dateNum])+1)% workerList[0].size]
        val modifiedDutyListForWeekday = dutyList.toMutableList()
        modifiedDutyListForWeekday[dateNum] = nextPerson
        for(i in dateNum+1 ..< getMaxDateOfMonth(date.month)) {
            if(!checkIsHolidayOrWeekend(date, i) && dutyList[i] == nextPerson){
                modifiedDutyListForWeekday[i] = dutyList[dateNum]
                break
            }
        }
        return modifiedDutyListForWeekday
    }

    fun getDateOfDoubleWorkingDay(dutyList: List<String>): Int {
        var dateNum = 0
        for (i in 1..<dutyList.size) {
            if (dutyList[i] == dutyList[i - 1]) {
                dateNum = i
                break
            }
        }
        return dateNum
    }

    fun getWorkerName(date: Date, workerList: List<List<String>>, dateNum: Int): String {
        if (checkIsHolidayOrWeekend(date, dateNum)) return workerList[1][(weekendCount++) % workerList[1].size]
        return workerList[0][(weekdayCount++) % workerList[0].size]
    }

    fun checkIsHolidayOrWeekend(date: Date, dateNum: Int): Boolean {
        val day = getDayOfTheDate(date.day, dateNum)
        if (day == "토" || day == "일") return true
        if (date.checkIsHoliday(dateNum)) return true
        return false
    }

    fun getMaxDateOfMonth(month: Int): Int {
        return when (month) {
            1 -> Month.JAN.numberOfDay
            2 -> Month.FEB.numberOfDay
            3 -> Month.MAR.numberOfDay
            4 -> Month.APR.numberOfDay
            5 -> Month.MAY.numberOfDay
            6 -> Month.JUN.numberOfDay
            7 -> Month.JUL.numberOfDay
            8 -> Month.AUG.numberOfDay
            9 -> Month.SEP.numberOfDay
            10 -> Month.OCT.numberOfDay
            11 -> Month.NOV.numberOfDay
            12 -> Month.DEC.numberOfDay
            else -> 31
        }
    }

    fun getDayOfTheDate(day: String, dateNum: Int): String {
        val startingDay = dayList.indexOf(day)
        return dayList[(startingDay + dateNum - 1) % dayList.size]
    }

    companion object {
        private lateinit var date: Date
        private lateinit var workerList: List<List<String>>

        private val dayList = listOf("월", "화", "수", "목", "금", "토", "일")
        private var weekdayCount = 0
        private var weekendCount = 0
    }
}