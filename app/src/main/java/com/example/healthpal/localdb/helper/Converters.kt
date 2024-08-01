package com.example.healthpal.localdb.helper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.example.healthpal.localdb.helper.WorkoutEnums.WorkoutEquipmentTypeEnum
import com.example.healthpal.localdb.helper.WorkoutEnums.WorkoutMusclesTypesEnum
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.util.Date
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


class Converters {

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromDuration(value: Long?): Duration? {
        return value?.toDuration(DurationUnit.MILLISECONDS)
    }

    @TypeConverter
    fun durationToLong(duration: kotlin.time.Duration?): Long? {
        return duration?.inWholeMilliseconds
    }
}