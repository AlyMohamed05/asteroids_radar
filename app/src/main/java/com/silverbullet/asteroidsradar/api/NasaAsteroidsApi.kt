package com.silverbullet.asteroidsradar.api

import com.silverbullet.asteroidsradar.model.ImageOfDayResponse
import com.silverbullet.asteroidsradar.utils.Constants
import com.silverbullet.asteroidsradar.utils.TimeUtils
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaAsteroidsApi {

    @GET("neo/rest/v1/feed")
    suspend fun fetchTodayAsteroids(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("start_date") startDate: String = TimeUtils.today,
    ): Response<ResponseBody>

    @GET("/planetary/apod")
    suspend fun fetchImageOfTheDay(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<ImageOfDayResponse>
}