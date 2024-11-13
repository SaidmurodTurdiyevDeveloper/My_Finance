package us.smt.myfinance.ui.screen.cards.card_list

import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.myfinance.ui.screen.cards.add_credit_card.AddCardScreen
import us.smt.myfinance.ui.utils.AppNavigator
import us.smt.myfinance.ui.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CardListViewModel @Inject constructor(appNavigator: AppNavigator) : BaseViewModel<CardListState, CardListIntent>(initializeData = CardListState(), appNavigator = appNavigator) {
    override fun onAction(intent: CardListIntent) {
        when (intent) {
            CardListIntent.AddCard -> openAddCard()
        }
    }

    private fun openAddCard() {
        navigate(AddCardScreen())
    }

}