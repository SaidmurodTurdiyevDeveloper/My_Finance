package us.smt.myfinance.ui.screen.fund.fundhome

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import us.smt.myfinance.R
import us.smt.myfinance.domen.model.FundData
import us.smt.myfinance.ui.dialog.AddMoneyDialog
import us.smt.myfinance.ui.dialog.DeleteDialog
import us.smt.myfinance.ui.screen.home.home_tab.AnimatedPreloader
import us.smt.myfinance.util.toMoneyType

class FundScreen : Screen {
    @Composable
    override fun Content() {

        val viewModel = getViewModel<FundListViewModel>()
        val state by viewModel.state.collectAsState()
        LaunchedEffect(key1 = Unit) {
            viewModel.onAction(FundListIntent.LoadData)
        }
        FundScreenContent(state = state, onAction = viewModel::onAction)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FundScreenContent(
    state: FundState,
    onAction: (FundListIntent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Funds", style = MaterialTheme.typography.titleLarge.copy(color = Color.White)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(FundListIntent.Back) }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1E88E5), // Blue color for the app bar
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                actions = {
                    TotalText(state = state)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAction(FundListIntent.AddFund) },
                containerColor = Color(0xFF1E88E5)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Fund", tint = Color.White)
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (state.fundList.isEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(100.dp))
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(32.dp), contentAlignment = Alignment.Center
                        ) {
                            AnimatedPreloader(
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }
                } else {
                    items(state.fundList) { data ->
                        FundCardItem(data = data, onAction = onAction, delete = {
                            onAction(FundListIntent.OpenDeleteDialog(dataId = data.id))
                        })
                    }
                }
            }

        }
    )

    if (!state.isOpenAddMoneyDialog.isNullOrBlank()) {
        AddMoneyDialog(
            onDismiss = { onAction(FundListIntent.CloseAddMoneyDialog) },
            onAddItem = { onAction(FundListIntent.AddFundMoney(dataId = state.isOpenAddMoneyDialog, money = it)) }
        )
    }

    if (!state.isOpenDeleteDialog.isNullOrBlank()) {
        DeleteDialog(
            onDismiss = { onAction(FundListIntent.CloseDeleteMoneyDialog) },
            onDelete = { onAction(FundListIntent.DeleteFund) }
        )
    }
}

@Composable
private fun TotalText(
    state: FundState
) {
    val animatedValue by animateIntAsState(state.total, label = "")
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.coin),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = animatedValue.toString().toMoneyType(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .padding(end = 16.dp)
        )

    }

}

@Composable
private fun FundCardItem(
    data: FundData,
    onAction: (FundListIntent) -> Unit,
    delete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .width(250.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        ),
        onClick = {
            onAction(FundListIntent.OpenAddFundMoneyDialog(dataId = data.id))
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = data.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = data.amount.toMoneyType() + " $",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000)
                )
            }

            IconButton(
                onClick = delete,
                content = {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Go to details",
                        tint = Color.Gray
                    )
                },
            )
        }
    }
}
