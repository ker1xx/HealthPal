package com.example.healthpal.remote.DI

import com.example.healthpal.remote.service.workout.WorkoutApi
import com.example.healthpal.remote.service.workout.WorkoutInterceptor
import com.example.healthpal.remote.service.workout.WorkoutRepository
import com.example.healthpal.utility.Constants.WORKOUT_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkoutDependencyInjection {

    @Singleton
    @Provides
    fun provideWorkoutRepository(
        api: WorkoutApi
    ) = WorkoutRepository(api)

    @Singleton
    @Provides
    fun provideWorkoutAPI(): WorkoutApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(
                        HttpLoggingInterceptor
                            .Level
                            .BODY
                    )
            )
            .apply {
                addInterceptor(WorkoutInterceptor())
            }
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(WORKOUT_BASE_URL)
            .client(client)
            .build()
            .create(WorkoutApi::class.java)
    }
}