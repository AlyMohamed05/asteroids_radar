package com.silverbullet.asteroidsradar

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.silverbullet.asteroidsradar.database.AsteroidsDao
import com.silverbullet.asteroidsradar.database.AsteroidsRadarDatabase
import com.silverbullet.asteroidsradar.database.ImageOfTheDayDao
import com.silverbullet.asteroidsradar.utils.TestUtils
import com.silverbullet.asteroidsradar.utils.TimeUtils
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AsteroidsDatabaseTests {
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
    fun insertTest(){
        runBlocking {
            asteroidsDao.insertAll(TestUtils.generateTestAsteroidsList())
            val asteroids = asteroidsDao.getAll()
            assertEquals(10,asteroids.size)
            assertEquals("9",asteroids[9].id)
            assertEquals("Test Asteroid",asteroids[0].name)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testClear(){
        runBlocking {
            asteroidsDao.insertAll(TestUtils.generateTestAsteroidsList())
            val beforeClearAsteroids = asteroidsDao.getAll()
            assertEquals(10,beforeClearAsteroids.size)
            asteroidsDao.clearBeforeToday(TimeUtils.today)
            val afterClearAsteroids = asteroidsDao.getAll()
            assertEquals(0,afterClearAsteroids.size)
        }
    }

    // ImageOfTheDayDao tests

    @Test
    @Throws(Exception::class)
    fun testEmptyTableReturnsNull(){
        runBlocking {
            val image = imageOfTheDayDao.getImageOfTheDay()
            assertEquals(null,image)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testInsertImage(){
        runBlocking {
            imageOfTheDayDao.insertImageOfTheDay(TestUtils.generateTestImageOfTheDayList()[0])
            val image = imageOfTheDayDao.getImageOfTheDay()
            assertNotEquals(null,image)
            assertEquals("IMAGE2",image!!.copyright)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testTableHasMoreThanImage(){
        runBlocking {
            for(image in TestUtils.generateTestImageOfTheDayList()){
                imageOfTheDayDao.insertImageOfTheDay(image)
            }
            val image = imageOfTheDayDao.getImageOfTheDay()
            assertNotEquals(null,image)
            assertEquals("IMAGE2",image!!.copyright)
        }
    }

}