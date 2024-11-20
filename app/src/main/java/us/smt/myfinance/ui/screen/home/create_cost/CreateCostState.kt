package us.smt.myfinance.ui.screen.home.create_cost

import us.smt.myfinance.ui.utils.TextFieldData

data class CreateCostState(
    val costType: TextFieldData = TextFieldData(),
    val cost: TextFieldData = TextFieldData(),
    val description: TextFieldData = TextFieldData()
)
