package us.smt.myfinance.ui.screen.cards.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import us.smt.myfinance.ui.utils.TextFieldData
import us.smt.myfinance.util.getErrorText


@Composable
fun CreditCardNumberTextView(
    hint: String,
    state: TextFieldData,
    onChange: (String) -> Unit,
    textStyle: androidx.compose.ui.text.TextStyle
) {
    OutlinedTextField(
        value = state.text,
        onValueChange = {
            onChange(it)
        },
        visualTransformation = CreditCardNumberTransformation(),
        label = { Text(hint) },
        modifier = Modifier.fillMaxWidth(),
        isError = state.error != null,
        supportingText = {
            if (state.error != null) {
                val errorText = getErrorText(state.error)
                Text(
                    text = errorText,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        textStyle = textStyle,
        singleLine = true,
        shape = RoundedCornerShape(4.dp)
    )
}

class CreditCardNumberTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // Extract digits from the input text
        val digits = text.text.filter { it.isDigit() }

        // Apply the mask and limit it to 19 characters (4 groups of 4 digits and 3 spaces)
        val formatted = buildString {
            digits.chunked(4).forEachIndexed { index, chunk ->
                if (index > 0) append(" ") // Add space between groups of 4 digits
                append(chunk)
            }
        }.take(19) // Only allow 19 characters (4 groups of 4 digits + 3 spaces)

        val creditCardOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 4) return offset
                if (offset <= 8) return offset + 1
                if (offset <= 12) return offset + 2
                if (offset <= 16) return offset + 3
                return 19
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 5) return offset
                if (offset <= 10) return offset - 1
                if (offset <= 15) return offset - 2
                if (offset <= 19) return offset - 3
                return 16
            }
        }

        return TransformedText(AnnotatedString(formatted), creditCardOffsetTranslator)
    }
}
