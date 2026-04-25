package com.mrcerramiento.presupuesto.ui.screens

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrcerramiento.presupuesto.data.BudgetRepository
import com.mrcerramiento.presupuesto.data.models.Budget
import com.mrcerramiento.presupuesto.data.models.BudgetItem
import com.mrcerramiento.presupuesto.data.models.BudgetWithItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BudgetViewModel(context: Context) : ViewModel() {
    private val repository = BudgetRepository(context)

    private val _budgets = MutableStateFlow<List<Budget>>(emptyList())
    val budgets: StateFlow<List<Budget>> = _budgets.asStateFlow()

    private val _currentBudget = MutableStateFlow<BudgetWithItems?>(null)
    val currentBudget: StateFlow<BudgetWithItems?> = _currentBudget.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    init {
        loadBudgets()
    }

    fun loadBudgets() {
        viewModelScope.launch {
            repository.getAllBudgets().collect { budgets ->
                _budgets.value = budgets
            }
        }
    }

    fun loadBudgetWithItems(budgetId: Long) {
        viewModelScope.launch {
            _loading.value = true
            val budget = repository.getBudgetWithItems(budgetId)
            _currentBudget.value = budget
            _loading.value = false
        }
    }

    fun createBudget(
        clientName: String,
        clientPhone: String,
        clientEmail: String,
        clientAddress: String,
        onBudgetCreated: (Long) -> Unit
    ) {
        viewModelScope.launch {
            val budgetNumber = "BUD-${System.currentTimeMillis()}"
            val budget = Budget(
                clientName = clientName,
                clientPhone = clientPhone,
                clientEmail = clientEmail,
                clientAddress = clientAddress,
                budgetNumber = budgetNumber
            )
            val id = repository.saveBudget(budget)
            onBudgetCreated(id)
        }
    }

    fun updateBudget(budget: Budget) {
        viewModelScope.launch {
            repository.updateBudget(budget)
        }
    }

    fun deleteBudget(budgetId: Long) {
        viewModelScope.launch {
            repository.deleteBudget(budgetId)
            loadBudgets()
        }
    }

    fun addItem(
        budgetId: Long,
        description: String,
        widthMm: Float,
        heightMm: Float,
        material: String,
        specifications: String,
        quantity: Int
    ) {
        viewModelScope.launch {
            val item = BudgetItem(
                budgetId = budgetId,
                description = description,
                widthMm = widthMm,
                heightMm = heightMm,
                material = material,
                specifications = specifications,
                quantity = quantity
            )
            repository.addItem(item)
            loadBudgetWithItems(budgetId)
        }
    }

    fun deleteItem(itemId: Long, budgetId: Long) {
        viewModelScope.launch {
            repository.deleteItem(itemId)
            loadBudgetWithItems(budgetId)
        }
    }

    fun updateItem(item: BudgetItem) {
        viewModelScope.launch {
            repository.updateItem(item)
            _currentBudget.value?.let { loadBudgetWithItems(it.budget.id) }
        }
    }
}
