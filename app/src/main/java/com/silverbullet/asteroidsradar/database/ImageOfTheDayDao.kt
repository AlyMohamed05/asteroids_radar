package com.silverbullet.asteroidsradar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.silverbullet.asteroidsradar.model.ImageOfDayResponse

@Dao
interface ImageOfTheDayDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertImageOfTheDay(image: ImageOfDayResponse)

    @Query("SELECT * FROM image_of_the_day_table ORDER BY date DESC LIMIT 1")
    suspend fun getImageOfTheDay(): ImageOfDayResponse?
}