package com.mrcerramiento.presupuesto.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mrcerramiento.presupuesto.data.models.Budget
import com.mrcerramiento.presupuesto.data.models.BudgetItem
import com.mrcerramiento.presupuesto.data.models.BudgetWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Insert
    suspend fun insert(budget: Budget): Long

    @Update
    suspend fun update(budget: Budget)

    @Delete
    suspend fun delete(budget: Budget)

    @Query("SELECT * FROM budgets ORDER BY createdDate DESC")
    fun getAllBudgets(): Flow<List<Budget>>

    @Query("SELECT * FROM budgets WHERE id = :id")
    suspend fun getBudgetById(id: Long): Budget?

    @Transaction
    @Query("SELECT * FROM budgets WHERE id = :id")
    suspend fun getBudgetWithItems(id: Long): BudgetWithItems?

    @Query("DELETE FROM budgets WHERE id = :id")
    suspend fun deleteBudgetById(id: Long)
}

@Dao
interface BudgetItemDao {
    @Insert
    suspend fun insert(item: BudgetItem): Long

    @Update
    suspend fun update(item: BudgetItem)

    @Delete
    suspend fun delete(item: BudgetItem)

    @Query("SELECT * FROM budget_items WHERE budgetId = :budgetId ORDER BY `order`")
    suspend fun getItemsByBudgetId(budgetId: Long): List<BudgetItem>

    @Query("DELETE FROM budget_items WHERE budgetId = :budgetId")
    suspend fun deleteItemsByBudgetId(budgetId: Long)

    @Query("DELETE FROM budget_items WHERE id = :id")
    suspend fun deleteItemById(id: Long)
}
