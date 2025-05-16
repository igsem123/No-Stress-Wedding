package br.com.iftm.edu.nostresswedding.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "tasks")
class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String = "",
    val description: String = "",
    var isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val userId: String = "",
)

data class TaskWithUser(
    @Embedded val task: TaskEntity,

    @Relation(
        parentColumn = "userId",
        entityColumn = "uid"
    )
    val user: UserEntity
)