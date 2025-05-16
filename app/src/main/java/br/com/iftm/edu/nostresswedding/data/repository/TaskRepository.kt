package br.com.iftm.edu.nostresswedding.data.repository

import br.com.iftm.edu.nostresswedding.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun insertTask(task: TaskEntity)
    suspend fun updateTask(task: TaskEntity)
    suspend fun updateTaskStatus(task: TaskEntity)
    suspend fun deleteTask(task: TaskEntity)
    fun getTasksByUserId(userId: String): Flow<List<TaskEntity>>
    fun getCompletedTasks(): Flow<List<TaskEntity>>
    fun getPendingTasks(): Flow<List<TaskEntity>>
}