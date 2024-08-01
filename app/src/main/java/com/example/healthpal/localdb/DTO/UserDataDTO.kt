package com.example.healthpal.localdb.DTO

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.healthpal.localdb.helper.Converters

@Entity(tableName = "user_data")
@TypeConverters(Converters::class)
data class UserDataDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String?,
    val image: Bitmap,
)
