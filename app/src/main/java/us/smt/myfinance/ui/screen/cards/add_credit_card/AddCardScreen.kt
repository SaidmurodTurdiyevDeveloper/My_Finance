package us.smt.myfinance.ui.screen.cards.add_credit_card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen

class AddCardScreen : Screen {
    @Composable
    override fun Content() {
        AddCardScreenContent({ cardNumber, cardHolderName, expirationDate, cvv -> })
    }
}

@Composable
private fun AddCardScreenContent(onSaveClick: (String, String, String, String) -> Unit) {
    // State for storing input values
    var cardNumber by remember { mutableStateOf("") }
    var cardHolderName by remember { mutableStateOf("") }
    var expirationDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Add New Card",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Card Number Input
        OutlinedTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text("Card Number") },
            placeholder = { Text("1234 5678 1234 5678") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        // Cardholder Name Input
        OutlinedTextField(
            value = cardHolderName,
            onValueChange = { cardHolderName = it },
            label = { Text("Cardholder Name") },
            placeholder = { Text("John Doe") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions.Default,
            singleLine = true
        )

        // Expiration Date Input
        OutlinedTextField(
            value = expirationDate,
            onValueChange = { expirationDate = it },
            label = { Text("Expiration Date") },
            placeholder = { Text("MM/YY") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        // CVV Input
        OutlinedTextField(
            value = cvv,
            onValueChange = { cvv = it },
            label = { Text("CVV") },
            placeholder = { Text("***") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        // Save Button
        Button(
            onClick = { onSaveClick(cardNumber, cardHolderName, expirationDate, cvv) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(text = "Save Card")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddCardScreenPreview() {
    MaterialTheme {
        AddCardScreenContent { _, _, _, _ -> }
    }
}
