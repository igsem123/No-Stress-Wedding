package br.com.iftm.edu.nostresswedding.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.iftm.edu.nostresswedding.data.local.dao.UserDao
import br.com.iftm.edu.nostresswedding.data.local.entity.UserEntity

@Database(
    entities = [ UserEntity::class ],
    version = 1,
    exportSchema = false
)
abstract class NSWeddingDatabase: RoomDatabase() {
    // DAOs
    abstract fun userDao(): UserDao
//    abstract fun weddingDao(): WeddingDao
//    abstract fun taskDao(): TaskDao
//    abstract fun vendorDao(): VendorDao
//    abstract fun paymentDao(): PaymentDao
//    abstract fun guestDao(): GuestDao
//    abstract fun budgetDao(): BudgetDao
}