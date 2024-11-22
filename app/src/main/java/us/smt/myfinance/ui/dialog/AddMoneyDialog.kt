package us.smt.myfinance.ui.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.isDigitsOnly

@Composable
fun AddMoneyDialog(
    onDismiss: () -> Unit,
    onAddItem: (money: String) -> Unit
) {
    var money by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add") },
        text = {
            Column {
                OutlinedTextField(
                    value = money,
                    onValueChange = {
                        money = it
                        error = false
                    },
                    isError = error,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = { Text("Money") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (money.isNotBlank() && money.isDigitsOnly()) {
                        onAddItem(money)
                    } else {
                        error = true
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


@Preview
@Composable
private fun CreateCostTypeDialogPrev() {

}
