package br.com.iftm.edu.nostresswedding.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val uid: String,
    val username: String = "",
    val email: String = "",
    val phone: String = "",
    val weddingDate: String = "",
    val weddingBudget: Double = 0.0,
    val name: String,
)