package us.smt.myfinance.ui.screen.home.home_tab

import android.icu.util.Calendar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.myfinance.data.database.local.shared.LocalStorage
import us.smt.myfinance.domen.model.CostData
import us.smt.myfinance.domen.model.CreditCard
import us.smt.myfinance.domen.model.DebtOweData
import us.smt.myfinance.domen.model.FundData
import us.smt.myfinance.ui.screen.cards.card_list.CardListScreen
import us.smt.myfinance.ui.screen.fund.addfundmoney.CreateFundScreen
import us.smt.myfinance.ui.screen.fund.fundhome.FundScreen
import us.smt.myfinance.ui.screen.home.create_cost.CreateCostScreen
import us.smt.myfinance.ui.utils.AppNavigator
import us.smt.myfinance.ui.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(navigator: AppNavigator, private val localStorage: LocalStorage) : BaseViewModel<HomeState, HomeIntent>(HomeState(), navigator) {

    override fun onAction(intent: HomeIntent) {
        when (intent) {
            HomeIntent.GetTransactions -> openDebtScreen()
            HomeIntent.OpenAddCost -> openAddCostScreen()
            HomeIntent.OpenFound -> openFund()
            HomeIntent.OpenAddFound -> openAddFundScreen()
            HomeIntent.Init -> initData()
        }
    }

    private fun initData() {
        val gson = Gson()
        val cardType = object : TypeToken<List<CreditCard>>() {}.type
        val debtType = object : TypeToken<List<DebtOweData>>() {}.type
        val costType = object : TypeToken<List<CostData>>() {}.type
        val fundType = object : TypeToken<List<FundData>>() {}.type
        val cards: List<CreditCard> = if (localStorage.cards.isEmpty()) emptyList() else gson.fromJson(localStorage.cards, cardType)
        val owes: List<DebtOweData> = if (localStorage.owes.isEmpty()) emptyList() else gson.fromJson(localStorage.owes, debtType)
        val debts: List<DebtOweData> = if (localStorage.debts.isEmpty()) emptyList() else gson.fromJson(localStorage.debts, debtType)
        val costs: List<CostData> = if (localStorage.costs.isEmpty()) emptyList() else gson.fromJson(localStorage.costs, costType)
        val funds: List<FundData> = if (localStorage.funds.isEmpty()) emptyList() else gson.fromJson(localStorage.funds, fundType)
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
        val fundSum = funds.sumOf {
            it.amount.toInt()
        }
        val cl = Calendar.getInstance()
        cl.set(Calendar.HOUR_OF_DAY, 0)
        cl.set(Calendar.MINUTE, 0)
        val start = cl.timeInMillis
        val todayCost = costs.filter {
            it.timeMillisecond.toLong() > start
        }
        val ls = ArrayList(owes)
        ls.addAll(debts)
        update(
            state = state.value.copy(
                balance = balance.toString(),
                allOwe = owe.toString(),
                allFunds = fundSum.toString(),
                costs = todayCost,
                owes = ls,
                funds = funds,
                allCosts = costSum.toString()
            )
        )
    }

    private fun openDebtScreen() {
        navigate(CardListScreen())
    }

    private fun openFund() {
        navigate(FundScreen())
    }

    private fun openAddCostScreen() {
        navigate(CreateCostScreen())
    }

    private fun openAddFundScreen() {
        navigate(CreateFundScreen())
    }

}