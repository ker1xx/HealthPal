package com.example.healthpal.remote.service.workout

import com.example.healthpal.remote.data.workout.respone.WorkoutList
import retrofit2.http.GET

fun interface WorkoutApi {

    @GET("search")
    suspend fun getWorkouts(): WorkoutList
}