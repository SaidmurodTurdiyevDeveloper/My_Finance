package us.smt.myfinance.ui.screen.home.home_tab

import us.smt.myfinance.domen.model.DebtOweData

data class HomeState(
    val balance: String = "",
    val owes: List<DebtOweData> = emptyList(),
    val allOwe: String = "",
    val funds: List<String> = emptyList(),
    val allFunds: String = "",
    val costs: List<String> = emptyList(),
    val allCosts: String = ""
)
