package us.smt.myfinance.ui.screen.auth.login

import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.myfinance.data.database.local.shared.LocalStorage
import us.smt.myfinance.ui.screen.auth.authentification.RegistrationScreen
import us.smt.myfinance.ui.screen.tab.MainTabScreen
import us.smt.myfinance.ui.utils.AppNavigator
import us.smt.myfinance.ui.utils.BaseViewModel
import us.smt.myfinance.ui.utils.TextFieldData
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
        update(state = state.value.copy(email = TextFieldData(text = email)))
    }

    private fun changePassword(password: String) {
        update(state = state.value.copy(password = TextFieldData(text = password)))
    }

    private fun openRegisterScreen() {
        navigate(RegistrationScreen())
    }

    private fun login() {
//        if (localStorage.isRegistered){
//            update(state = state.value.copy(error = AuthError.UserNotFound))
//            return
//        }
        navigate(MainTabScreen())
    }

}