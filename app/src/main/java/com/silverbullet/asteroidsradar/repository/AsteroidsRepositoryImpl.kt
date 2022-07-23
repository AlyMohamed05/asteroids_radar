package com.silverbullet.asteroidsradar.repository

import androidx.lifecycle.LiveData
import com.silverbullet.asteroidsradar.api.NasaAsteroidsApi
import com.silverbullet.asteroidsradar.database.AsteroidsDao
import com.silverbullet.asteroidsradar.database.ImageOfTheDayDao
import com.silverbullet.asteroidsradar.model.Asteroid
import com.silverbullet.asteroidsradar.model.ImageOfDayResponse
import com.silverbullet.asteroidsradar.utils.ParseUtils
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class AsteroidsRepositoryImpl @Inject constructor(
    private val asteroidsApi: NasaAsteroidsApi,
    private val asteroidsDao: AsteroidsDao,
    private val imageOfTheDayDao: ImageOfTheDayDao
) : AsteroidsRepository {

    override val asteroidsList: LiveData<List<Asteroid>>
        get() = asteroidsDao.getAsteroidsLive()
    override val imageOfTheDay: LiveData<ImageOfDayResponse?>
        get() = imageOfTheDayDao.getImageOfTheDayLive()

    override suspend fun refreshContent(refreshType: RefreshType): RefreshContentEvent {
         return withContext(coroutineContext) {
            when (refreshType) {
                RefreshType.ALL -> {
                    // TODO : replace the return value with a real working one
                    val imageOfTheDayRefreshAsync =async { refreshImageOfTheDay() }
                    val asteroidsListRefreshAsync = async { refreshAsteroidsList() }
                    val asteroidsListRefreshEvent = asteroidsListRefreshAsync.await()
                    val imageOfTheDayRefreshEvent = imageOfTheDayRefreshAsync.await()
                    return@withContext imageOfTheDayRefreshEvent
                }
                RefreshType.ASTEROIDS -> {
                    return@withContext refreshAsteroidsList()
                }
                RefreshType.IMAGE -> {
                    return@withContext refreshImageOfTheDay()
                }
            }
        }
    }

    private suspend fun refreshAsteroidsList(): RefreshContentEvent {
        val event = try {
            val response = asteroidsApi.fetchTodayAsteroids()
            if (response.code() != 200 || response.body() == null) {
                throw java.lang.Exception(response.message())
            }
            val asteroidsListResult = runCatching {
                val asteroidsListJson = response.body()!!.string()
                response.body()!!.close()
                ParseUtils.parseJsonToAsteroidsList(asteroidsListJson)
            }
            if(asteroidsListResult.isSuccess){
                val asteroids =asteroidsListResult.getOrThrow()
                asteroidsDao.insertAll(asteroids)
                RefreshContentEvent.SUCCESS
            }else{
                RefreshContentEvent.ParsingError
            }
        } catch (e: IOException) {
            // No Internet
            RefreshContentEvent.NoInternet
        }
        Timber.d("Asteroids Refresh Call Event : $event")
        return event
    }

    private suspend fun refreshImageOfTheDay() : RefreshContentEvent{
        val event = try{
            val response = asteroidsApi.fetchImageOfTheDay()
            if (response.code() != 200 || response.body() == null) {
                throw java.lang.Exception(response.message())
            }
            val imageOfTheDay = response.body()!!
            imageOfTheDayDao.insertImageOfTheDay(imageOfTheDay)
            RefreshContentEvent.SUCCESS
        }catch (e: IOException){
            RefreshContentEvent.NoInternet
        }
        Timber.d("Image of The Day Call Event : $event")
        return event
    }

    sealed class RefreshContentEvent {

        object SUCCESS: RefreshContentEvent()

        object ParsingError: RefreshContentEvent()

        object NoInternet: RefreshContentEvent()
    }

    sealed class RefreshType {

        // Refreshes the asteroids list and image of the day
        object ALL : RefreshType()

        // Refreshes the asteroids list only
        object ASTEROIDS : RefreshType()

        // Refreshes Image of the day only
        object IMAGE : RefreshType()
    }
}