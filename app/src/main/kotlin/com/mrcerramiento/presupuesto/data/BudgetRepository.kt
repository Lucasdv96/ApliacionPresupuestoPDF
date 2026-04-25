package com.mrcerramiento.presupuesto.data

import android.content.Context
import com.mrcerramiento.presupuesto.data.local.BudgetDatabase
import com.mrcerramiento.presupuesto.data.models.Budget
import com.mrcerramiento.presupuesto.data.models.BudgetItem
import kotlinx.coroutines.flow.Flow

class BudgetRepository(context: Context) {
    private val db = BudgetDatabase.getInstance(context)
    private val budgetDao = db.budgetDao()
    private val budgetItemDao = db.budgetItemDao()

    fun getAllBudgets(): Flow<List<Budget>> = budgetDao.getAllBudgets()

    suspend fun saveBudget(budget: Budget): Long = budgetDao.insert(budget)

    suspend fun updateBudget(budget: Budget) = budgetDao.update(budget)

    suspend fun deleteBudget(id: Long) {
        budgetItemDao.deleteItemsByBudgetId(id)
        budgetDao.deleteBudgetById(id)
    }

    suspend fun getBudgetWithItems(id: Long) = budgetDao.getBudgetWithItems(id)

    suspend fun addItem(item: BudgetItem): Long = budgetItemDao.insert(item)

    suspend fun updateItem(item: BudgetItem) = budgetItemDao.update(item)

    suspend fun deleteItem(id: Long) = budgetItemDao.deleteItemById(id)

    suspend fun getItemsByBudgetId(budgetId: Long) = budgetItemDao.getItemsByBudgetId(budgetId)
}
