package com.silverbullet.asteroidsradar

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.silverbullet.asteroidsradar.database.AsteroidsDao
import com.silverbullet.asteroidsradar.database.AsteroidsRadarDatabase
import com.silverbullet.asteroidsradar.database.ImageOfTheDayDao
import com.silverbullet.asteroidsradar.utils.TestUtils
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AsteroidsRadarDatabaseUnitTest {

    private lateinit var database: AsteroidsRadarDatabase
    private lateinit var asteroidsDao: AsteroidsDao
    private lateinit var imageOfTheDayDao: ImageOfTheDayDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AsteroidsRadarDatabase::class.java)
            .build()
        asteroidsDao = database.getAsteroidsDao()
        imageOfTheDayDao = database.getImageOfTheDayDao()
    }

    @After
    @Throws(IOException::class)
    fun clear(){
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun `test inserting to asteroids db`(){
        runBlocking {
            asteroidsDao.insertAll(TestUtils.generateTestAsteroidsList())
            val asteroids = asteroidsDao.getAll()
            assertEquals(10,asteroids.size)
            assertEquals(9,asteroids[9])
            assertEquals("Test Asteroid",asteroids[0])
        }
    }
}