package com.example.healthpal.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.healthpal.localdb.DAO.UserDataDAO
import com.example.healthpal.localdb.DAO.UserWorkoutProgressDAO
import com.example.healthpal.localdb.DAO.UserWorkoutStatDAO
import com.example.healthpal.localdb.DAO.WorkoutDao
import com.example.healthpal.localdb.DTO.UserDataDTO
import com.example.healthpal.localdb.DTO.workout.UserWorkoutProgressDTO
import com.example.healthpal.localdb.DTO.workout.UserWorkoutStatDTO
import com.example.healthpal.localdb.DTO.workout.WorkoutDTO
import com.example.healthpal.localdb.helper.Converters

@Database(
    entities = [
        WorkoutDTO::class,
        UserDataDTO::class,
        UserWorkoutStatDTO::class,
        UserWorkoutProgressDTO::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class HealthPalDatabase : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao
    abstract fun userDataDao(): UserDataDAO
    abstract fun userWorkoutStatDAO(): UserWorkoutStatDAO
    abstract fun userWorkoutProgressDAO(): UserWorkoutProgressDAO

    companion object {
        @Volatile
        private var INSTANCE: HealthPalDatabase? = null

        fun getDbInstance(context: Context): HealthPalDatabase {
            return INSTANCE ?: synchronized(this)
            {
                val inst = Room.databaseBuilder(
                    context.applicationContext,
                    HealthPalDatabase::class.java,
                    "HealthpalDatabase"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = inst
                inst
            }
        }

    }
}
