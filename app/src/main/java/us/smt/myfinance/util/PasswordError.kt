package us.smt.myfinance.util

sealed class PasswordError : TextViewError {
    data object Empty : PasswordError()
    data object NumberMissed : PasswordError()
    data object UpperLetterMissed : PasswordError()
    data object LowerLetterMissed : PasswordError()
    data object MoreLetterMissed : PasswordError()
    data object NotEnoughLetterMissed : PasswordError()
}
