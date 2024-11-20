package us.smt.myfinance.ui.screen.fund.fundhome

import us.smt.myfinance.domen.model.FundData

data class FundState(
    val isOpenDialog:String?=null,
    val fundList: List<FundData> = emptyList()
)
