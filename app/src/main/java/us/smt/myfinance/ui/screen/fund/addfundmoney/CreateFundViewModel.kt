package us.smt.myfinance.ui.screen.fund.addfundmoney

import androidx.core.text.isDigitsOnly
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.myfinance.data.database.local.shared.LocalStorage
import us.smt.myfinance.domen.model.FundData
import us.smt.myfinance.ui.utils.AppNavigator
import us.smt.myfinance.ui.utils.BaseViewModel
import us.smt.myfinance.ui.utils.TextFieldData
import us.smt.myfinance.util.TextViewError
import javax.inject.Inject

@HiltViewModel
class CreateFundViewModel @Inject constructor(
    private val localStorage: LocalStorage,
    navigator: AppNavigator
) : BaseViewModel<CreateFundState, CreateFundIntent>(initializeData = CreateFundState(), appNavigator = navigator) {
    override fun onAction(intent: CreateFundIntent) {
        when (intent) {
            CreateFundIntent.AddFund -> addFund()
            is CreateFundIntent.ChangeAmount -> changeAmount(intent.amount)
            is CreateFundIntent.ChangeName -> changeName(intent.name)
            CreateFundIntent.Back -> back()
        }
    }

    private fun addFund() {
        if (state.value.cost.text.isEmpty()) {
            update(state = state.value.copy(cost = state.value.cost.copy(error = TextViewError.Empty)))
            return
        }

        if (!state.value.cost.text.isDigitsOnly()) {
            update(state = state.value.copy(cost = state.value.cost.copy(error = TextViewError.InvalidCharacter)))
            return
        }
        val gson = Gson()
        val itemType = object : TypeToken<List<FundData>>() {}.type
        val costs: List<FundData> = if (localStorage.funds.isEmpty()) emptyList() else gson.fromJson(localStorage.funds, itemType)
        val ls = costs.toMutableList()
        ls.add(
            FundData(
                id = ls.size.toString(),
                amount = state.value.cost.text,
                name = state.value.name.text
            )
        )
        val str = gson.toJson(ls)
        localStorage.funds = str
        back()
    }

    private fun changeAmount(amount: String) {
        update(
            state.value.copy(
                cost = TextFieldData(
                    text = amount,
                    success = amount.isDigitsOnly()
                )
            )
        )
    }

    private fun changeName(name: String) {
        update(
            state.value.copy(
                name = TextFieldData(
                    text = name,
                    success = name.isNotBlank()
                )
            )
        )
    }

}