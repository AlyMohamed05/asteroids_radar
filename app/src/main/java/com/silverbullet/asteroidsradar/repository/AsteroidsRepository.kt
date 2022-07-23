package com.silverbullet.asteroidsradar.repository

import androidx.lifecycle.LiveData
import com.silverbullet.asteroidsradar.model.Asteroid
import com.silverbullet.asteroidsradar.model.ImageOfDayResponse
import com.silverbullet.asteroidsradar.repository.AsteroidsRepositoryImpl.RefreshType
import com.silverbullet.asteroidsradar.repository.AsteroidsRepositoryImpl.RefreshContentEvent

interface AsteroidsRepository {

    val asteroidsList: LiveData<List<Asteroid>>
    val imageOfTheDay: LiveData<ImageOfDayResponse?>

    suspend fun refreshContent(refreshType: RefreshType = RefreshType.ALL): RefreshContentEvent
}