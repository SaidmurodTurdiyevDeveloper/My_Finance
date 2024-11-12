package us.smt.myfinance.ui.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Intent>(initializeData: State, private val navigator: Navigator) : ViewModel() {
    protected var _state = MutableStateFlow(initializeData)
    val state = _state.asStateFlow()

    abstract fun onAction(intent: Intent)

    protected fun back() {
        viewModelScope.launch {
            navigator.back()
        }
    }

    protected fun navigate(screen: Screen) {
        viewModelScope.launch {
            navigator.navigateTo(screen)
        }
    }
}