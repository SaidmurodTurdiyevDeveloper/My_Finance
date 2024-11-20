package us.smt.myfinance.ui.screen.fund.fundhome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.LifecycleEffectOnce
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import us.smt.myfinance.domen.model.FundData
import us.smt.myfinance.ui.dialog.AddMoneyDialog

class FundScreen : Screen {
    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {

        val viewModel = getViewModel<FundListViewModel>()
        val state by viewModel.state.collectAsState()
        LifecycleEffectOnce {
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
                    containerColor = Color(0xFF448062), // Blue color for the app bar
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAction(FundListIntent.AddFund) },
                containerColor = Color(0xFF448062)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Fund", tint = Color.White)
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
            ) {
                items(state.fundList) { data ->
                    FundCardItem(data = data, onAction = onAction)
                }
            }
        }
    )

    if (!state.isOpenDialog.isNullOrBlank()) {
        AddMoneyDialog(
            onDismiss = { onAction(FundListIntent.CloseDialog) },
            onAddItem = { onAction(FundListIntent.AddFundMoney(dataId = state.isOpenDialog, money = it)) }
        )
    }
}


@Composable
private fun FundCardItem(
    data: FundData,
    onAction: (FundListIntent) -> Unit
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
