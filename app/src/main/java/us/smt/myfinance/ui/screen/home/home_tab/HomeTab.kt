package us.smt.myfinance.ui.screen.home.home_tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import us.smt.myfinance.ui.dialog.CreateCostDialog

object HomeTab : Tab {
    private fun readResolve(): Any = HomeTab
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 0u,
            title = "Home",
            icon = rememberVectorPainter(Icons.Default.Home)
        )

    @Composable
    override fun Content() {
        val viewmodel = getViewModel<HomeViewModel>()
        val state by viewmodel.state.collectAsState()

        PaymentHomeScreen(
            state = state,
            onAction = viewmodel::onAction
        )
    }
}

@Composable
private fun PaymentHomeScreen(
    state: HomeState,
    onAction: (HomeIntent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction(HomeIntent.OpenDialog)
                },
                containerColor = Color(0xFF6200EE), // FAB Color
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Expense")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF2F4F6))
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "My Finance",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black // Title Color
                    )
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = ripple()) {
                        onAction(HomeIntent.GetTransactions)
                    }
                    .background(Color(0xFF5E92F3), shape = RoundedCornerShape(12.dp))
                    .padding(24.dp)
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = state.balance.ifBlank { "0" },
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Cards All Balance",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val savingsList = listOf(
                "Favqulodda holatlar: 400,000 so'm",
                "Ta'til: 300,000 so'm",
                "Ta'lim: 300,000 so'm"
            )

            val debtsList = listOf(
                "Mansurdan qarzim: 200,000 so'm",
                "Avtokredit: 500,000 so'm",
                "Ipoteka: 1,000,000 so'm"
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                item {
                    FinancialSection(title = "Oxirgi oylik xarajatlar", amount = state.allCosts.ifBlank { "0" })
                    FinancialSection(title = "Sizning qarz holatinigiz", amount = state.allOwe.ifBlank { "0" })
                    FinancialSection(title = "Umumiy jamg'arma balansi", amount = state.allFunds.ifBlank { "0" })

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Jamg'armalar",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6200EE) // Section Title Color
                            )
                        )
                        TextButton(
                            onClick = { onAction(HomeIntent.OpenFound) },
                            content = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(

                                        text = "All",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF6200EE) // Section Title Color
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
                                }
                            })
                    }
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(savingsList) { saving ->
                            FinancialCardItem(name = saving)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "To'lanishi kerak bo'lgan qarzlar va kreditlar",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6200EE) // Section Title Color
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(debtsList) { debt ->
                            FinancialCardItem(name = debt)
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Bugungi xarajatlar",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6200EE) // Section Title Color
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                items(state.costs) { expense ->
                    FinancialSection(title = expense.name, amount = expense.amount.toString().ifBlank { "0" })
                }
            }
        }
    }
    if (state.isOpenDialog) {
        CreateCostDialog(
            onDismiss = {
                onAction(HomeIntent.CloseDialog)
            },
            onAddItem = { name, money ->
                onAction(HomeIntent.AddCost(money, name))
            }
        )
    }
}

@Composable
fun FinancialSection(title: String, amount: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF000000) // Section Title Color
            )
        )
        Text(
            text = amount,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Gray,
                fontSize = 14.sp // Change the font size for amount
            )
        )
    }
}

@Composable
fun FinancialCardItem(name: String) {
    Card(
        modifier = Modifier
            .height(100.dp)
            .width(200.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000) // Card Text Color
                )
            }
        }
    }
}
