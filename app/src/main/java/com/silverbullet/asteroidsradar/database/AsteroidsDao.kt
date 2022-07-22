package com.silverbullet.asteroidsradar.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.silverbullet.asteroidsradar.model.Asteroid

@Dao
interface AsteroidsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(asteroids: List<Asteroid>)

    @Query("SELECT * FROM asteroids_table ORDER BY closeApproachDate ASC")
    fun getAsteroidsLive(): LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroids_table")
    suspend fun getAll(): List<Asteroid>

    /**
     * This function deletes all asteroids with date before today
     */
    @Query("DELETE FROM asteroids_table WHERE closeApproachDate < :today")
    suspend fun clearBeforeToday(today: String)
}