package oncall.model

enum class Month(val month: Int, val numberOfDay: Int) {
    JAN(1, 31),
    FEB(2, 28),
    MAR(3, 31),
    APR(4, 30),
    MAY(5, 31),
    JUN(6, 30),
    JUL(7, 31),
    AUG(8, 31),
    SEP(9, 30),
    OCT(10, 31),
    NOV(11, 30),
    DEC(12, 31),
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