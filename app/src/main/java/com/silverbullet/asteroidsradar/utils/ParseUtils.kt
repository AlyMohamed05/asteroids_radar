package com.silverbullet.asteroidsradar.utils

import com.silverbullet.asteroidsradar.model.Asteroid
import org.json.JSONObject

object ParseUtils {

    /**
     * This function takes json string response from Nasa Asteroids Neows and parse and returns
     * list of asteroids.
     */
    fun parseJsonToAsteroidsList(jsonResponse: String): List<Asteroid> {
        val result = mutableListOf<Asteroid>()
        val nearEarthObjects = JSONObject(jsonResponse).getJSONObject("near_earth_objects")
        // Lookup for each day in the response and parse it if exists
        for (day in TimeUtils.upcomingWeek) {
            // day exists in the data and we need parse all asteroids coming in that day
            if (nearEarthObjects.has(day)) {
                // get all asteroids in that day
                val asteroidsJsonList = nearEarthObjects.getJSONArray(day)
                // parse every asteroids in the asteroids json list
                for (i in 0 until asteroidsJsonList.length()) {
                    val asteroidJsonObject = asteroidsJsonList.getJSONObject(i)
                    val asteroid = getAsteroidFromJsonObject(asteroidJsonObject)
                    result.add(asteroid)
                }
            }
        }
        return result.toList()
    }

    private fun getAsteroidFromJsonObject(asteroidJsonObject: JSONObject): Asteroid {
        val id = asteroidJsonObject.getString("id")
        val name = asteroidJsonObject.getString("name")
        val absoluteMagnitude = asteroidJsonObject.getDouble("absolute_magnitude_h")
        val estimatedDiameter = asteroidJsonObject
            .getJSONObject("estimated_diameter")
            .getJSONObject("kilometers")
            .getDouble("estimated_diameter_max")
        val isPotentiallyHazardous =
            asteroidJsonObject.getBoolean("is_potentially_hazardous_asteroid")
        val approachData = asteroidJsonObject.getJSONArray("close_approach_data").getJSONObject(0)
        val closeApproachDate = approachData.getString("close_approach_date")
        val relativeVelocity =
            approachData.getJSONObject("relative_velocity").getDouble("kilometers_per_second")
        val distanceFromEarth =
            approachData.getJSONObject("miss_distance").getDouble("astronomical")
        return Asteroid(
            id,
            name,
            absoluteMagnitude,
            estimatedDiameter,
            isPotentiallyHazardous,
            closeApproachDate,
            relativeVelocity,
            distanceFromEarth
        )
    }
}