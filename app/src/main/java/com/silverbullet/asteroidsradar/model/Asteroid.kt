package com.silverbullet.asteroidsradar.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "asteroids_table")
data class Asteroid(
    @PrimaryKey val id: String,
    val name: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val isPotentiallyHazardous: Boolean,
    val closeApproachDate: String,
    val relativeVelocity: Double,
    val distanceFromEarth: Double
): Serializable
