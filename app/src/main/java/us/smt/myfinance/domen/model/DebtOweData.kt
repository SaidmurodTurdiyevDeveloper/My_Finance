package us.smt.myfinance.domen.model

data class DebtOweData(
    val id:String="0",
    val amount: Int,
    val ownerName: String,
    val finalDate: String,
    val isCompany:Boolean,
    val owe: Boolean = false
)