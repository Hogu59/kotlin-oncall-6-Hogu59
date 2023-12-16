package oncall

import oncall.controller.SchedulingSerivce

fun main() {
    val schedulingService = SchedulingSerivce()
    schedulingService.run()
}
