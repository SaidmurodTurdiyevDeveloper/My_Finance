package us.smt.myfinance.ui.screen.auth.login

import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.myfinance.ui.utils.BaseViewModel
import us.smt.myfinance.ui.utils.Navigator
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(navigator: Navigator) : BaseViewModel<LoginState, LoginIntent>(initializeData = LoginState(), navigator = navigator) {
    override fun onAction(intent: LoginIntent) {

    }

}