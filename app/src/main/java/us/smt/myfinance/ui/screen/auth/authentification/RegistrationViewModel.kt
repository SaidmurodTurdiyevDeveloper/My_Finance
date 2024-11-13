package us.smt.myfinance.ui.screen.auth.authentification

import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.myfinance.ui.screen.tab.MainTabScreen
import us.smt.myfinance.ui.utils.AppNavigator
import us.smt.myfinance.ui.utils.BaseViewModel
import us.smt.myfinance.ui.utils.TextFieldData
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(appNavigator: AppNavigator) : BaseViewModel<RegistrationState, RegistrationIntent>(initializeData = RegistrationState(), appNavigator = appNavigator) {
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
        update(state = state.value.copy(name = TextFieldData(text = name)))
    }

    private fun changeLastName(lastName: String) {
        update(state = state.value.copy(surname = TextFieldData(text = lastName)))
    }

    private fun changeEmail(email: String) {
        update(state = state.value.copy(email = TextFieldData(text = email)))
    }

    private fun changePassword(password: String) {
        update(state = state.value.copy(password = TextFieldData(text = password)))
    }

    private fun changeConfirmPassword(password: String) {
        update(state = state.value.copy(confirmPassword = TextFieldData(text = password)))
    }

    private fun register(){
        navigate(MainTabScreen())
    }
}