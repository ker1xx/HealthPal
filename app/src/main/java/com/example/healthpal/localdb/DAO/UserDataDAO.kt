package com.example.healthpal.localdb.DAO

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.healthpal.localdb.DTO.UserDataDTO

@Dao
interface UserDataDAO {

    @Upsert
    suspend fun upsertUserData(userData: UserDataDTO)

    @Query("SELECT * FROM user_data")
    fun getAllUserData() : UserDataDTO
}