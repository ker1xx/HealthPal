package com.example.healthpal.localdb.DAO

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.healthpal.localdb.DTO.workout.WorkoutDTO

@Dao
interface WorkoutDao {

    @Upsert
    suspend fun upsertWorkout(trainings: List<WorkoutDTO>)

    @Query("SELECT * FROM workout")
    fun getAllWorkouts(): List<WorkoutDTO>

    @Query("SELECT * FROM workout WHERE Intensity_Level == :intensity")
    fun getWorkoutsByIntensity(intensity: String) : List<WorkoutDTO>

    @Query("DELETE FROM workout")
    suspend fun clearAll()
}