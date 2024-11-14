package us.smt.myfinance.ui.screen.cards.add_credit_card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import us.smt.myfinance.ui.screen.auth.view.textview.SimpleTextView

class AddCardScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<AddCardViewModel>()
        val state by viewModel.state.collectAsState()
        AddCardScreenContent(state = state, onAction = viewModel::onAction)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddCardScreenContent(state: AddCardState, onAction: (AddCardIntent) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add New Card") },
                navigationIcon = {
                    IconButton(onClick = { onAction(AddCardIntent.Back) }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Card Number Input
                SimpleTextView(
                    hint = "Card Number",
                    state = state.cardNumber,
                    onChange = { cardNumber -> onAction(AddCardIntent.ChangeCardNumber(cardNumber)) },
                )
                Spacer(modifier = Modifier.height(16.dp))
                SimpleTextView(
                    hint = "Cardholder Name",
                    state = state.cardHolderName,
                    onChange = { cardHolderName -> onAction(AddCardIntent.ChangeCardHolderName(cardHolderName)) },
                )
                Spacer(modifier = Modifier.height(16.dp))
                SimpleTextView(
                    hint = "Expiration Date",
                    state = state.expirationDate,
                    onChange = { expirationDate -> onAction(AddCardIntent.ChangeExpirationDate(expirationDate)) },
                )
                Spacer(modifier = Modifier.height(16.dp))
                SimpleTextView(
                    hint = "CVV",
                    state = state.cvv,
                    onChange = { cvv -> onAction(AddCardIntent.ChangeCvv(cvv)) },
                )

                // Save Button
                Button(
                    onClick = { onAction(AddCardIntent.Save) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(text = "Save Card")
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
private fun AddCardScreenPreview() {
    MaterialTheme {
        AddCardScreenContent(state = AddCardState(), onAction = {})
    }
}
