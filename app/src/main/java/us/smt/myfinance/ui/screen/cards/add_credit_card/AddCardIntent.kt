package us.smt.myfinance.ui.screen.cards.add_credit_card

sealed interface AddCardIntent {
    data object Save : AddCardIntent
    data object Back : AddCardIntent
    data class ChangeCardNumber(val cardNumber: String) : AddCardIntent
    data class ChangeCardHolderName(val cardHolderName: String) : AddCardIntent
    data class ChangeExpirationDate(val expirationDate: String) : AddCardIntent
    data class ChangeCvv(val cvv: String) : AddCardIntent
}