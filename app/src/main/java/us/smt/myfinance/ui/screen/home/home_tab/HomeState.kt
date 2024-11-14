package us.smt.myfinance.ui.screen.home.home_tab

import us.smt.myfinance.domen.model.CostData
import us.smt.myfinance.domen.model.DebtOweData

data class HomeState(
    val isOpenDialog: Boolean = false,
    val balance: String = "",
    val owes: List<DebtOweData> = emptyList(),
    val allOwe: String = "",
    val funds: List<String> = emptyList(),
    val allFunds: String = "",
    val costs: List<CostData> = emptyList(),
    val allCosts: String = ""
)
