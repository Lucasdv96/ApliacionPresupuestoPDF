package com.mrcerramiento.presupuesto.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mrcerramiento.presupuesto.data.models.Budget
import com.mrcerramiento.presupuesto.data.models.BudgetItem

@Database(
    entities = [Budget::class, BudgetItem::class],
    version = 1,
    exportSchema = false
)
abstract class BudgetDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDao
    abstract fun budgetItemDao(): BudgetItemDao

    companion object {
        @Volatile
        private var instance: BudgetDatabase? = null

        fun getInstance(context: Context): BudgetDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    BudgetDatabase::class.java,
                    "budget_database"
                ).build().also { instance = it }
            }
    }
}
