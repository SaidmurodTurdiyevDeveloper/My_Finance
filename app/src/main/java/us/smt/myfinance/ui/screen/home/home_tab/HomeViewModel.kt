package us.smt.myfinance.ui.screen.home.home_tab

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.myfinance.data.database.local.shared.LocalStorage
import us.smt.myfinance.domen.model.CostData
import us.smt.myfinance.domen.model.CreditCard
import us.smt.myfinance.domen.model.DebtOweData
import us.smt.myfinance.ui.screen.cards.card_list.CardListScreen
import us.smt.myfinance.ui.screen.fund.fundhome.FundScreen
import us.smt.myfinance.ui.utils.AppNavigator
import us.smt.myfinance.ui.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(navigator: AppNavigator, private val localStorage: LocalStorage) : BaseViewModel<HomeState, HomeIntent>(HomeState(), navigator) {
    init {
        val gson = Gson()
        val cardType = object : TypeToken<List<CreditCard>>() {}.type
        val debtType = object : TypeToken<List<DebtOweData>>() {}.type
        val costType = object : TypeToken<List<CostData>>() {}.type
        val cards: List<CreditCard> = if (localStorage.cards.isEmpty()) emptyList() else gson.fromJson(localStorage.cards, cardType)
        val owes: List<DebtOweData> = if (localStorage.owes.isEmpty()) emptyList() else gson.fromJson(localStorage.owes, debtType)
        val debts: List<DebtOweData> = if (localStorage.debts.isEmpty()) emptyList() else gson.fromJson(localStorage.cards, debtType)
        val costs: List<CostData> = if (localStorage.costs.isEmpty()) emptyList() else gson.fromJson(localStorage.costs, costType)
        val oweSum = owes.sumOf {
            it.amount
        }
        val debtSum = debts.sumOf {
            it.amount
        }
        val costSum = costs.sumOf {
            it.amount
        }
        val owe = oweSum - debtSum
        val balance = cards.sumOf {
            it.money
        }
        update(state = state.value.copy(balance = balance.toString(), allOwe = owe.toString(), costs = costs, allCosts = costSum.toString()))
    }

    override fun onAction(intent: HomeIntent) {
        when (intent) {
            HomeIntent.GetTransactions -> openDebtScreen()
            is HomeIntent.AddCost -> addCost(intent.amount, intent.name)
            HomeIntent.CloseDialog -> closeAddCostDialog()
            HomeIntent.OpenDialog -> openAddCostDialog()
            HomeIntent.OpenFound -> openFund()
        }
    }

    private fun openDebtScreen() {
        navigate(CardListScreen())
    }

    private fun openFund() {
        navigate(FundScreen())
    }

    private fun addCost(amount: Int, name: String) {
        val gson = Gson()
        val costType = object : TypeToken<List<CostData>>() {}.type
        val costs: List<CostData> = if (localStorage.costs.isEmpty()) emptyList() else gson.fromJson(localStorage.costs, costType)
        val ls = costs.toMutableList()
        ls.add(CostData(amount = amount, name = name))
        val sumCost = ls.sumOf {
            it.amount
        }
        localStorage.costs = gson.toJson(ls)
        update(state = state.value.copy(allCosts = sumCost.toString(), costs = ls))
    }

    private fun openAddCostDialog() {
        update(state = state.value.copy(isOpenDialog = true))
    }

    private fun closeAddCostDialog() {
        update(state = state.value.copy(isOpenDialog = false))
    }

}