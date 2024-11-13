package us.smt.myfinance.ui.screen.home.home_tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object HomeTab : Tab {
    private fun readResolve(): Any = HomeTab
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 0u,
            title = "Home",
            icon = rememberVectorPainter(Icons.Default.Home)
        )

    @Composable
    override fun Content() {
        val viewmodel = getViewModel<HomeViewModel>()
        val state by viewmodel.state.collectAsState()

        PaymentHomeScreen(
            state = state,
            onAction = viewmodel::onAction
        )
    }
}

@Composable
private fun PaymentHomeScreen(
    state: HomeState,
    onAction: (HomeIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F4F6))
            .padding(16.dp)
    ) {
        // Top Bar with options
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Finance",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        // Balance Overview
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(interactionSource = remember { MutableInteractionSource() }, indication = ripple()) {
                    onAction(HomeIntent.GetTransactions)
                }
                .background(Color(0xFF5E92F3), shape = RoundedCornerShape(12.dp))
                .padding(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$5,240.75",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Available Balance",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ActionButton(
                label = "Send",
                icon = {
                    Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Send")
                },
                backgroundColor = Color(0xFF66BB6A),
                onClick = { /* Handle send action */ }
            )
            ActionButton(
                label = "Request",
                icon = {
                    Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Send")
                },
                backgroundColor = Color(0xFFFFA726),
                onClick = { /* Handle request action */ }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Recent Transactions Section
        Text(
            text = "Recent Transactions",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Sample list of recent transactions
        Column(modifier = Modifier.fillMaxWidth()) {
            TransactionItem(name = "Starbucks", amount = "-$12.45", date = "Today")
            TransactionItem(name = "PayPal", amount = "+$250.00", date = "Yesterday")
            TransactionItem(name = "Amazon", amount = "-$65.00", date = "Nov 10")
        }
    }
}

@Composable
private fun ActionButton(label: String, icon: @Composable () -> Unit, backgroundColor: Color, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(56.dp)
                .background(backgroundColor, shape = RoundedCornerShape(50))
        ) {
            icon()
        }
        Text(text = label, fontSize = 14.sp)
    }
}

@Composable
private fun TransactionItem(name: String, amount: String, date: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = date, fontSize = 14.sp, color = Color.Gray)
        }
        Text(text = amount, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = if (amount.startsWith("-")) Color.Red else Color.Green)
    }
}
