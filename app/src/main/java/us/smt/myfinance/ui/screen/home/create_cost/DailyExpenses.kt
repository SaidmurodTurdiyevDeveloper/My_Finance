package us.smt.myfinance.ui.screen.home.create_cost

import us.smt.myfinance.R

enum class DailyExpenses(private val description: String, private val resIcon: Int) {
    FOOD("Food", R.drawable.food),
    TRANSPORT("Transport", R.drawable.transport),
    ENTERTAINMENT("Entertainment", R.drawable.entertainment),
    UTILITIES("Utilities", R.drawable.utilities),
    HEALTH("Healthcare", R.drawable.healthcare),
    EDUCATION("Education", R.drawable.education);

    fun getExpenseDetails(): String {
        return description
    }

    fun getResIcon(): Int {
        return resIcon
    }
}