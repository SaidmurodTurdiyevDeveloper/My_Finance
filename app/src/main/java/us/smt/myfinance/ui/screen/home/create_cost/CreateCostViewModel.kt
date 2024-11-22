package us.smt.myfinance.ui.screen.home.create_cost

import androidx.core.text.isDigitsOnly
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.myfinance.data.database.local.shared.LocalStorage
import us.smt.myfinance.domen.model.CostData
import us.smt.myfinance.ui.utils.AppNavigator
import us.smt.myfinance.ui.utils.BaseViewModel
import us.smt.myfinance.ui.utils.TextFieldData
import us.smt.myfinance.util.TextViewError
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CreateCostViewModel @Inject constructor(
    private val localStorage: LocalStorage,
    navigator: AppNavigator
) : BaseViewModel<CreateCostState, CreateCostIntent>(initializeData = CreateCostState(), appNavigator = navigator) {
    override fun onAction(intent: CreateCostIntent) {
        when (intent) {
            CreateCostIntent.AddCost -> addCost()
            is CreateCostIntent.ChangeAmount -> changeAmount(intent.amount)
            is CreateCostIntent.ChangeDescription -> changeDescription(intent.description)
            is CreateCostIntent.ChangeName -> changeName(intent.type)
            CreateCostIntent.Back -> back()
            is CreateCostIntent.ChangeType -> changeType(intent.type)
        }
    }

    private fun addCost() {
        if (state.value.cost.text.isEmpty()) {
            update(state = state.value.copy(cost = state.value.cost.copy(error = TextViewError.Empty)))
            return
        }

        if (state.value.costType.text.isEmpty()) {
            update(state = state.value.copy(costType = state.value.costType.copy(error = TextViewError.Empty)))
            return
        }

        if (!state.value.cost.text.isDigitsOnly()) {
            update(state = state.value.copy(cost = state.value.cost.copy(error = TextViewError.InvalidCharacter)))
            return
        }

        val gson = Gson()
        val itemType = object : TypeToken<List<CostData>>() {}.type
        val costs: List<CostData> = if (localStorage.costs.isEmpty()) emptyList() else gson.fromJson(localStorage.costs, itemType)
        val ls = costs.toMutableList()
        val time = Calendar.getInstance().timeInMillis
        ls.add(
            CostData(
                amount = state.value.cost.text.toInt(),
                description = state.value.description.text,
                name = state.value.costType.text,
                timeMillisecond = time.toString(),
                type = state.value.type,
                imageRes = -1,
                id = ls.size.toString()
            )
        )
        localStorage.costs = gson.toJson(ls)
        back()
    }

    private fun changeAmount(amount: String) {
        update(
            state.value.copy(
                cost = TextFieldData(
                    text = amount
                )
            )
        )
    }

    private fun changeDescription(description: String) {
        update(
            state.value.copy(
                description = TextFieldData(
                    text = description
                )
            )
        )
    }

    private fun changeName(type: String) {
        update(
            state.value.copy(
                costType = TextFieldData(
                    text = type
                )
            )
        )
    }

    private fun changeType(type: DailyExpenses) {
        update(
            state.value.copy(
                type = type
            )
        )
    }

}