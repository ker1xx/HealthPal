package com.example.healthpal.remote.service.workout

import androidx.room.withTransaction
import com.example.healthpal.localdb.HealthPalDatabase
import com.example.healthpal.localdb.mappers.toWorkoutDTO
import com.example.healthpal.remote.data.workout.respone.WorkoutList
import com.example.healthpal.utility.Resource
import javax.inject.Inject

class WorkoutRepository @Inject constructor(
    private val api: WorkoutApi
) {
    suspend fun getWorkouts(db: HealthPalDatabase): Resource<WorkoutList> {
        val response = try {
            api.getWorkouts()
        } catch (e: Exception){
            return Resource.Error("An unknown error.")
        }
        db.withTransaction {
            db.workoutDao().clearAll()
            val workoutDTOs = response.workoutItem.map {
                it.toWorkoutDTO()
            }
            db.workoutDao().upsertWorkout(workoutDTOs)
        }
        return Resource.Success(response)
    }
}