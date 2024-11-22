package us.smt.myfinance.ui.screen.payments.payment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import us.smt.myfinance.ui.screen.commpn.SimpleTextView

class PaymentScreen(private val id: Int) : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<PaymentViewModel>()
        PaymentScreen(viewModel = viewModel, index = id)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PaymentScreen(viewModel: PaymentViewModel, index: Int) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onAction(PaymentIntent.Back)
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1E88E5), // Blue color for the app bar
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                title = { Text("Payment") })
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                // Code Field
                OutlinedTextField(
                    value = state.code.text,
                    onValueChange = { viewModel.onAction(PaymentIntent.ChangeCode(it)) },
                    label = { Text("Code") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.code.error != null
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Amount Field
                OutlinedTextField(
                    value = state.amount.text,
                    onValueChange = { viewModel.onAction(PaymentIntent.ChangeAmount(it)) },
                    label = { Text("Amount") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.amount.error != null
                )

                Spacer(modifier = Modifier.height(16.dp))

                SimpleTextView(
                    hint = "Comment",
                    state = state.comment,
                    onChange = { viewModel.onAction(PaymentIntent.ChangeComment(it)) }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Submit Button
                Button(
                    onClick = { viewModel.onAction(PaymentIntent.SubmitPayment(index)) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5)), // Blue color for the button
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(50.dp), // Fixed height for the button
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Submit Payment")
                }
            }
        }
    )
}
