package br.com.iftm.edu.nostresswedding.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import br.com.iftm.edu.nostresswedding.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Upsert
    suspend fun upsertUser(userEntity: UserEntity)

    @Query("SELECT * FROM users WHERE uid = :userId")
    suspend fun getUserById(userId: String): UserEntity?

    @Delete
    suspend fun deleteUser(userEntity: UserEntity)
}