package com.example.healthpal.localdb.DTO.workout

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.healthpal.localdb.helper.WorkoutEnums.WorkoutIntensityLevelTypeEnum
import com.example.healthpal.localdb.helper.WorkoutEnums.WorkoutMusclesTypesEnum

@Entity(tableName = "user_workout_progress")
data class UserWorkoutProgressDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val intensityLevelType: WorkoutIntensityLevelTypeEnum,
    val frequencyCompletingType: WorkoutMusclesTypesEnum,
    val frequencyTrainedMusclesType: WorkoutMusclesTypesEnum
)
