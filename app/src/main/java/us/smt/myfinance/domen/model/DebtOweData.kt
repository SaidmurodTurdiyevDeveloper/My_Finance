package us.smt.myfinance.domen.model

data class DebtOweData(
    val amount: Int,
    val ownerName: String,
    val finalDate: String,
    val owe: Boolean = false
)