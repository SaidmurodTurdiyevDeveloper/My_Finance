package us.smt.myfinance.ui.screen.debt.debt_tab

import us.smt.myfinance.domen.model.DebtOweData

data class DebtState(
    val isOpenAddDialog: Boolean = false,
    val isOpenCompleteDialog: String? = null,
    val debts: List<DebtOweData> = emptyList(),
    val owes: List<DebtOweData> = emptyList()
)
