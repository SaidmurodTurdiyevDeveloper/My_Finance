package us.smt.myfinance.ui.screen.fund.fundhome

sealed interface FundListIntent {
    data object AddFund : FundListIntent
    data object DeleteFund : FundListIntent
    data object CloseAddMoneyDialog : FundListIntent
    data object CloseDeleteMoneyDialog : FundListIntent
    data class OpenAddFundMoneyDialog(val dataId:String) : FundListIntent
    data class OpenDeleteDialog(val dataId:String) : FundListIntent
    data class AddFundMoney(val dataId:String,val money:String) : FundListIntent
    data object Back : FundListIntent
    data object LoadData : FundListIntent
}