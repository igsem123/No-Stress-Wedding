package br.com.iftm.edu.nostresswedding.data.repository

import br.com.iftm.edu.nostresswedding.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl : TaskRepository {
    override suspend fun insertTask(task: TaskEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(task: TaskEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(task: TaskEntity) {
        TODO("Not yet implemented")
    }

    override fun getAllTasks(): Flow<List<TaskEntity>> {
        TODO("Not yet implemented")
    }

    override fun getTaskById(id: Long): Flow<TaskEntity?> {
        TODO("Not yet implemented")
    }

    override fun getCompletedTasks(): Flow<List<TaskEntity>> {
        TODO("Not yet implemented")
    }

    override fun getPendingTasks(): Flow<List<TaskEntity>> {
        TODO("Not yet implemented")
    }
}