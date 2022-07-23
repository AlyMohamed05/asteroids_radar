package com.silverbullet.asteroidsradar.di

import android.content.Context
import androidx.room.Room
import com.silverbullet.asteroidsradar.api.NasaAsteroidsApi
import com.silverbullet.asteroidsradar.database.AsteroidsDao
import com.silverbullet.asteroidsradar.database.AsteroidsRadarDatabase
import com.silverbullet.asteroidsradar.database.ImageOfTheDayDao
import com.silverbullet.asteroidsradar.repository.AsteroidsRepository
import com.silverbullet.asteroidsradar.repository.AsteroidsRepositoryImpl
import com.silverbullet.asteroidsradar.utils.Constants.API_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
object HomeModule {

    @Singleton
    @Provides
    fun provideAsteroidsRadarApi(): NasaAsteroidsApi {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaAsteroidsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAsteroidsRadarDatabase(@ApplicationContext context: Context): AsteroidsRadarDatabase {
        return Room.databaseBuilder(
            context,
            AsteroidsRadarDatabase::class.java,
            "AsteroidsRadarDB.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideAsteroidsDao(db: AsteroidsRadarDatabase): AsteroidsDao {
        return db.getAsteroidsDao()
    }

    @Singleton
    @Provides
    fun provideImageOfTheDayDao(db: AsteroidsRadarDatabase): ImageOfTheDayDao {
        return db.getImageOfTheDayDao()
    }

    @Singleton
    @Provides
    fun provideAsteroidsRepository(
        api: NasaAsteroidsApi,
        asteroidsDao: AsteroidsDao,
        imageOfTheDayDao: ImageOfTheDayDao
    ): AsteroidsRepository {
        return AsteroidsRepositoryImpl(api, asteroidsDao, imageOfTheDayDao)
    }
}