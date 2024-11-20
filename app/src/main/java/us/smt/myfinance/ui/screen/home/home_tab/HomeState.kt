package us.smt.myfinance.ui.screen.home.home_tab

import us.smt.myfinance.domen.model.CostData
import us.smt.myfinance.domen.model.DebtOweData
import us.smt.myfinance.domen.model.FundData

data class HomeState(
    val balance: String = "",
    val owes: List<DebtOweData> = emptyList(),
    val allOwe: String = "",
    val funds: List<FundData> = emptyList(),
    val allFunds: String = "",
    val costs: List<CostData> = emptyList(),
    val allCosts: String = ""
)
