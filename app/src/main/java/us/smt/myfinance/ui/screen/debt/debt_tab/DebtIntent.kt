package us.smt.myfinance.ui.screen.debt.debt_tab

import us.smt.myfinance.domen.model.DebtOweData

sealed interface DebtIntent {
    data class OpenCompleteDebt(val id:String) : DebtIntent
    data object OpenAddDebt : DebtIntent
    data object CloseAddDebt : DebtIntent
    data class CompleteDept(val isDebt:Boolean) : DebtIntent
    data object CloseCompleteDebt : DebtIntent
    data class AddDebt(val debt: DebtOweData) : DebtIntent
    data class AddOwe(val owe: DebtOweData) : DebtIntent
}