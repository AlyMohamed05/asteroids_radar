package com.silverbullet.asteroidsradar

import android.app.Application
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.silverbullet.asteroidsradar.workers.RefreshContentWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class AsteroidsApp : Application(), Configuration.Provider {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        setupRefreshingWorker()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    private fun setupRefreshingWorker(){
        applicationScope.launch {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .setRequiresCharging(true)
                .apply {
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                        setRequiresDeviceIdle(true)
                    }
                }.build()
            val request = PeriodicWorkRequestBuilder<RefreshContentWorker>(1,TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()
            WorkManager.getInstance(this@AsteroidsApp).enqueueUniquePeriodicWork(
                RefreshContentWorker.WORKER_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}