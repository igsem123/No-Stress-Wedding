package br.com.iftm.edu.nostresswedding.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "guests",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["uid"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GuestEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val phone: String,
    val isConfirmed: Boolean,
    val userId: String
)