package br.com.iftm.edu.nostresswedding.di

import android.app.Application
import androidx.room.Room
import br.com.iftm.edu.nostresswedding.data.local.database.NSWeddingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
}