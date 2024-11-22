package us.smt.myfinance.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly

@Composable
fun CreateDebtOweDialog(
    onDismiss: () -> Unit,
    onAddItem: (name: String, money: String, untilTime: String, isCompany: Boolean) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var money by remember { mutableStateOf("") }
    var untilTime by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }
    var moneyError by remember { mutableStateOf(false) }
    var untilTimeError by remember { mutableStateOf(false) }
    var isCompany by remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    isError = nameError,
                    onValueChange = {
                        name = it
                        nameError = false
                    },
                    label = { Text("Name") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = money,
                    isError = moneyError,
                    onValueChange = {
                        money = it
                        moneyError = false
                    },
                    label = { Text("Money") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = untilTime,
                    isError = untilTimeError,
                    onValueChange = {
                        untilTime = it
                        untilTimeError = false
                    },
                    label = { Text("Until Time") },
                    placeholder = { Text("MM/DD/YYYY") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Checkbox Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = !isCompany,
                            onCheckedChange = { isCompany = false }
                        )
                        Text("Person")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = isCompany,
                            onCheckedChange = { isCompany = true }
                        )
                        Text("Company")
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isBlank()) {
                        nameError = true
                    } else if (money.isBlank() || !money.isDigitsOnly()) {
                        moneyError = true
                    } else if (validateDateFormat(untilTime).not()) {
                        untilTimeError = true
                    } else {
                        onAddItem(name, money, untilTime, isCompany)
                        onDismiss()
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

private fun validateDateFormat(input: String): Boolean {
    val regex = """^(0[1-9]|1[0-2])/([0-2][0-9]|3[01])/(\d{4})$""".toRegex()
    return regex.matches(input)
}

