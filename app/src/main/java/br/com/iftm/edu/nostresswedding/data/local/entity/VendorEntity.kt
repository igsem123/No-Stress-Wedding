package br.com.iftm.edu.nostresswedding.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "vendors",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["uid"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class VendorEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val phone: String,
    val email: String,
    val serviceType: String,
    val userId: String
)
