package com.example.healthpal.localdb.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.healthpal.localdb.DTO.workout.UserWorkoutStatDTO

@Dao
interface UserWorkoutStatDAO {

    @Insert
    suspend fun insertUserWorkoutStat(userWorkoutStatDTO: UserWorkoutStatDTO)

    @Query("SELECT * FROM user_workout_stat")
    fun getAllUserWorkoutStat() : List<UserWorkoutStatDTO>
}