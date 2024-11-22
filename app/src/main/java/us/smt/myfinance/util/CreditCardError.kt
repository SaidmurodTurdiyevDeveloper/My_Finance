package us.smt.myfinance.util

sealed class CreditCardError : TextViewError {
    data object Empty : CreditCardError()
    data object InvalidLength : CreditCardError()
    data object InvalidCharacter : CreditCardError()
}
// Extension function to validate credit card number
fun String.validateCreditCard(): CreditCardError? {
    // Check if the string is empty
    if (this.isEmpty()) {
        return CreditCardError.Empty
    }

    // Check if the length of the card number is valid (typically 13 to 19 digits)
    if (this.length < 13 || this.length > 19) {
        return CreditCardError.InvalidLength
    }

    // Check if the card number contains only digits (no invalid characters)
    if (!this.all { it.isDigit() }) {
        return CreditCardError.InvalidCharacter
    }

    // No errors, return null (valid credit card number)
    return null
}