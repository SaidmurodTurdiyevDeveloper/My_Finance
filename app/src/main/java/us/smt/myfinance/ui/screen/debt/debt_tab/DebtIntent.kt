package us.smt.myfinance.ui.screen.debt.debt_tab

import us.smt.myfinance.domen.model.DebtOweData

sealed interface DebtIntent {
    data object OpenDebt : DebtIntent
    data object CloseDebt : DebtIntent
    data class AddDebt(val debt: DebtOweData) : DebtIntent
    data class AddOwe(val owe: DebtOweData) : DebtIntent
}