package us.smt.myfinance.ui.screen.cards.add_credit_card

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.myfinance.data.database.local.shared.LocalStorage
import us.smt.myfinance.domen.model.CreditCard
import us.smt.myfinance.ui.screen.sms.SmsConfirmScreen
import us.smt.myfinance.ui.utils.AppNavigator
import us.smt.myfinance.ui.utils.BaseViewModel
import us.smt.myfinance.ui.utils.TextFieldData
import us.smt.myfinance.util.TextViewError
import us.smt.myfinance.util.validateCreditCard
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
        update(state = state.value.copy(cardHolderName = TextFieldData(text = name, success = name.isNotBlank())))
    }

    private fun changeCardNumber(number: String) {
        update(state = state.value.copy(cardNumber = TextFieldData(text = number, success = number.validateCreditCard() == null)))
    }

    private fun changeCvv(cvv: String) {
        update(state = state.value.copy(cvv = TextFieldData(text = cvv, success = cvv.isNotBlank())))
    }

    private fun changeExpirationDate(date: String) {
        update(state = state.value.copy(expirationDate = TextFieldData(text = date, success = date.isNotBlank())))
    }

    private fun addCard() {

        if (state.value.cardHolderName.text.isEmpty()) {
            update(state = state.value.copy(cardHolderName = state.value.cardHolderName.copy(error = TextViewError.Empty)))
            return
        }

        if (!state.value.cardNumber.success) {
            val error = state.value
                .cardNumber.text.validateCreditCard()
            update(state = state.value.copy(cardNumber = state.value.cardNumber.copy(error = error)))
            return
        }
        if (state.value.expirationDate.text.isEmpty()) {
            update(state = state.value.copy(expirationDate = state.value.expirationDate.copy(error = TextViewError.Empty)))
            return
        }
        navigate(SmsConfirmScreen {
            val money = Random.nextInt(10, 1000) * 1000

            val gson = Gson()
            val itemType = object : TypeToken<List<CreditCard>>() {}.type
            val cards: List<CreditCard> = if (localStorage.cards.isEmpty()) emptyList() else gson.fromJson(localStorage.cards, itemType)
            val ls = cards.toMutableList()
            val newCard = CreditCard(
                id = cards.size.toString(),
                cardHolderName = state.value.cardHolderName.text,
                cardNumber = state.value.cardNumber.text.trim().chunked(4).joinToString(" "),
                cvv = state.value.cvv.text,
                money = money,
                expiryDate = state.value.expirationDate.text
            )
            ls.add(newCard)
            localStorage.cards = gson.toJson(ls)
            back()
            back()
        })
    }

}