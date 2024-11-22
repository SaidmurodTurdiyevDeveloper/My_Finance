package us.smt.myfinance.ui.screen.home.home_tab

import us.smt.myfinance.domen.model.CostData
import us.smt.myfinance.domen.model.DebtOweData
import us.smt.myfinance.domen.model.FundData

data class HomeState(
    val balance: Int = 0,
    val allDebts: List<DebtOweData> = emptyList(),
    val allOwes: List<DebtOweData> = emptyList(),
    val isDeletingExpenseId: String? = null,
    val allOwe: String = "0",
    val allDebt: String = "0",
    val funds: List<FundData> = emptyList(),
    val allFunds: String = "0",
    val costs: List<CostData> = emptyList(),
    val lastCosts: List<CostData> = emptyList(),
    val allCosts: String = "0"
)
