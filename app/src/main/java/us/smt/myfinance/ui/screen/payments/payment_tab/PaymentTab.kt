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
import androidx.compose.foundation.lazy.items
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
        CommunalScreen()
    }
}

@Composable
private fun CommunalScreen() {
    // Updated list of payments in English
    val paymentsList = listOf(
        "Electricity" to R.drawable.electr_energy,
        "Internet Bills" to R.drawable.internet,
        "Gas Bills" to R.drawable.gas,
        "Water Bills" to R.drawable.water,
        "Garbage Collection" to R.drawable.garbage,
        "Heating" to R.drawable.heating,
        "Television Subscription" to R.drawable.tv,
        "Maintenance Fees" to R.drawable.maintenance
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
            items(paymentsList) { payment ->
                CommunalItem(
                    payment = payment,
                    onClick = {
                        // Action when item is clicked
                    }
                )
            }
        }
    }
}

@Composable
fun CommunalItem(payment: Pair<String, Int>, onClick: () -> Unit) {
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
                contentDescription = payment.first
            )
            Spacer(modifier = Modifier.width(16.dp))

            // Payment description
            Text(
                text = payment.first,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}


