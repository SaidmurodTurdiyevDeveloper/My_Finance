package us.smt.myfinance.ui.screen.debt.debt_tab

import us.smt.myfinance.domen.model.DebtOweData

data class DebtState(
    val isOpenDialog: Boolean = false,
    val debts: List<DebtOweData> = emptyList(),
    val owes: List<DebtOweData> = emptyList()
)
