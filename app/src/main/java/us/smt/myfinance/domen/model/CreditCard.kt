package us.smt.myfinance.domen.model

data class CreditCard(
    val cardHolderName: String,
    val cardNumber: String,
    val expiryDate: String,
    val money:Int,
    val cvv: String
)
