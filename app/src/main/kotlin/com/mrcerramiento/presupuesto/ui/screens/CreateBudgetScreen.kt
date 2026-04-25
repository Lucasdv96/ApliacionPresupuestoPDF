package com.mrcerramiento.presupuesto.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mrcerramiento.presupuesto.ui.components.MRButton
import com.mrcerramiento.presupuesto.ui.components.MRTextField
import com.mrcerramiento.presupuesto.ui.components.SectionHeader

@Composable
fun CreateBudgetScreen(
    viewModel: BudgetViewModel,
    onBackClick: () -> Unit,
    onBudgetCreated: (Long) -> Unit
) {
    val clientName = remember { mutableStateOf("") }
    val clientPhone = remember { mutableStateOf("") }
    val clientEmail = remember { mutableStateOf("") }
    val clientAddress = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Nuevo Presupuesto",
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
            SectionHeader("Datos del Cliente")
            Spacer(modifier = Modifier.height(12.dp))

            MRTextField(
                value = clientName.value,
                onValueChange = { clientName.value = it },
                label = "Nombre del cliente"
            )
            Spacer(modifier = Modifier.height(12.dp))

            MRTextField(
                value = clientPhone.value,
                onValueChange = { clientPhone.value = it },
                label = "Teléfono"
            )
            Spacer(modifier = Modifier.height(12.dp))

            MRTextField(
                value = clientEmail.value,
                onValueChange = { clientEmail.value = it },
                label = "Email"
            )
            Spacer(modifier = Modifier.height(12.dp))

            MRTextField(
                value = clientAddress.value,
                onValueChange = { clientAddress.value = it },
                label = "Dirección",
                singleLine = false
            )
            Spacer(modifier = Modifier.height(24.dp))

            MRButton(
                text = "Crear Presupuesto",
                onClick = {
                    if (clientName.value.isNotEmpty() && clientPhone.value.isNotEmpty()) {
                        viewModel.createBudget(
                            clientName = clientName.value,
                            clientPhone = clientPhone.value,
                            clientEmail = clientEmail.value,
                            clientAddress = clientAddress.value,
                            onBudgetCreated = onBudgetCreated
                        )
                    }
                },
                enabled = clientName.value.isNotEmpty() && clientPhone.value.isNotEmpty()
            )
        }
    }
}
