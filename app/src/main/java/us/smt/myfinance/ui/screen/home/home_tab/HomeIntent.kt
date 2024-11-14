package us.smt.myfinance.ui.screen.home.home_tab

sealed interface HomeIntent {
    data object GetTransactions : HomeIntent
    data object OpenDialog : HomeIntent
    data object CloseDialog : HomeIntent
    data object OpenFound : HomeIntent
    data class AddCost(val amount: Int, val name: String) : HomeIntent
}