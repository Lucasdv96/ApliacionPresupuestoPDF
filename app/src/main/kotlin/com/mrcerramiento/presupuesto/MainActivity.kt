package com.mrcerramiento.presupuesto

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mrcerramiento.presupuesto.ui.screens.BudgetDetailScreen
import com.mrcerramiento.presupuesto.ui.screens.BudgetViewModel
import com.mrcerramiento.presupuesto.ui.screens.CreateBudgetScreen
import com.mrcerramiento.presupuesto.ui.screens.EditContractScreen
import com.mrcerramiento.presupuesto.ui.screens.HomeScreen
import com.mrcerramiento.presupuesto.ui.theme.MRPresupuestoTheme
import com.mrcerramiento.presupuesto.util.PdfGenerator
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MRPresupuestoTheme {
                val navController = rememberNavController()
                val viewModel = BudgetViewModel(this@MainActivity)

                AppNavigation(navController, viewModel, this@MainActivity)
            }
        }
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: BudgetViewModel,
    activity: MainActivity
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onCreateNewBudget = {
                    navController.navigate("create_budget")
                },
                onBudgetClick = { budgetId ->
                    navController.navigate("budget_detail/$budgetId")
                },
                onEditBudget = { budgetId ->
                    navController.navigate("budget_detail/$budgetId")
                }
            )
        }

        composable("create_budget") {
            CreateBudgetScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onBudgetCreated = { budgetId ->
                    navController.navigate("budget_detail/$budgetId") {
                        popUpTo("create_budget") { inclusive = true }
                    }
                }
            )
        }

        composable("budget_detail/{budgetId}") { backStackEntry ->
            val budgetId = backStackEntry.arguments?.getString("budgetId")?.toLong() ?: 0L
            BudgetDetailScreen(
                budgetId = budgetId,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onEditContract = { budgetId ->
                    navController.navigate("edit_contract/$budgetId")
                },
                onGeneratePDF = { budgetId ->
                    generateAndSharePdf(budgetId, viewModel, activity)
                }
            )
        }

        composable("edit_contract/{budgetId}") { backStackEntry ->
            val budgetId = backStackEntry.arguments?.getString("budgetId")?.toLong() ?: 0L
            EditContractScreen(
                budgetId = budgetId,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

private fun generateAndSharePdf(
    budgetId: Long,
    viewModel: BudgetViewModel,
    activity: MainActivity
) {
    viewModel.loadBudgetWithItems(budgetId)

    // Small delay to ensure data is loaded
    val currentBudget = viewModel.currentBudget

    try {
        currentBudget.value?.let { budgetData ->
            val pdfGenerator = PdfGenerator(activity)
            val pdfFile = pdfGenerator.generatePdf(budgetData)

            pdfFile?.let {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "application/pdf"
                    putExtra(Intent.EXTRA_STREAM, android.net.Uri.fromFile(it))
                    putExtra(Intent.EXTRA_SUBJECT, "Presupuesto ${budgetData.budget.budgetNumber}")
                }
                activity.startActivity(Intent.createChooser(intent, "Compartir PDF"))
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
