package us.smt.myfinance.ui.screen.auth.authentification

import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.myfinance.data.database.local.shared.LocalStorage
import us.smt.myfinance.ui.utils.AppNavigator
import us.smt.myfinance.ui.utils.BaseViewModel
import us.smt.myfinance.ui.utils.TextFieldData
import us.smt.myfinance.util.RePasswordError
import us.smt.myfinance.util.TextViewError
import us.smt.myfinance.util.isValidEmail
import us.smt.myfinance.util.validatePassword
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    appNavigator: AppNavigator,
    private val localStorage: LocalStorage
) : BaseViewModel<RegistrationState, RegistrationIntent>(initializeData = RegistrationState(), appNavigator = appNavigator) {
    override fun onAction(intent: RegistrationIntent) {
        when (intent) {
            is RegistrationIntent.NameChanged -> changeName(intent.name)
            is RegistrationIntent.LastNameChanged -> changeLastName(intent.surname)
            is RegistrationIntent.EmailChanged -> changeEmail(intent.email)
            is RegistrationIntent.PasswordChanged -> changePassword(intent.password)
            is RegistrationIntent.RePasswordChanged -> changeConfirmPassword(intent.password)
            RegistrationIntent.Back -> back()
            RegistrationIntent.Register -> register()
        }
    }

    private fun changeName(name: String) {
        update(state = state.value.copy(name = TextFieldData(text = name, success = name.isBlank().not())))
    }

    private fun changeLastName(lastName: String) {
        update(state = state.value.copy(surname = TextFieldData(text = lastName, success = lastName.isBlank().not())))
    }

    private fun changeEmail(email: String) {
        val mailError = email.isValidEmail()
        update(state = state.value.copy(email = TextFieldData(text = email, success = mailError == null)))
    }

    private fun changePassword(password: String) {
        val passwordError = password.validatePassword()
        update(state = state.value.copy(password = TextFieldData(text = password, success = passwordError == null)))
    }

    private fun changeConfirmPassword(password: String) {
        val passwordError = password == state.value.password.text
        update(state = state.value.copy(confirmPassword = TextFieldData(text = password, success = passwordError)))
    }

    private fun register() {
        if (!state.value.name.success) {
            update(state = state.value.copy(name = state.value.name.copy(error = TextViewError.Empty)))
            return
        }
        if (!state.value.surname.success) {
            update(state = state.value.copy(surname = state.value.surname.copy(error = TextViewError.Empty)))
            return
        }
        if (!state.value.email.success) {
            val mailError = state.value.email.text.isValidEmail()
            update(state = state.value.copy(email = state.value.email.copy(error = mailError)))
            return
        }
        if (!state.value.password.success) {
            val passwordError = state.value.password.text.validatePassword()
            update(state = state.value.copy(password = state.value.password.copy(error = passwordError)))
            return
        }
        if (!state.value.confirmPassword.success) {
            update(state = state.value.copy(password = state.value.password.copy(error = RePasswordError.IsSame)))
            return
        }
        localStorage.email = state.value.email.text
        localStorage.name = state.value.name.text
        localStorage.lastName = state.value.surname.text
        localStorage.password = state.value.password.text
        localStorage.isRegistered = true
        back()
    }
}