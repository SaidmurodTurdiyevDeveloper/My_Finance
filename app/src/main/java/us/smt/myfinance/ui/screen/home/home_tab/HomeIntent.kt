package us.smt.myfinance.ui.screen.home.home_tab

sealed interface HomeIntent {
    data object Init : HomeIntent
    data class OpenDeleteExpense(val id: String) : HomeIntent
    data class OpenStatistic(val index: Int) : HomeIntent
    data object GetTransactions : HomeIntent
    data object OpenAddCost : HomeIntent
    data object DismissDeleteDialog : HomeIntent
    data object DeleteExpense : HomeIntent
    data object OpenAddFound : HomeIntent
    data object OpenFound : HomeIntent
}