package com.mrcerramiento.presupuesto.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mrcerramiento.presupuesto.ui.components.MRButton
import com.mrcerramiento.presupuesto.ui.components.SectionHeader

@Composable
fun EditContractScreen(
    budgetId: Long,
    viewModel: BudgetViewModel,
    onBackClick: () -> Unit
) {
    val budget = viewModel.currentBudget.collectAsState()
    val contractText = remember { mutableStateOf("") }

    remember {
        budget.value?.let {
            contractText.value = it.budget.contract
        }
        Unit
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Editar Contrato",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            SectionHeader("Términos y Condiciones")
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = contractText.value,
                onValueChange = { contractText.value = it },
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(8.dp)
                    ),
                shape = RoundedCornerShape(8.dp),
                textStyle = androidx.compose.material3.MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            MRButton(
                text = "Guardar Cambios",
                onClick = {
                    budget.value?.let {
                        val updatedBudget = it.budget.copy(contract = contractText.value)
                        viewModel.updateBudget(updatedBudget)
                        onBackClick()
                    }
                }
            )
        }
    }
}
