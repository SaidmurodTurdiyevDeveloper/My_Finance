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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.LifecycleEffectOnce
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import us.smt.myfinance.R
import us.smt.myfinance.domen.model.DebtOweData
import us.smt.myfinance.domen.model.FundData

object HomeTab : Tab {
    private fun readResolve(): Any = HomeTab
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 0u,
            title = "Home",
            icon = rememberVectorPainter(Icons.Default.Home)
        )

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val viewmodel = getViewModel<HomeViewModel>()
        val state by viewmodel.state.collectAsState()
        LifecycleEffectOnce {
            viewmodel.onAction(HomeIntent.Init)
        }
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
                onClick = { onAction(HomeIntent.OpenAddCost) },
                containerColor = Color(0xFF6200EE), // FAB Color
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Expense")
            }
        },
        content = { padding ->
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
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple()
                        ) { onAction(HomeIntent.GetTransactions) }
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

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    item {
                        FinancialSection(title = stringResource(R.string.last_month_cost), amount = state.allCosts.ifBlank { "0" })
                        FinancialSection(title = stringResource(R.string.debt_situation), amount = state.allOwe.ifBlank { "0" })
                        FinancialSection(title = stringResource(R.string.all_fund), amount = state.allFunds.ifBlank { "0" })

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Funds",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6200EE)
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
                                                color = Color(0xFF448062) // Section Title Color
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null,
                                            tint = Color(0xFF448062)
                                        )
                                    }
                                }
                            )
                        }
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            if (state.funds.isEmpty()) {
                                item {
                                    AddFundCardItem(onAction = onAction)
                                }
                            } else {
                                items(state.funds) { saving ->
                                    FundCardItem(data = saving)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        if (state.owes.isNotEmpty()) {
                            Text(
                                text = stringResource(R.string.must_payment_debt),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6200EE) // Section Title Color
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(state.owes) { debt ->
                                    FinancialCardItem(data = debt)
                                }
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                        Text(
                            text = stringResource(R.string.today_cost),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6200EE) // Section Title Color
                            ),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    items(state.costs) { expense ->
                        FinancialSection(title = expense.type, amount = expense.amount.toString().ifBlank { "0" })
                    }
                }
            }
        }
    )
}

@Composable
fun FinancialSection(title: String, amount: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFFF5F5F5),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF000000), // Section Title Color
                fontSize = 20.sp // Increased font size for the title
            ),
            modifier = Modifier.padding(bottom = 4.dp) // Spacing between title and amount
        )
        Text(
            text = amount,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Gray,
                fontSize = 18.sp // Increased font size for the amount
            )
        )
    }
}


@Composable
private fun AddFundCardItem(onAction: (HomeIntent) -> Unit) {
    Card(
        modifier = Modifier
            .height(80.dp)
            .width(250.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        ),
        onClick = { onAction(HomeIntent.OpenAddFound) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color(0xFF448062),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Add items",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF448062)
            )
        }

    }
}

@Composable
private fun FundCardItem(data: FundData) {
    Card(
        modifier = Modifier
            .height(100.dp)
            .width(250.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = data.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = data.amount.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000)
                )
            }
        }
    }
}

@Composable
private fun FinancialCardItem(data: DebtOweData) {
    Card(
        modifier = Modifier
            .height(200.dp)
            .width(200.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = data.ownerName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = data.amount.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = data.finalDate,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF444444)
                )
            }
        }
    }
}

