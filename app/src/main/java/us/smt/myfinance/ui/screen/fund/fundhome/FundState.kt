package us.smt.myfinance.ui.screen.fund.fundhome

import us.smt.myfinance.domen.model.FundData

data class FundState(
    val isOpenAddMoneyDialog:String?=null,
    val isOpenDeleteDialog:String?=null,
    val total:Int=0,
    val fundList: List<FundData> = emptyList()
)
