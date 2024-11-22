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
import us.smt.myfinance.ui.screen.home.statistic.StatisticBarScreen
import us.smt.myfinance.ui.screen.home.statistic.StatisticData
import us.smt.myfinance.ui.screen.home.statistic.StatisticScreen
import us.smt.myfinance.ui.screen.home.statistic.calculateCostByType
import us.smt.myfinance.ui.screen.home.statistic.calculateDailyCost
import us.smt.myfinance.ui.screen.home.statistic.getRandomColor
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
            is HomeIntent.OpenStatistic -> openStatisticScreen(index = intent.index)
            HomeIntent.DeleteExpense -> deleteExpense()
            HomeIntent.DismissDeleteDialog -> closeDeleteExpenseDialog()
            is HomeIntent.OpenDeleteExpense -> openDeleteExpenseDialog(intent.id)
        }
    }

    private fun openDeleteExpenseDialog(
        id: String
    ) {
        update(
            state.value.copy(
                isDeletingExpenseId = id
            )
        )
    }

    private fun closeDeleteExpenseDialog() {
        update(
            state.value.copy(
                isDeletingExpenseId = null
            )
        )
    }

    private fun deleteExpense() {
        val ls = state.value.costs.filter {
            it.id != state.value.isDeletingExpenseId
        }
        update(
            state.value.copy(
                costs = ls,
                isDeletingExpenseId = null
            )
        )
        localStorage.costs = Gson().toJson(ls)
    }

    private fun initData() {
        val gson = Gson()
        val cl = Calendar.getInstance()
        cl.set(Calendar.HOUR_OF_DAY, 0)
        cl.set(Calendar.MINUTE, 0)
        val clMonth = Calendar.getInstance()
        clMonth.set(Calendar.HOUR_OF_DAY, 0)
        clMonth.set(Calendar.MINUTE, 0)
        clMonth.add(Calendar.DAY_OF_MONTH, -30)
        val cardType = object : TypeToken<List<CreditCard>>() {}.type
        val debtType = object : TypeToken<List<DebtOweData>>() {}.type
        val costType = object : TypeToken<List<CostData>>() {}.type
        val fundType = object : TypeToken<List<FundData>>() {}.type
        val cards: List<CreditCard> = if (localStorage.cards.isEmpty()) emptyList() else gson.fromJson(localStorage.cards, cardType)
        val owes: List<DebtOweData> = if (localStorage.owes.isEmpty()) emptyList() else gson.fromJson(localStorage.owes, debtType)
        val debts: List<DebtOweData> = if (localStorage.debts.isEmpty()) emptyList() else gson.fromJson(localStorage.debts, debtType)
        var costs: List<CostData> = if (localStorage.costs.isEmpty()) emptyList() else gson.fromJson(localStorage.costs, costType)
        val funds: List<FundData> = if (localStorage.funds.isEmpty()) emptyList() else gson.fromJson(localStorage.funds, fundType)

        val oweSum = owes.sumOf {
            it.amount
        }
        val debtSum = debts.sumOf {
            it.amount
        } * -1
        costs = costs.filter {
            it.timeMillisecond.toLong() > clMonth.timeInMillis
        }
        localStorage.costs = gson.toJson(costs)
        val costSum = costs.sumOf {
            it.amount
        }
        val balance = cards.sumOf {
            it.money
        }
        val fundSum = funds.sumOf {
            it.amount.toInt()
        }
        val start = cl.timeInMillis
        val todayCost = costs.filter {
            it.timeMillisecond.toLong() > start
        }
        update(
            state = state.value.copy(
                balance = balance.toString(),
                allOwe = oweSum.toString(),
                allDebt = debtSum.toString(),
                allFunds = fundSum.toString(),
                costs = todayCost,
                lastCosts = costs,
                allDebts = debts,
                allOwes = owes,
                funds = funds,
                allCosts = costSum.toString()
            )
        )
    }

    private fun openDebtScreen() {
        navigate(CardListScreen())
    }

    private fun openStatisticScreen(
        index: Int
    ) {
        when (index) {
            0 -> {
                val costs = state.value.lastCosts
                val lsPie = calculateCostByType(costs)
                val lsBar = calculateDailyCost(costs)
                navigate(
                    StatisticScreen(
                        title = "Last month expenses",
                        pieList = lsPie,
                        barList = lsBar
                    )
                )
            }
            1 -> {
                val lsPie = state.value.funds.map {
                    StatisticData(
                        title = it.name,
                        value = it.amount.toDouble(),
                        color = getRandomColor()
                    )
                }
                val lsBar = state.value.funds.map {
                    StatisticData(
                        title = it.name,
                        value = it.amount.toDouble(),
                        color = getRandomColor()
                    )
                }
                navigate(
                    StatisticScreen(
                        title = "Fund",
                        pieList = lsPie,
                        barList = lsBar
                    )
                )
            }
            2 -> {
                val lsBar = state.value.allOwes.map {
                    StatisticData(
                        title = it.ownerName,
                        value = it.amount.toDouble(),
                        color = getRandomColor()
                    )
                }
                navigate(
                    StatisticBarScreen(
                        title = "They are all owe",
                        barList = lsBar
                    )
                )
            }
            3 -> {
                val lsBar = state.value.allDebts.map {
                    StatisticData(
                        title = it.ownerName,
                        value = it.amount.toDouble(),
                        color = getRandomColor()
                    )
                }
                navigate(
                    StatisticBarScreen(
                        title = "Your all debt",
                        barList = lsBar
                    )
                )
            }
        }
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