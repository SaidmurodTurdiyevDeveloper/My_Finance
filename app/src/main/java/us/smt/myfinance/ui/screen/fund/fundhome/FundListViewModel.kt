package us.smt.myfinance.ui.screen.fund.fundhome

import androidx.core.text.isDigitsOnly
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.myfinance.data.database.local.shared.LocalStorage
import us.smt.myfinance.domen.model.FundData
import us.smt.myfinance.ui.screen.fund.addfundmoney.CreateFundScreen
import us.smt.myfinance.ui.utils.AppNavigator
import us.smt.myfinance.ui.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class FundListViewModel @Inject constructor(
    appNavigator: AppNavigator,
    private val localStorage: LocalStorage
) : BaseViewModel<FundState, FundListIntent>(initializeData = FundState(), appNavigator = appNavigator) {


    override fun onAction(intent: FundListIntent) {
        when (intent) {
            FundListIntent.AddFund -> openAddFund()
            FundListIntent.Back -> back()
            FundListIntent.LoadData -> loadList()
            is FundListIntent.OpenAddFundMoneyDialog -> openAddMoneyDialog(id = intent.dataId)
            is FundListIntent.AddFundMoney -> addFundMoney(intent.dataId, intent.money)
            FundListIntent.CloseDialog -> closeAddMoneyDialog()
        }
    }

    private fun addFundMoney(id: String, amount: String) {
        if (amount.isBlank()) return
        if (!amount.isDigitsOnly()) return
        val ls = state.value.fundList.map {
            if (it.id == id) {
                it.copy(amount = (it.amount.toInt() + amount.toInt()).toString())
            } else {
                it
            }
        }
        update(state.value.copy(fundList = ls, isOpenDialog = null))
        localStorage.funds = Gson().toJson(ls)
    }

    private fun openAddMoneyDialog(id: String) {
        update(state.value.copy(isOpenDialog = id))
    }

    private fun closeAddMoneyDialog() {
        update(state.value.copy(isOpenDialog = null))
    }

    private fun loadList() {
        val gson = Gson()
        val itemType = object : TypeToken<List<FundData>>() {}.type
        val funds: List<FundData> = if (localStorage.funds.isEmpty()) emptyList() else gson.fromJson(localStorage.funds, itemType)
        update(state = state.value.copy(fundList = funds))
    }

    private fun openAddFund() {
        navigate(CreateFundScreen())
    }

}