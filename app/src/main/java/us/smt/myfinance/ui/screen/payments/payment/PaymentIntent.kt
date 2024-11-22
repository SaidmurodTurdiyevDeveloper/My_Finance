package us.smt.myfinance.ui.screen.payments.payment

sealed interface PaymentIntent {
    data object Back : PaymentIntent
    data class ChangeCode(val code: String) : PaymentIntent
    data class ChangeAmount(val amount: String) : PaymentIntent
    data class ChangeComment(val comment: String) : PaymentIntent
    data class SubmitPayment(val index: Int) : PaymentIntent
}