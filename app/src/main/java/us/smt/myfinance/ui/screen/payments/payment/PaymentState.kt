package us.smt.myfinance.ui.screen.payments.payment

import us.smt.myfinance.ui.utils.TextFieldData

data class PaymentState(
    val code:TextFieldData=TextFieldData(),
    val amount:TextFieldData=TextFieldData(),
    val comment:TextFieldData=TextFieldData()
)