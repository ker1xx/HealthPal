package com.example.healthpal.localdb.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.healthpal.localdb.DTO.workout.UserWorkoutProgressDTO

@Dao
interface UserWorkoutProgressDAO {

    @Insert
    suspend fun insertUserWorkoutProgress(workoutProgressDTO: UserWorkoutProgressDTO)

    @Query("SELECT * FROM user_workout_progress")
    fun getAllUserWorkoutProgress() : UserWorkoutProgressDTO

}