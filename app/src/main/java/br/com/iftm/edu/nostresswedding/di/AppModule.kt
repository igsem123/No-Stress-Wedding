package br.com.iftm.edu.nostresswedding.di

import android.app.Application
import androidx.room.Room
import br.com.iftm.edu.nostresswedding.data.local.database.NSWeddingDatabase
import br.com.iftm.edu.nostresswedding.data.repository.TaskRepository
import br.com.iftm.edu.nostresswedding.data.repository.TaskRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo de injeção de dependência para fornecer instâncias do banco de dados local e do Firestore.
 *
 * @property application Instância do aplicativo.
 */

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application): NSWeddingDatabase {
        return Room.databaseBuilder(
            application,
            NSWeddingDatabase::class.java,
            "ns_wedding_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFirestoreDatabase(application: Application): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(
        db: NSWeddingDatabase
    ): TaskRepository {
        return TaskRepositoryImpl(db)
    }
}