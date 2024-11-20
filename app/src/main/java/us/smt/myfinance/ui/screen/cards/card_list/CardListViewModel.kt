package us.smt.myfinance.ui.screen.cards.card_list

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.myfinance.data.database.local.shared.LocalStorage
import us.smt.myfinance.domen.model.CreditCard
import us.smt.myfinance.ui.screen.cards.add_credit_card.AddCardScreen
import us.smt.myfinance.ui.utils.AppNavigator
import us.smt.myfinance.ui.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CardListViewModel @Inject constructor(
    appNavigator: AppNavigator,
    private val localStorage: LocalStorage
) : BaseViewModel<CardListState, CardListIntent>(initializeData = CardListState(), appNavigator = appNavigator) {


    override fun onAction(intent: CardListIntent) {
        when (intent) {
            CardListIntent.AddCard -> openAddCard()
            CardListIntent.Back -> back()
            CardListIntent.LoadData -> loadList()
        }
    }


    private fun loadList() {
        val gson = Gson()
        val itemType = object : TypeToken<List<CreditCard>>() {}.type
        val cards: List<CreditCard> = if (localStorage.cards.isEmpty()) emptyList() else gson.fromJson(localStorage.cards, itemType)
        update(state = state.value.copy(creditCards = cards))
    }

    private fun openAddCard() {
        navigate(AddCardScreen())
    }

}