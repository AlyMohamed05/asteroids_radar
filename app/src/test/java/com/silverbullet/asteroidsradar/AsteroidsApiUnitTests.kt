package com.silverbullet.asteroidsradar

import com.silverbullet.asteroidsradar.api.NasaAsteroidsApi
import com.silverbullet.asteroidsradar.utils.Constants
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AsteroidsApiUnitTests {

    private lateinit var api: NasaAsteroidsApi

    @Before
    fun setup() {
        api = Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaAsteroidsApi::class.java)
    }

    @Test
    fun `test image of day response`(){
        runBlocking {
            val response = api.fetchImageOfTheDay()
            val code = response.code()
            assertEquals(200,code)
            assertNotEquals(null,response.body())
            println(response.body())
        }
    }

    @Test
    fun `test fetch asteroids response`(){
        runBlocking {
            val response = api.fetchTodayAsteroids()
            val code = response.code()
            assertEquals(200,code)
            assertNotEquals(null,response.body())
            println(response.body()!!.string())
        }
    }

}