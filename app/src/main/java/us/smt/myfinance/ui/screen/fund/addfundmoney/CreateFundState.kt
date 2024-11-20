package us.smt.myfinance.ui.screen.fund.addfundmoney

import us.smt.myfinance.ui.utils.TextFieldData

data class CreateFundState(
    val name: TextFieldData = TextFieldData(),
    val cost: TextFieldData = TextFieldData()
)
