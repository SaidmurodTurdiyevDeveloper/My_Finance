package us.smt.myfinance.ui.screen.payments.payment

import androidx.core.text.isDigitsOnly
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.myfinance.data.database.local.shared.LocalStorage
import us.smt.myfinance.domen.model.CostData
import us.smt.myfinance.ui.screen.home.create_cost.DailyExpenses
import us.smt.myfinance.ui.screen.payments.payment_tab.getPaymentResource
import us.smt.myfinance.ui.screen.payments.payment_tab.getPaymentTitle
import us.smt.myfinance.ui.screen.sms.SmsConfirmScreen
import us.smt.myfinance.ui.utils.AppNavigator
import us.smt.myfinance.ui.utils.BaseViewModel
import us.smt.myfinance.ui.utils.TextFieldData
import us.smt.myfinance.util.TextViewError
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    navigator: AppNavigator,
    private val localStorage: LocalStorage
) : BaseViewModel<PaymentState, PaymentIntent>(initializeData = PaymentState(), appNavigator = navigator) {

    override fun onAction(intent: PaymentIntent) {
        when (intent) {
            is PaymentIntent.ChangeCode -> {
                update(state = state.value.copy(code = TextFieldData(text = intent.code)))
            }

            is PaymentIntent.ChangeAmount -> {
                update(
                    state = state.value.copy(amount = TextFieldData(text = intent.amount))
                )
            }

            is PaymentIntent.ChangeComment -> {
                update(
                    state = state.value.copy(comment = TextFieldData(text = intent.comment))
                )
            }

            is PaymentIntent.SubmitPayment -> {
                submitPayment(intent.index)
            }

            PaymentIntent.Back -> back()
        }
    }

    private fun submitPayment(index: Int) {
        if (state.value.code.text.isEmpty()) {
            update(state = state.value.copy(code = state.value.code.copy(error = TextViewError.Empty)))
            return
        }

        if (state.value.amount.text.isEmpty()) {
            update(state = state.value.copy(amount = state.value.amount.copy(error = TextViewError.Empty)))
            return
        }

        if (!state.value.amount.text.isDigitsOnly()) {
            update(state = state.value.copy(amount = state.value.amount.copy(error = TextViewError.InvalidCharacter)))
            return
        }
        navigate(SmsConfirmScreen {
            val gson = Gson()
            val itemType = object : TypeToken<List<CostData>>() {}.type
            val costs: List<CostData> = if (localStorage.costs.isEmpty()) emptyList() else gson.fromJson(localStorage.costs, itemType)
            val ls = costs.toMutableList()
            val time = Calendar.getInstance().timeInMillis
            ls.add(
                CostData(
                    amount = state.value.amount.text.toInt(),
                    description = state.value.comment.text,
                    name = getPaymentTitle(index),
                    timeMillisecond = time.toString(),
                    id = ls.size.toString(),
                    type = DailyExpenses.UTILITIES,
                    imageRes = getPaymentResource(index)
                )
            )
            localStorage.costs = gson.toJson(ls)
            back()
            back()
        })
    }
}
