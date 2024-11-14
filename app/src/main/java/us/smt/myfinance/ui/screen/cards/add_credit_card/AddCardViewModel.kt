package us.smt.myfinance.ui.screen.cards.add_credit_card

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.myfinance.data.database.local.shared.LocalStorage
import us.smt.myfinance.domen.model.CreditCard
import us.smt.myfinance.ui.utils.AppNavigator
import us.smt.myfinance.ui.utils.BaseViewModel
import us.smt.myfinance.ui.utils.TextFieldData
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AddCardViewModel @Inject constructor(appNavigator: AppNavigator, private val localStorage: LocalStorage) :
    BaseViewModel<AddCardState, AddCardIntent>(initializeData = AddCardState(), appNavigator = appNavigator) {
    override fun onAction(intent: AddCardIntent) {
        when (intent) {
            is AddCardIntent.ChangeCardHolderName -> changeCardHolderName(intent.cardHolderName)
            is AddCardIntent.ChangeCardNumber -> changeCardNumber(intent.cardNumber)
            is AddCardIntent.ChangeCvv -> changeCvv(intent.cvv)
            is AddCardIntent.ChangeExpirationDate -> changeExpirationDate(intent.expirationDate)
            AddCardIntent.Save -> addCard()
            AddCardIntent.Back -> back()
        }
    }

    private fun changeCardHolderName(name: String) {
        update(state = state.value.copy(cardHolderName = TextFieldData(text = name)))
    }

    private fun changeCardNumber(number: String) {
        update(state = state.value.copy(cardNumber = TextFieldData(text = number)))
    }

    private fun changeCvv(cvv: String) {
        update(state = state.value.copy(cvv = TextFieldData(text = cvv)))
    }

    private fun changeExpirationDate(date: String) {
        update(state = state.value.copy(expirationDate = TextFieldData(text = date)))
    }

    private fun addCard() {
        if (state.value.cardHolderName.text.isEmpty() || state.value.cardNumber.text.isEmpty() || state.value.cvv.text.isEmpty() || state.value.expirationDate.text.isEmpty()) {
            return
        }
        val money = Random.nextInt(10, 1000) * 1000
        val newCard = CreditCard(
            cardHolderName = state.value.cardHolderName.text,
            cardNumber = state.value.cardNumber.text,
            cvv = state.value.cvv.text,
            money = money,
            expiryDate = state.value.expirationDate.text
        )
        val gson = Gson()
        val itemType = object : TypeToken<List<CreditCard>>() {}.type
        val cards: List<CreditCard> = if (localStorage.cards.isEmpty()) emptyList() else gson.fromJson(localStorage.cards, itemType)
        val ls = cards.toMutableList()
        ls.add(newCard)
        localStorage.cards = gson.toJson(ls)
        back()
    }

}