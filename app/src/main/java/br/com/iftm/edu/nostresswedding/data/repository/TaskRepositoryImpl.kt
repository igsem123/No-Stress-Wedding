package br.com.iftm.edu.nostresswedding.data.repository

import br.com.iftm.edu.nostresswedding.data.local.database.NSWeddingDatabase
import br.com.iftm.edu.nostresswedding.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val db: NSWeddingDatabase
) : TaskRepository {
    override suspend fun insertTask(task: TaskEntity) {
        try {
            db.taskDao().insertTask(task)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateTask(task: TaskEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTaskStatus(task: TaskEntity) {
        try {
            db.taskDao().updateTaskStatus(task.id, task.isCompleted)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteTask(task: TaskEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getTasksByUserId(userId: String): Flow<List<TaskEntity>> = flow {
        val tasks =  try {
            db.taskDao().getTasksByUserId(userId)
        } catch (e: Exception) {
            throw e
        }

        emit(tasks)
    }

    override suspend fun getCompletedTasks(): Flow<List<TaskEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPendingTasks(): Flow<List<TaskEntity>> {
        TODO("Not yet implemented")
    }
}