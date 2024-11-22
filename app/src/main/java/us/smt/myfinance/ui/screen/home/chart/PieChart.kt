package us.smt.myfinance.ui.screen.home.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import us.smt.myfinance.ui.screen.home.statistic.StatisticData
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
internal fun PieChart(
    modifier: Modifier = Modifier,
    list: List<StatisticData>
) {
    val chartDegrees = 360f
    var startAngle = 270.0

    val all = list.sumOf { it.value }

    // Calculate proportions as percentages
    val proportions = list.map {
        it.value * 100 / all
    }

    // Calculate the angles for each segment based on the proportion
    val angleProgress = proportions.map { prop ->
        chartDegrees * prop / 100
    }

    val progressSize = mutableListOf<Double>()

    // To store the cumulative progress (used for positioning the text)
    LaunchedEffect(angleProgress) {
        progressSize.add(angleProgress.first())
        for (x in 1 until angleProgress.size) {
            progressSize.add(angleProgress[x] + progressSize[x - 1])
        }
    }

    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {
        val canvasSize = min(constraints.maxWidth, constraints.maxHeight)
        val size = Size(canvasSize.toFloat(), canvasSize.toFloat())
        val canvasSizeDp = with(LocalDensity.current) { canvasSize.toDp() }

        Canvas(modifier = Modifier.size(canvasSizeDp)) {

            angleProgress.forEachIndexed { index, angle ->
                drawArc(
                    color = list[index].color,
                    startAngle = startAngle.toFloat(),
                    sweepAngle = angle.toFloat(),
                    useCenter = true,
                    size = size,
                    style = Fill
                )
                if (angleProgress.size>1){
                    val middleAngle = startAngle + angle / 2
                    val x = (size.width / 2) + (size.width / 2) * cos(Math.toRadians(middleAngle))
                    val y = (size.height / 2) + (size.height / 2) * sin(Math.toRadians(middleAngle))

                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            "${proportions[index].toInt()}%", // Text to show percentage
                            x.toFloat(),
                            y.toFloat(),
                            android.graphics.Paint().apply {
                                color = android.graphics.Color.BLACK
                                textAlign = android.graphics.Paint.Align.CENTER
                                textSize = 40f // Adjust text size as needed
                            }
                        )
                    }
                }
                startAngle += angle
            }
        }
    }
}
