package us.smt.myfinance.ui.screen.auth.login

import us.smt.myfinance.ui.utils.TextFieldData
import us.smt.myfinance.util.UserError

data class LoginState(
    val isLoading: Boolean = false,
    val error: UserError? = null,
    val email: TextFieldData = TextFieldData(),
    val password: TextFieldData = TextFieldData()
)
