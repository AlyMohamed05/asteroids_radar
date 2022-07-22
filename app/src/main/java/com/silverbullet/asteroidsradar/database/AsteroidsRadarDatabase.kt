package com.silverbullet.asteroidsradar.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.silverbullet.asteroidsradar.model.Asteroid
import com.silverbullet.asteroidsradar.model.ImageOfDayResponse

@Database(
    entities = [Asteroid::class,ImageOfDayResponse::class],
    version = 1
)
abstract class AsteroidsRadarDatabase : RoomDatabase(){

    abstract fun getAsteroidsDao(): AsteroidsDao
    abstract fun getImageOfTheDayDao(): ImageOfTheDayDao
}