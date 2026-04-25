package com.mrcerramiento.presupuesto.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mrcerramiento.presupuesto.ui.components.EmptyState
import com.mrcerramiento.presupuesto.ui.components.InfoCard
import com.mrcerramiento.presupuesto.ui.components.MRButton
import com.mrcerramiento.presupuesto.ui.components.MRFloatingActionButton
import com.mrcerramiento.presupuesto.ui.components.MRTextField
import com.mrcerramiento.presupuesto.ui.components.SectionHeader

@Composable
fun BudgetDetailScreen(
    budgetId: Long,
    viewModel: BudgetViewModel,
    onBackClick: () -> Unit,
    onEditContract: (Long) -> Unit,
    onGeneratePDF: (Long) -> Unit
) {
    val budget = viewModel.currentBudget.collectAsState()

    remember { viewModel.loadBudgetWithItems(budgetId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detalles del Presupuesto",
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
        },
        floatingActionButton = {
            MRFloatingActionButton(onClick = { })
        }
    ) { padding ->
        budget.value?.let { budgetData ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(padding)
                    .padding(16.dp)
            ) {
                item {
                    SectionHeader("Información del Cliente")
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoCard("Nombre:", budgetData.budget.clientName)
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoCard("Teléfono:", budgetData.budget.clientPhone)
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoCard("Email:", budgetData.budget.clientEmail)
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoCard("Dirección:", budgetData.budget.clientAddress)
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    SectionHeader("Items del Presupuesto")
                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (budgetData.items.isEmpty()) {
                    item {
                        EmptyState(
                            icon = "📦",
                            title = "Sin items",
                            description = "Agrega items a este presupuesto"
                        )
                    }
                } else {
                    items(budgetData.items) { item ->
                        ItemCard(
                            item = item,
                            onDelete = { viewModel.deleteItem(item.id, budgetId) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    SectionHeader("Resumen")
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoCard(
                        "Total de items:",
                        budgetData.items.size.toString()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoCard(
                        "Superficie total:",
                        String.format("%.2f m²", budgetData.getTotalSurfaceM2())
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    MRButton(
                        text = "Editar Contrato",
                        onClick = { onEditContract(budgetId) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    MRButton(
                        text = "Generar PDF",
                        onClick = { onGeneratePDF(budgetId) }
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
fun ItemCard(
    item: com.mrcerramiento.presupuesto.data.models.BudgetItem,
    onDelete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.description,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${item.widthMm}mm x ${item.heightMm}mm",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.material,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                if (item.specifications.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = item.specifications,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
            IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color(0xFFE53935),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
        if (item.quantity > 1) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Cantidad: ${item.quantity}",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
