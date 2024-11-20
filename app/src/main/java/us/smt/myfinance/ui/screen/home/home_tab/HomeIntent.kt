package us.smt.myfinance.ui.screen.home.home_tab

sealed interface HomeIntent {
    data object Init : HomeIntent
    data object GetTransactions : HomeIntent
    data object OpenAddCost : HomeIntent
    data object OpenAddFound : HomeIntent
    data object OpenFound : HomeIntent
}