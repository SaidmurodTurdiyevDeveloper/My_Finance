package us.smt.myfinance.domen.model

data class CostData(
    val timeMillisecond:String,
    val amount: Int,
    val type: String,
    val description:String
)