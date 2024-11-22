package us.smt.myfinance.ui.screen.home.create_cost

sealed interface CreateCostIntent {
    data object Back : CreateCostIntent
    data object AddCost : CreateCostIntent
    data class ChangeAmount(val amount: String) : CreateCostIntent
    data class ChangeDescription(val description: String) : CreateCostIntent
    data class ChangeName(val type: String) : CreateCostIntent
    data class ChangeType(val type: DailyExpenses) : CreateCostIntent
}