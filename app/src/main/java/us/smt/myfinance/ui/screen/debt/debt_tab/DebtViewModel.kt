package us.smt.myfinance.ui.screen.debt.debt_tab

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.myfinance.data.database.local.shared.LocalStorage
import us.smt.myfinance.domen.model.DebtOweData
import us.smt.myfinance.ui.utils.AppNavigator
import us.smt.myfinance.ui.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class DebtViewModel @Inject constructor(navigator: AppNavigator, private val localStorage: LocalStorage) : BaseViewModel<DebtState, DebtIntent>(DebtState(), navigator) {
    override fun onAction(intent: DebtIntent) {
        when (intent) {
            is DebtIntent.AddDebt -> openDebtScreen(intent.debt)
            is DebtIntent.AddOwe -> openOweScreen(intent.owe)
            DebtIntent.OpenAddDebt -> openAddDialog()
            is DebtIntent.OpenCompleteDebt -> openCompleteDialog(intent.id)
            DebtIntent.CloseAddDebt -> closeAddDialog()
            DebtIntent.CloseCompleteDebt -> closeCompleteDialog()
            is DebtIntent.CompleteDept -> completeDebt(intent.isDebt)
        }
    }

    init {
        loadList()
    }

    private fun completeDebt(isDebt: Boolean) {
        if (isDebt) {
            val ls = state.value.debts.filter { it.id != state.value.isOpenCompleteDialog }
            update(state = state.value.copy(debts = ls, isOpenCompleteDialog = null))
        } else {
            val ls = state.value.owes.filter { it.id != state.value.isOpenCompleteDialog }
            update(state = state.value.copy(owes = ls, isOpenCompleteDialog = null))
        }
        saveList()
    }

    private fun loadList() {
        val gson = Gson()
        val itemType = object : TypeToken<List<DebtOweData>>() {}.type
        val owes: List<DebtOweData> = if (localStorage.owes.isEmpty()) emptyList() else gson.fromJson(localStorage.owes, itemType)
        val debts: List<DebtOweData> = if (localStorage.debts.isEmpty()) emptyList() else gson.fromJson(localStorage.debts, itemType)
        update(state = state.value.copy(debts = debts.toList(), owes = owes.toList()))
    }

    private fun openAddDialog() {
        update(state = state.value.copy(isOpenAddDialog = true))
    }

    private fun openCompleteDialog(id: String) {
        update(state = state.value.copy(isOpenCompleteDialog = id))
    }

    private fun closeAddDialog() {
        update(state = state.value.copy(isOpenAddDialog = false))
    }

    private fun closeCompleteDialog() {
        update(state = state.value.copy(isOpenCompleteDialog = null))
    }

    private fun openDebtScreen(data: DebtOweData) {
        val ls = state.value.debts.toMutableList()
        ls.add(data.copy(id = ls.size.toString()))
        update(state = state.value.copy(debts = ls, isOpenAddDialog = false))
        saveList()
    }

    private fun openOweScreen(data: DebtOweData) {
        val ls = state.value.owes.toMutableList()
        ls.add(data.copy(id = ls.size.toString()))
        update(state = state.value.copy(owes = ls, isOpenAddDialog = false))
        saveList()
    }

    override fun onCleared() {
        saveList()
        super.onCleared()
    }

    private fun saveList() {
        val gson = Gson()
        val lsDebt = state.value.debts
        val lsJson = gson.toJson(lsDebt)
        val lsOwe = state.value.owes
        val lsJsonOwe = gson.toJson(lsOwe)
        localStorage.owes = lsJsonOwe
        localStorage.debts = lsJson
    }
}