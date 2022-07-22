package com.silverbullet.asteroidsradar.utils

import com.silverbullet.asteroidsradar.model.Asteroid

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
                    "date",
                    33.0,
                    33.0
                )
            )
        }
        return result
    }
}