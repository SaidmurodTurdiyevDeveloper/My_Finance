package us.smt.myfinance.ui.screen.debt.debt_tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import us.smt.myfinance.R
import us.smt.myfinance.domen.model.DebtOweData
import us.smt.myfinance.ui.dialog.CreateDebtOweDialog

object DebtTab : Tab {
    private fun readResolve(): Any = DebtTab
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 2u,
            title = "Debt",
            icon = painterResource(
                R.drawable.ic_payments
            )
        )

    @Composable
    override fun Content() {
        val viewModel = getViewModel<DebtViewModel>()
        val state by viewModel.state.collectAsState()
        DebtOweScreen(
            state = state,
            onAction = viewModel::onAction
        )
    }
}

@Composable
private fun DebtOweScreen(
    state: DebtState,
    onAction: (DebtIntent) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction(DebtIntent.OpenDebt)
                },
                containerColor = Color(0xFF6200EE), // FAB rangini ko'k qildik
                contentColor = Color.White // FAB ustidagi ikon rangini oq qildik
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5)) // Butun ekranning fon rangini och kul rang qildik
        ) {
            // Tabs
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White, // Tabning fon rangini oq qildik
                indicator = {} // Indikatorni olib tashladik
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = {
                        Text(
                            "My Debts",
                            modifier = Modifier.padding(16.dp),
                            color = if (selectedTab == 0) Color(0xFF6200EE) else Color.Gray // Tanlangan tabning rangini ko'k qildik, boshqasini kulrang
                        )
                    }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = {
                        Text(
                            "They Owe",
                            modifier = Modifier.padding(16.dp),
                            color = if (selectedTab == 1) Color(0xFF6200EE) else Color.Gray // Tanlangan tabning rangini ko'k qildik, boshqasini kulrang
                        )
                    }
                )
            }

            // Content
            when (selectedTab) {
                0 -> DebtList(state.debts)
                1 -> OweList(state.owes)
            }
        }
    }

    if (state.isOpenDialog) {
        CreateDebtOweDialog(
            onDismiss = { onAction(DebtIntent.CloseDebt) },
            onAddItem = { name, money, untilTime ->
                if (selectedTab == 0) {
                    onAction(
                        DebtIntent.AddDebt(
                            DebtOweData(
                                amount = if (money.isNotEmpty() && money.isDigitsOnly()) money.toInt() else 0,
                                ownerName = name,
                                finalDate = untilTime
                            )
                        )
                    )
                } else {
                    onAction(
                        DebtIntent.AddOwe(
                            DebtOweData(
                                amount = if (money.isNotEmpty() && money.isDigitsOnly()) money.toInt() else 0,
                                ownerName = name,
                                finalDate = untilTime
                            )
                        )
                    )
                }
            }
        )
    }
}

@Composable
private fun DebtList(debts: List<DebtOweData>) {
    val totalDebt = debts.sumOf { it.amount }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Total Debt: $$totalDebt",
            fontSize = 20.sp, // Font o'lchamini kattalashtirdik
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(debts.size) { index ->
                DebtOweCard(item = debts[index])
            }
        }
    }
}

@Composable
private fun OweList(owes: List<DebtOweData>) {
    val totalOwe = owes.sumOf { it.amount }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Total Owe: $$totalOwe",
            fontSize = 20.sp, // Font o'lchamini kattalashtirdik
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyColumn {
            items(owes.size) { index ->
                DebtOweCard(item = owes[index])
            }
        }
    }
}

@Composable
private fun DebtOweCard(item: DebtOweData) {
    Card(
        shape = RoundedCornerShape(12.dp), // Kartaning burchaklarini yumaloqladik
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Kartaning fon rangini oq qildik
        ),
        elevation = CardDefaults.cardElevation(6.dp) // Ko'tarilishni oshirdik
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Amount: $${item.amount}",
                fontSize = 18.sp, // Font o'lchamini kattalashtirdik
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Owner: ${item.ownerName}",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Final Date: ${item.finalDate}",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}


@Preview
@Composable
private fun DebtOweScreenPreview() {
    DebtOweScreen(DebtState()) {}
}