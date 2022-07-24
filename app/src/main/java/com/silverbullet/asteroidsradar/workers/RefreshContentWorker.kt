package com.silverbullet.asteroidsradar.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.silverbullet.asteroidsradar.database.AsteroidsDao
import com.silverbullet.asteroidsradar.database.ImageOfTheDayDao
import com.silverbullet.asteroidsradar.repository.AsteroidsRepository
import com.silverbullet.asteroidsradar.repository.AsteroidsRepositoryImpl
import com.silverbullet.asteroidsradar.utils.TimeUtils
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class RefreshContentWorker @AssistedInject constructor(
    @Assisted appContext:Context,
    @Assisted workerParams: WorkerParameters,
    private val imageOfTheDayDao: ImageOfTheDayDao,
    private val asteroidsDao: AsteroidsDao,
    private val repository: AsteroidsRepository
): CoroutineWorker(appContext,workerParams){

    companion object{
        const val WORKER_NAME = "Refresh Content"
    }

    override suspend fun doWork(): Result {
        asteroidsDao.clearBeforeToday(TimeUtils.today)
        imageOfTheDayDao.clearBeforeToday(TimeUtils.today)
        if(repository.refreshContent()==AsteroidsRepositoryImpl.RefreshContentEvent.SUCCESS) {
            return Result.Success()
        }
        return Result.failure()
    }
}