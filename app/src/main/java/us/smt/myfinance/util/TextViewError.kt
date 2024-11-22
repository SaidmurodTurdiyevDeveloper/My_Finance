package us.smt.myfinance.util

interface TextViewError {
    data object Empty : TextViewError
    data object InvalidCharacter : TextViewError
}

fun getErrorText(error: TextViewError): String = when (error) {
    TextViewError.Empty -> "Field is empty"
    TextViewError.InvalidCharacter -> "Invalid character"
    EmailError.Invalid -> "Invalid email"
    PasswordError.LowerLetterMissed -> "Password must contain at least one lower letter"
    PasswordError.MoreLetterMissed -> "Password must contain at least one upper letter"
    PasswordError.NotEnoughLetterMissed -> "Password must contain at least one letter"
    PasswordError.NumberMissed -> "Password must contain at least one number"
    PasswordError.UpperLetterMissed -> "Password must contain at least one upper letter"
    RePasswordError.IsSame -> "Passwords are the same"
    CreditCardError.Empty -> "Card number cannot be empty"
    CreditCardError.InvalidLength -> "Card number must be 16 digits"
    CreditCardError.InvalidCharacter -> "Card number must only contain digits"
    else -> ""
}
