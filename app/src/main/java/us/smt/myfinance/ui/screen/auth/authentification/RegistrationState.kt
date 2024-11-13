package us.smt.myfinance.ui.screen.auth.authentification

import us.smt.myfinance.ui.utils.TextFieldData
import us.smt.myfinance.util.UserError

data class RegistrationState(
    val isLoading: Boolean = false,
    val error: UserError? = null,
    val name: TextFieldData = TextFieldData(),
    val surname: TextFieldData = TextFieldData(),
    val email: TextFieldData = TextFieldData(),
    val password: TextFieldData = TextFieldData(),
    val confirmPassword: TextFieldData = TextFieldData()
)
