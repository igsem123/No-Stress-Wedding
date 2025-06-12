package br.com.iftm.edu.nostresswedding.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import br.com.iftm.edu.nostresswedding.data.local.entity.TaskEntity
import br.com.iftm.edu.nostresswedding.data.local.entity.UserEntity

data class TaskWithUser(
    @Embedded val task: TaskEntity,

    @Relation(
        parentColumn = "userId",
        entityColumn = "uid"
    )
    val user: UserEntity
)