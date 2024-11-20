package us.smt.myfinance.ui.screen.fund.addfundmoney

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import us.smt.myfinance.ui.utils.TextFieldData

class CreateFundScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<CreateFundViewModel>()
        val state by viewModel.state.collectAsState()
        CreateFundScreenContent(
            state = state,
            onAction = viewModel::onAction
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateFundScreenContent(state: CreateFundState, onAction: (CreateFundIntent) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add New Fund") },
                navigationIcon = {
                    IconButton(onClick = { onAction(CreateFundIntent.Back) }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF448062), // Blue color for the app bar
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
                    hint = "Fund name",
                    state = state.name,
                    onChange = { cardNumber -> onAction(CreateFundIntent.ChangeName(cardNumber)) },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                )
                Spacer(modifier = Modifier.height(16.dp))
                SimpleTextView(
                    hint = "Fund amount",
                    state = state.cost,
                    onChange = { cardHolderName -> onAction(CreateFundIntent.ChangeAmount(cardHolderName)) },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                )

                // Save Button
                Button(
                    onClick = { onAction(CreateFundIntent.AddFund) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF448062)), // Blue color for the button
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

@Composable
private fun SimpleTextView(
    hint: String,
    state: TextFieldData,
    onChange: (String) -> Unit,
    textStyle: androidx.compose.ui.text.TextStyle
) {
    OutlinedTextField(
        value = state.text,
        onValueChange = onChange,
        label = { Text(hint) },
        modifier = Modifier.fillMaxWidth(),
        textStyle = textStyle,
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            unfocusedLabelColor = Color.Gray,
            cursorColor = Color(0xFF448062)
        )
    )
}
