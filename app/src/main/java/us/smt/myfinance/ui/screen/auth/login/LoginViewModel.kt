package us.smt.myfinance.ui.screen.auth.login

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.myfinance.data.database.local.shared.LocalStorage
import us.smt.myfinance.ui.screen.auth.authentification.RegistrationScreen
import us.smt.myfinance.ui.screen.tab.MainTabScreen
import us.smt.myfinance.ui.utils.AppNavigator
import us.smt.myfinance.ui.utils.BaseViewModel
import us.smt.myfinance.ui.utils.TextFieldData
import us.smt.myfinance.util.AuthError
import us.smt.myfinance.util.isValidEmail
import us.smt.myfinance.util.validatePassword
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(appNavigator: AppNavigator, private val localStorage: LocalStorage) :
    BaseViewModel<LoginState, LoginIntent>(initializeData = LoginState(), appNavigator = appNavigator) {
    override fun onAction(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailChanged -> changeEmail(intent.email)
            is LoginIntent.PasswordChanged -> changePassword(intent.password)
            is LoginIntent.OpenRegistration -> openRegisterScreen()
            is LoginIntent.Login -> login()
        }
    }

    private fun changeEmail(email: String) {

        update(state = state.value.copy(email = TextFieldData(text = email, success = email.isValidEmail() == null)))
    }

    private fun changePassword(password: String) {
        update(state = state.value.copy(password = TextFieldData(text = password, success = password.validatePassword() == null)))
    }

    private fun openRegisterScreen() {
        update(state = state.value.copy(error = null))
        navigate(RegistrationScreen())
    }

    private fun login() {
        if (!localStorage.isRegistered) {
            update(state = state.value.copy(error = AuthError.UserNotRegister))
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
        if (localStorage.email != state.value.email.text || localStorage.password != state.value.password.text) {
            update(state = state.value.copy(error = AuthError.UserNotFound))
            return
        }
        localStorage.isLogin = true
        navigate(MainTabScreen())
    }

}