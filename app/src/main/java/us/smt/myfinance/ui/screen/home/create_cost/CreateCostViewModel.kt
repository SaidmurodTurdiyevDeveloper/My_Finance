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
            is CreateCostIntent.ChangeType -> changeType(intent.type)
            CreateCostIntent.Back -> back()
        }
    }

    private fun addCost() {
        if (state.value.cost.text.isEmpty() || state.value.costType.text.isEmpty() || !state.value.cost.text.isDigitsOnly()) {
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
                type = state.value.costType.text,
                timeMillisecond = time.toString()
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

    private fun changeType(type: String) {
        update(
            state.value.copy(
                costType = TextFieldData(
                    text = type
                )
            )
        )
    }

}