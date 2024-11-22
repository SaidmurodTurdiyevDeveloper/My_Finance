package us.smt.myfinance.ui.screen.home.home_tab

import us.smt.myfinance.domen.model.CostData
import us.smt.myfinance.domen.model.DebtOweData
import us.smt.myfinance.domen.model.FundData

data class HomeState(
    val balance: String = "",
    val allDebts: List<DebtOweData> = emptyList(),
    val allOwes: List<DebtOweData> = emptyList(),
    val isDeletingExpenseId: String? = null,
    val allOwe: String = "",
    val allDebt: String = "",
    val funds: List<FundData> = emptyList(),
    val allFunds: String = "",
    val costs: List<CostData> = emptyList(),
    val lastCosts: List<CostData> = emptyList(),
    val allCosts: String = ""
)
