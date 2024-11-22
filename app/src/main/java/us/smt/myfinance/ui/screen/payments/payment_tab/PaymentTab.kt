package us.smt.myfinance.ui.screen.payments.payment_tab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import us.smt.myfinance.R

object PaymentTab : Tab {
    private fun readResolve(): Any = PaymentTab
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 1u,
            title = "Payment",
            icon = painterResource(R.drawable.ic_payments)
        )

    @Composable
    override fun Content() {
        val viewModel = getViewModel<PaymentListViewModel>()
        CommunalScreen(
            openScreen = viewModel::openPayment
        )
    }
}

@Composable
private fun CommunalScreen(
    openScreen: (index: Int) -> Unit
) {
    val paymentsList = listOf(
        0 to R.drawable.electr_energy,
        1 to R.drawable.internet,
        2 to R.drawable.gas,
        3 to R.drawable.water,
        4 to R.drawable.garbage,
        5 to R.drawable.heating,
        6 to R.drawable.tv,
        7 to R.drawable.maintenance
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
    ) {
        // Screen title
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "Communal Payments",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(paymentsList) { index, payment ->
                CommunalItem(
                    payment = payment,
                    onClick = {
                        openScreen(index)
                    }
                )
            }
        }
    }
}

fun getPaymentTitle(index: Int): String = when (index) {
    0 -> "Electricity"
    1 -> "Internet Bills"
    2 -> "Gas Bills"
    3 -> "Water Bills"
    4 -> "Garbage Collection"
    5 -> "Heating"
    6 -> "Television Subscription"
    7 -> "Maintenance Fees"
    else -> ""
}

fun getPaymentResource(index: Int): Int = when (index) {
    0 -> R.drawable.electr_energy
    1 -> R.drawable.internet
    2 -> R.drawable.gas
    3 -> R.drawable.water
    4 -> R.drawable.garbage
    5 -> R.drawable.heating
    6 -> R.drawable.tv
    7 -> R.drawable.maintenance
    else -> -1
}

@Composable
fun CommunalItem(payment: Pair<Int, Int>, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon for payment type
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                painter = painterResource(payment.second),
                contentDescription = getPaymentTitle(payment.first)
            )
            Spacer(modifier = Modifier.width(16.dp))

            // Payment description
            Text(
                text = getPaymentTitle(payment.first),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}


