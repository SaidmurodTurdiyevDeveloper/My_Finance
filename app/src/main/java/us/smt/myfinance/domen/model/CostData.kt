package us.smt.myfinance.domen.model

import us.smt.myfinance.ui.screen.home.create_cost.DailyExpenses

data class CostData(
    val id: String,
    val timeMillisecond: String,
    val amount: Int,
    val imageRes: Int,
    val name: String,
    val type: DailyExpenses,
    val description: String
)