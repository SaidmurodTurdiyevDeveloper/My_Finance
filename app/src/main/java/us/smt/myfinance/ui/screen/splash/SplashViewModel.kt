package us.smt.myfinance.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import us.smt.myfinance.data.database.local.shared.LocalStorage
import us.smt.myfinance.ui.screen.auth.login.LoginScreen
import us.smt.myfinance.ui.screen.tab.MainTabScreen
import us.smt.myfinance.ui.utils.AppNavigator
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val navigator: AppNavigator, private val localStorage: LocalStorage) : ViewModel() {
    fun start() {
        if (localStorage.isLogin) {
            viewModelScope.launch {
                delay(100)
                navigator.replace(MainTabScreen())
            }
        } else {
            viewModelScope.launch {
                delay(100)
                navigator.replace(LoginScreen())
            }
        }
    }
}