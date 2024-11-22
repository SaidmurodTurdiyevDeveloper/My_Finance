package us.smt.myfinance.ui.screen.home.statistic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import us.smt.myfinance.ui.screen.home.chart.BarChart
import us.smt.myfinance.ui.screen.home.chart.PieChart

class StatisticScreen(
    private var title: String,
    private var pieList: List<StatisticData>,
    private var barList: List<StatisticData>
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navigator?.pop()
                            }
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF1E88E5), // Blue color for the app bar
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ),
                    title = { Text(text = title, style = MaterialTheme.typography.headlineSmall) },
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (pieList.isEmpty() && barList.isEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(200.dp))
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(32.dp), contentAlignment = Alignment.Center
                        ) {
                            ChartEmpty(
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(100.dp)
                            )
                        }
                    }
                } else {
                    if (pieList.isNotEmpty()) {
                        item {
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 24.dp, horizontal = 32.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    PieChart(
                                        list = pieList
                                    )

                                }
                            }
                        }
                        items(pieList) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 2.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = it.title + " - " + it.value + "$", style = MaterialTheme.typography.labelSmall)
                                Box(
                                    Modifier
                                        .size(18.dp)
                                        .background(it.color, shape = RoundedCornerShape(8.dp))
                                )
                            }
                        }
                    }
                    if (barList.isNotEmpty()) {

                        item {

                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                BarChart(
                                    values = barList
                                )

                            }
                        }
                        items(barList) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 2.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = it.title, style = MaterialTheme.typography.labelSmall)
                                Box(
                                    Modifier
                                        .size(18.dp)
                                        .background(it.color, shape = RoundedCornerShape(8.dp))
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}