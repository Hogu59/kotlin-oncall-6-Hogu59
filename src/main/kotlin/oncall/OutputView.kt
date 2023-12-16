package oncall

class OutputView {
    fun printOnCallMonthDayGuideMention() = println(ON_CALL_MONTH_DAY_INPUT_MENTION)

    companion object {
        const val ON_CALL_MONTH_DAY_INPUT_MENTION = "비상 근무를 배정할 월과 시작 요일을 입력하세요>"
    }
}