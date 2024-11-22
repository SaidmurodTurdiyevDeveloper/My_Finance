package us.smt.myfinance.ui.screen.home.statistic

import androidx.compose.ui.graphics.Color
import us.smt.myfinance.domen.model.CostData
import us.smt.myfinance.ui.screen.home.create_cost.DailyExpenses
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

data class StatisticData(
    val title: String,
    val value: Double,
    val color: Color
)
fun getRandomColor(): Color {
    val red = Random.nextInt(0, 256)
    val green = Random.nextInt(0, 256)
    val blue = Random.nextInt(0, 256)
    return Color(red, green, blue)
}

fun calculateCostByType(costs: List<CostData>): List<StatisticData> {
    val groupedByType = costs.groupBy { it.type }
    val statistics = ArrayList<StatisticData>()
    groupedByType.forEach { (type, costList) ->
        val totalCost = costList.sumOf { it.amount }

        val color = when (type) {
            DailyExpenses.FOOD -> Color.Green
            DailyExpenses.TRANSPORT -> Color.Blue
            DailyExpenses.ENTERTAINMENT -> Color.Yellow
            DailyExpenses.UTILITIES -> Color.Cyan
            DailyExpenses.HEALTH -> Color.Red
            DailyExpenses.EDUCATION -> Color.Magenta
        }

        val statistic = StatisticData(
            title = type.name,
            value = totalCost.toDouble(),
            color = color
        )
        statistics.add(statistic)
    }

    return statistics
}

fun calculateDailyCost(costs: List<CostData>): List<StatisticData> {
    // Define the date format to group by day (ignoring time)
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // Group by date
    val groupedByDate = costs.groupBy { costData ->
        dateFormat.format(Date(costData.timeMillisecond.toLong())) // Convert to date format
    }

    // Calculate total cost for each day and create a StatisticData list
    val statistics = ArrayList<StatisticData>()
    groupedByDate.forEach { (date, costList) ->
        val totalCost = costList.sumBy { it.amount } // Sum all amounts for the day
        val statistic = StatisticData(
            title = "Date: $date", // Title with the date
            value = totalCost.toDouble(), // Value is the sum of costs for that day
            color = getRandomColor() // Choose any color you prefer (this can be customized)
        )
        statistics.add(statistic)
    }

    return statistics
}