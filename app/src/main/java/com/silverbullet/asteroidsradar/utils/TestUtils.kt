package com.silverbullet.asteroidsradar.utils

import com.silverbullet.asteroidsradar.model.Asteroid
import com.silverbullet.asteroidsradar.model.ImageOfDayResponse

object TestUtils {

    /**
     * Return a list of 10 Asteroids starting by id 0
     */
    fun generateTestAsteroidsList(): List<Asteroid> {
        val result = mutableListOf<Asteroid>()
        repeat(10) {
            result.add(
                Asteroid(
                    it.toString(),
                    "Test Asteroid",
                    33.0,
                    33.0,
                    false,
                    "2022-07-03",
                    33.0,
                    33.0
                )
            )
        }
        return result
    }

    /**
     * Return a list of 2 images with 2 different date
     */
    fun generateTestImageOfTheDayList(): List<ImageOfDayResponse>{
        return listOf(
            ImageOfDayResponse("IMAGE2","2022-07-2","","","","","",""),
            ImageOfDayResponse("IMAGE1","2022-07-1","","","","","","")
        )
    }
}