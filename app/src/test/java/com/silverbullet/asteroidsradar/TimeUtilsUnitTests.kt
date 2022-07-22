package com.silverbullet.asteroidsradar

import com.silverbullet.asteroidsradar.utils.TimeUtils
import org.junit.Assert.*
import org.junit.Test

class TimeUtilsUnitTests {

    @Test
    fun `shows the 7 days in test log`(){
        println(TimeUtils.upcomingWeek)
    }
}