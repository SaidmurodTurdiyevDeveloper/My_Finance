package us.smt.myfinance.ui.screen.home.home_tab

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import us.smt.myfinance.R
import us.smt.myfinance.domen.model.FundData
import us.smt.myfinance.ui.dialog.DeleteDialog
import us.smt.myfinance.util.toMoneyType

class HomeTab(private val viewModel: HomeViewModel) : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 0u,
            title = "Home",
            icon = rememberVectorPainter(Icons.Default.Home)
        )

    @Composable
    override fun Content() {
        val state by viewModel.state.collectAsState()
        LaunchedEffect(key1 = Unit) {
            viewModel.onAction(HomeIntent.Init)
        }
        PaymentHomeScreen(
            state = state,
            onAction = viewModel::onAction
        )
    }
}

@Composable
private fun PaymentHomeScreen(
    state: HomeState,
    onAction: (HomeIntent) -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets.statusBars,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAction(HomeIntent.OpenAddCost) },
                containerColor = Color(0xFF1E88E5),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Expense")
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color(0xFFF2F4F6))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "My Finance",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF331E4D) // Title Color
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple()
                        ) { onAction(HomeIntent.GetTransactions) }
                        .background(Color(0xFF6E9BDC), shape = RoundedCornerShape(12.dp))
                        .padding(24.dp)
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        TotalCostText(state = state)
                        Text(
                            text = "Cards All Balance",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        bottom = 80.dp
                    )
                ) {
                    item {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                        ) {
                            item {
                                ServiceItem(
                                    color = Color(0xFFE2EBFD),
                                    amount = state.allCosts.ifBlank { "0" },
                                    withColor = false,
                                    title = stringResource(R.string.last_month_cost),
                                    onClick = {
                                        onAction(HomeIntent.OpenStatistic(0))
                                    }
                                )
                            }
                            item {
                                ServiceItem(
                                    color = Color(0xFFFCFFDD),
                                    amount = state.allFunds.ifBlank { "0" },
                                    withColor = false,
                                    title = stringResource(R.string.all_fund),
                                    onClick = {
                                        onAction(HomeIntent.OpenStatistic(1))
                                    })
                            }

                            item {
                                ServiceItem(
                                    color = Color(0xFFE3FFDD),
                                    amount = state.allOwe.ifBlank { "0" },
                                    withColor = false,
                                    title = stringResource(R.string.all_owes),
                                    onClick = {
                                        onAction(HomeIntent.OpenStatistic(2))
                                    })
                            }
                            item {
                                ServiceItem(
                                    color = Color(0xFFFFCBDA),
                                    amount = state.allDebt.ifBlank { "0" },
                                    withColor = true,
                                    title = stringResource(R.string.all_debt),
                                    onClick = {
                                        onAction(HomeIntent.OpenStatistic(3))
                                    }
                                )
                            }

                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp, start = 16.dp, end = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Funds",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF331E4D)
                                )
                            )
                            TextButton(
                                onClick = { onAction(HomeIntent.OpenFound) },
                                content = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "All",
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF29AAFF) // Section Title Color
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null,
                                            tint = Color(0xFF29AAFF)
                                        )
                                    }
                                }
                            )
                        }
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                        ) {
                            if (state.funds.isEmpty()) {
                                item {
                                    AddFundCardItem(onAction = onAction)
                                }
                            } else {
                                items(state.funds) { saving ->
                                    FundCardItem(data = saving, onClick = {
                                        onAction(HomeIntent.OpenFound)
                                    })
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = stringResource(R.string.today_cost),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF331E4D) // Section Title Color
                            ),
                            modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    if (state.costs.isEmpty()) {
                        item {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp), contentAlignment = Alignment.Center
                            ) {
                                AnimatedPreloader(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                        }
                    } else {
                        items(state.costs) { expense ->
                            FinancialSection(
                                title = expense.name,
                                image = if (expense.imageRes != -1) expense.imageRes else expense.type.getResIcon(),
                                amount = expense.amount.toString().ifBlank { "0" },
                                onClick = {
                                    onAction(HomeIntent.OpenDeleteExpense(expense.id))
                                }
                            )
                        }
                    }
                }
            }
        }
    )

    if (state.isDeletingExpenseId != null) {
        DeleteDialog(
            onDismiss = {
                onAction(HomeIntent.DismissDeleteDialog)
            }
        ) {
            onAction(HomeIntent.DeleteExpense)
        }
    }
}

@Composable
private fun TotalCostText(
    state: HomeState
) {
    val totalCost = remember(key1 = state.balance) {
        mutableIntStateOf(state.balance)
    }
    val animatedValue by animateIntAsState(totalCost.intValue, label = "")
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = animatedValue.toString().toMoneyType() + " $",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

    }

}

@Composable
private fun ServiceItem(
    color: Color,
    amount: String,
    withColor: Boolean,
    title: String,
    onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val temp = ((screenWidth - 48) / 3)
    val width = (if (temp in 100..160) temp else 110).dp
    val textColor = if (withColor) {
        val t = amount.toInt()
        if (t > 0) {
            Color.Green
        } else if (t < 0) Color.Red
        else Color.Gray
    } else Color.Gray
    Column(
        modifier = Modifier
            .background(
                color = color,
                shape = RoundedCornerShape(12.dp)
            )
            .widthIn(min = width)
            .clickable {
                onClick()
            }
            .height(88.dp)
            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp, top = 12.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(
                color = Color.DarkGray,
                fontWeight = FontWeight(400),
                fontSize = 14.sp,
                lineHeight = 14.sp
            )
        )
        Spacer(Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${amount.toMoneyType()} $",
                style = TextStyle(
                    color = textColor,
                    fontWeight = FontWeight(400),
                    fontSize = 24.sp,
                    lineHeight = 26.sp
                )
            )
        }

    }
}

@Composable
fun AnimatedPreloader(modifier: Modifier = Modifier) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.lottie_empty
        )
    )
    val height = LocalConfiguration.current.screenWidthDp - 80

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )


    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = preloaderProgress,
        modifier = modifier.height(height.dp)
    )
}

@Composable
private fun FinancialSection(
    title: String,
    @DrawableRes
    image: Int,
    amount: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFFF5F5F5),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
            painter = painterResource(image),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(16.dp)) // Spacing between image and text()
        Column(
            modifier = Modifier.weight(1f) // Ensures the text takes available space
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000), // Section Title Color
                    fontSize = 20.sp // Increased font size for the title
                ),
                modifier = Modifier.padding(bottom = 4.dp) // Spacing between title and amount
            )
            Text(
                text = "${amount.toMoneyType()} $",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Gray,
                    fontSize = 18.sp // Increased font size for the amount
                )
            )
        }
        IconButton(
            onClick = onClick,
            content = {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Go to details",
                    tint = Color.Gray
                )
            },
        )
    }
}


@Composable
private fun AddFundCardItem(onAction: (HomeIntent) -> Unit) {
    Card(
        modifier = Modifier
            .height(80.dp)
            .width(250.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        ),
        onClick = { onAction(HomeIntent.OpenAddFound) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color(0xFF448062),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Add items",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF448062)
            )
        }

    }
}

@Composable
private fun FundCardItem(
    data: FundData,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(100.dp)
            .width(250.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = data.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = data.amount.toMoneyType() + " $",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000)
                )
            }
            Image(
                modifier = Modifier.size(36.dp),
                painter = painterResource(R.drawable.coin),
                contentDescription = "Coin"
            )
        }
    }
}

