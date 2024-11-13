package us.smt.myfinance.ui.screen.home.home_tab

import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.myfinance.ui.screen.cards.card_list.CardListScreen
import us.smt.myfinance.ui.utils.AppNavigator
import us.smt.myfinance.ui.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val navigator: AppNavigator) : BaseViewModel<HomeState, HomeIntent>(HomeState(), navigator) {
    override fun onAction(intent: HomeIntent) {
        when (intent) {
            HomeIntent.GetTransactions -> openDebtScreen()
        }
    }

    private fun openDebtScreen() {
        navigate(CardListScreen())
    }

}