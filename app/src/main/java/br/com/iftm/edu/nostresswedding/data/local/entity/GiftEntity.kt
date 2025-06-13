package br.com.iftm.edu.nostresswedding.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "gifts",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["uid"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GuestEntity::class,
            parentColumns = ["id"],
            childColumns = ["guestId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
)
data class GiftEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val description: String,
    val price: Double,
    val isPurchased: Boolean,
    val userId: String,
    val guestId: Long
)