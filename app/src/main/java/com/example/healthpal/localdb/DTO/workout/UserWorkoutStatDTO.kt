package com.example.healthpal.localdb.DTO.workout

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.healthpal.localdb.helper.Converters
import java.util.Date
import kotlin.time.Duration

@Entity(tableName = "user_workout_stat")
@TypeConverters(Converters::class)
data class UserWorkoutStatDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val date: Date,
    val duration: Long,
    val calories: Int
)
