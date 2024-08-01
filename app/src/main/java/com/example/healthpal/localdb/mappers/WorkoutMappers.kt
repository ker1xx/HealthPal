package com.example.healthpal.localdb.mappers

import com.example.healthpal.R
import com.example.healthpal.localdb.DTO.workout.WorkoutDTO
import com.example.healthpal.localdb.helper.WorkoutEnums.WorkoutEquipmentTypeEnum
import com.example.healthpal.localdb.helper.WorkoutEnums.WorkoutIntensityLevelTypeEnum
import com.example.healthpal.localdb.helper.WorkoutEnums.WorkoutMusclesTypesEnum
import com.example.healthpal.remote.data.workout.respone.WorkoutItem
import com.example.healthpal.utility.backgroundSetter

fun WorkoutDTO.toWorkoutItem(): WorkoutItem {
    return WorkoutItem(
        Beginner_Sets = Beginner_Sets,
        Equipment = when (Equipment) {
            WorkoutEquipmentTypeEnum.Barbell -> "Barbell"
            WorkoutEquipmentTypeEnum.Bench -> "Bench"
            WorkoutEquipmentTypeEnum.EZBar -> "EZBar"
            WorkoutEquipmentTypeEnum.kettlebell -> "kettlebell"
            WorkoutEquipmentTypeEnum.Dumbbells -> "Dumbbells"
            WorkoutEquipmentTypeEnum.ChestPressMachine -> "Chest press machine"
            null -> ""
        },
        Expert_Sets = Expert_Sets,
        Explaination = Explaination,
        Intensity_Level = when (Intensity_Level) {
            WorkoutIntensityLevelTypeEnum.Beginner -> "Beginner"
            WorkoutIntensityLevelTypeEnum.Intermediate -> "Intermediate"
            WorkoutIntensityLevelTypeEnum.Expert -> "Expert"
        },
        Intermediate_Sets = Intermediate_Sets,
        Long_Explanation = Long_Explanation,
        Muscles = when (Muscles) {
            WorkoutMusclesTypesEnum.Biceps -> "Biceps"
            WorkoutMusclesTypesEnum.Triceps -> "Triceps"
            WorkoutMusclesTypesEnum.Chest -> "Chest"
            WorkoutMusclesTypesEnum.Back -> "Back"
            WorkoutMusclesTypesEnum.Legs -> "Legs"
            WorkoutMusclesTypesEnum.Abs -> "Abs"
            WorkoutMusclesTypesEnum.Stretching -> "Stretching"
            WorkoutMusclesTypesEnum.WarmUp -> "Warm Up"
            WorkoutMusclesTypesEnum.Lats -> "Lats"
            WorkoutMusclesTypesEnum.Hamstring -> "Hamstring"
            WorkoutMusclesTypesEnum.Calves -> "Calves"
            WorkoutMusclesTypesEnum.Quadriceps -> "Quadriceps"
            WorkoutMusclesTypesEnum.Trapezius -> "Trapezius"
            WorkoutMusclesTypesEnum.Shoulders -> "Shoulders"
            WorkoutMusclesTypesEnum.Glutes -> "Glutes"
        },
        Video = Video,
        WorkOut = WorkOut
    )
}

fun WorkoutItem.toWorkoutDTO(): WorkoutDTO {
    return WorkoutDTO(
        Id = null,
        Beginner_Sets = Beginner_Sets,
        Equipment = when (Equipment) {
            "Barbell" -> WorkoutEquipmentTypeEnum.Barbell
            "Bench" -> WorkoutEquipmentTypeEnum.Bench
            "EZBar" -> WorkoutEquipmentTypeEnum.EZBar
            "kettlebell" -> WorkoutEquipmentTypeEnum.kettlebell
            "Dumbbells" -> WorkoutEquipmentTypeEnum.Dumbbells
            "Chest press machine" -> WorkoutEquipmentTypeEnum.ChestPressMachine
            else -> null
        },
        Expert_Sets = Expert_Sets,
        Explaination = Explaination,
        Intensity_Level = when (Intensity_Level) {
            "Beginner" -> WorkoutIntensityLevelTypeEnum.Beginner
            "Intermediate" -> WorkoutIntensityLevelTypeEnum.Intermediate
            "Expert" -> WorkoutIntensityLevelTypeEnum.Expert
            else -> WorkoutIntensityLevelTypeEnum.Beginner
        },
        Intermediate_Sets = Intermediate_Sets,
        Long_Explanation = Long_Explanation,
        Muscles = when (Muscles) {
            "Biceps" -> WorkoutMusclesTypesEnum.Biceps
            "Triceps" -> WorkoutMusclesTypesEnum.Triceps
            "Chest" -> WorkoutMusclesTypesEnum.Chest
            "Back" -> WorkoutMusclesTypesEnum.Back
            "Legs" -> WorkoutMusclesTypesEnum.Legs
            "Abs" -> WorkoutMusclesTypesEnum.Abs
            "Stretching" -> WorkoutMusclesTypesEnum.Stretching
            "Warm Up" -> WorkoutMusclesTypesEnum.WarmUp
            "Lats" -> WorkoutMusclesTypesEnum.Lats
            "Hamstring" -> WorkoutMusclesTypesEnum.Hamstring
            "Calves" -> WorkoutMusclesTypesEnum.Calves
            "Quadriceps" -> WorkoutMusclesTypesEnum.Quadriceps
            "Trapezius" -> WorkoutMusclesTypesEnum.Trapezius
            "Shoulders" -> WorkoutMusclesTypesEnum.Shoulders
            "Glutes" -> WorkoutMusclesTypesEnum.Glutes
            else -> WorkoutMusclesTypesEnum.Biceps
        },
        Video = Video,
        WorkOut = WorkOut,
        Image = when (Muscles) {
            "Biceps" -> if (backgroundSetter.IsLightTheme)
                R.drawable.biceps_ic_white_96
            else
                R.drawable.biceps_ic_black_96
            "Triceps" -> if (backgroundSetter.IsLightTheme)
                R.drawable.triceps_ic_white_96
            else
                R.drawable.triceps_ic_black_96
            "Chest" -> if (backgroundSetter.IsLightTheme)
                R.drawable.chest_ic_white_96
            else
                R.drawable.chest_ic_black_96
            "Back" -> if (backgroundSetter.IsLightTheme)
                R.drawable.prelum_ic_white_96
            else
                R.drawable.prelum_ic_black_96
            "Legs" -> if (backgroundSetter.IsLightTheme)
                R.drawable.calves_ic_white_96
            else
                R.drawable.calves_ic_black_96
            "Abs" -> if (backgroundSetter.IsLightTheme)
                R.drawable.abs_ic_white_96
            else
                R.drawable.abs_ic_black_96
            "Stretching" -> if (backgroundSetter.IsLightTheme)
                R.drawable.stretching_ic_white_96
            else
                R.drawable.stretching_ic_black_96
            "Warm Up" -> if (backgroundSetter.IsLightTheme)
                R.drawable.warm_up_ic_white_96
            else
                R.drawable.warm_up_ic_black_96
            "Lats" -> if (backgroundSetter.IsLightTheme)
                R.drawable.lat_pulldown_ic_white_96
            else
                R.drawable.lat_pulldown_ic_black_96
            "Hamstring" -> if (backgroundSetter.IsLightTheme)
                R.drawable.hamstrings_ic_white_96
            else
                R.drawable.hamstrings_ic_black_96
            "Calves" -> if (backgroundSetter.IsLightTheme)
                R.drawable.calves_ic_white_96
            else
                R.drawable.calves_ic_black_96
            "Quadriceps" -> if (backgroundSetter.IsLightTheme)
                R.drawable.quadriceps_ic_white_96
            else
                R.drawable.quadriceps_ic_black_96
            "Trapezius" -> if (backgroundSetter.IsLightTheme)
                R.drawable.trapezius_ic_white_96
            else
                R.drawable.trapezius_ic_black_96
            "Shoulders" -> if (backgroundSetter.IsLightTheme)
                R.drawable.shoulder_ic_white_96
            else
                R.drawable.shoulders_ic_black_96
            "Glutes" -> if (backgroundSetter.IsLightTheme)
                R.drawable.glutes_ic_white_96
            else
                R.drawable.glutes_ic_black_96
            else -> R.drawable.dumbbell
        }
    )
}
