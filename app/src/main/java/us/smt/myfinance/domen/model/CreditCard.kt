package us.smt.myfinance.domen.model

data class CreditCard(
    val cardHolderName: String,
    val cardNumber: String,
    val expiryDate: String,
    val cardProviderImage: Int // Image resource ID for the card provider logo
)
