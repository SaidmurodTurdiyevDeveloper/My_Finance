package us.smt.myfinance.ui.screen.cards.add_credit_card

import us.smt.myfinance.ui.utils.TextFieldData

data class AddCardState(
    val cardNumber: TextFieldData = TextFieldData(),
    val cardHolderName: TextFieldData = TextFieldData(),
    val expirationDate: TextFieldData = TextFieldData(),
    val cvv: TextFieldData = TextFieldData()
)
