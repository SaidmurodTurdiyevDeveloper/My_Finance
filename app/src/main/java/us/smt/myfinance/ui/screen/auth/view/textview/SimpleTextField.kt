package us.smt.myfinance.ui.screen.auth.view.textview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import us.smt.myfinance.ui.utils.TextFieldData

@Composable
fun SimpleTextView(
    hint:String,
    state: TextFieldData,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        isError = state.error != null,
        value = state.text,
        onValueChange = onChange,
        label = { Text(hint) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}