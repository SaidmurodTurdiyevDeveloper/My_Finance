package us.smt.myfinance.ui.screen.home.home_tab

sealed interface HomeIntent {
    data object GetTransactions : HomeIntent
}