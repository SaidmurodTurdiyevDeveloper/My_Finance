package us.smt.myfinance.ui.screen.fund.addfundmoney

sealed interface CreateFundIntent {
    data object Back : CreateFundIntent
    data object AddFund : CreateFundIntent
    data class ChangeAmount(val amount: String) : CreateFundIntent
    data class ChangeName(val name: String) : CreateFundIntent
}