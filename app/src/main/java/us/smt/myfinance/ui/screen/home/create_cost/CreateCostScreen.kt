package us.smt.myfinance.ui.screen.home.create_cost

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import us.smt.myfinance.ui.screen.commpn.SimpleTextNumberView
import us.smt.myfinance.ui.screen.commpn.SimpleTextView

class CreateCostScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<CreateCostViewModel>()
        val state by viewModel.state.collectAsState()
        CreateCostScreenContent(
            state = state,
            onAction = viewModel::onAction
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun CreateCostScreenContent(state: CreateCostState, onAction: (CreateCostIntent) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add New Cost") },
                navigationIcon = {
                    IconButton(onClick = { onAction(CreateCostIntent.Back) }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1E88E5), // Blue color for the app bar
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
                    .background(Color(0xFFF5F5F5)) // Light gray background for the whole screen
            ) {
                // Card Number Input
                SimpleTextView(
                    hint = "Cost name",
                    state = state.costType,
                    onChange = { cardNumber -> onAction(CreateCostIntent.ChangeName(cardNumber)) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                SimpleTextNumberView(
                    hint = "Cost amount",
                    state = state.cost,
                    onChange = { cardHolderName -> onAction(CreateCostIntent.ChangeAmount(cardHolderName)) }
                )
                FlowRow {
                    DailyExpenses.entries.forEach { dailyExpenses ->
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .wrapContentSize(),
                            colors = CardDefaults.cardColors(
                                containerColor = if (state.type == dailyExpenses) Color(0xFF1E88E5) else Color.White
                            ),
                            onClick = { onAction(CreateCostIntent.ChangeType(dailyExpenses)) }
                        ) {
                            Box {
                                Text(
                                    text = dailyExpenses.getExpenseDetails(),
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (state.type == dailyExpenses) Color.White else Color.Black
                                    ),
                                    modifier = Modifier
                                        .padding(horizontal = 12.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                SimpleTextView(
                    hint = "Description",
                    state = state.description,
                    onChange = { expirationDate -> onAction(CreateCostIntent.ChangeDescription(expirationDate)) }
                )

                // Save Button
                Button(
                    onClick = { onAction(CreateCostIntent.AddCost) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5)), // Blue color for the button
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(50.dp), // Fixed height for the button
                    shape = RoundedCornerShape(12.dp) // Rounded corners for the button
                ) {
                    Text(
                        text = "Save",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White, fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    )
}

