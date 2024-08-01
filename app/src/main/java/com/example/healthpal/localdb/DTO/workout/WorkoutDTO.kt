package com.example.healthpal.localdb.DTO.workout

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.healthpal.localdb.helper.WorkoutEnums.WorkoutEquipmentTypeEnum
import com.example.healthpal.localdb.helper.WorkoutEnums.WorkoutIntensityLevelTypeEnum
import com.example.healthpal.localdb.helper.WorkoutEnums.WorkoutMusclesTypesEnum

@Entity(tableName = "workout")
data class WorkoutDTO(
    @PrimaryKey(autoGenerate = true)
    val Id: Int?,
    val Beginner_Sets: String,
    val Equipment: WorkoutEquipmentTypeEnum?,
    val Expert_Sets: String,
    val Explaination: String,
    val Intensity_Level: WorkoutIntensityLevelTypeEnum,
    val Intermediate_Sets: String,
    val Long_Explanation: String,
    val Muscles: WorkoutMusclesTypesEnum,
    val Video: String,
    val WorkOut: String,
    val Image: Int
)
