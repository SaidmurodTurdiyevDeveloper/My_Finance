package us.smt.myfinance.ui.screen.payments.payment_tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
    val paymentsList = listOf(
        "Elektr energiyasi" to Icons.Default.Warning,
        "Internet to'lovlari" to Icons.Default.Warning,
        "Gaz to'lovlari" to Icons.Default.Warning,
        "Suv to'lovlari" to Icons.Default.Warning,
        "Elektr energiyasi" to Icons.Default.Warning,
        "Internet to'lovlari" to Icons.Default.Warning,
        "Gaz to'lovlari" to Icons.Default.Warning,
        "Suv to'lovlari" to Icons.Default.Warning,
        "Elektr energiyasi" to Icons.Default.Warning,
        "Internet to'lovlari" to Icons.Default.Warning,
        "Gaz to'lovlari" to Icons.Default.Warning,
        "Suv to'lovlari" to Icons.Default.Warning,
        "Elektr energiyasi" to Icons.Default.Warning,
        "Internet to'lovlari" to Icons.Default.Warning,
        "Gaz to'lovlari" to Icons.Default.Warning,
        "Suv to'lovlari" to Icons.Default.Warning,
        "Elektr energiyasi" to Icons.Default.Warning,
        "Internet to'lovlari" to Icons.Default.Warning,
        "Gaz to'lovlari" to Icons.Default.Warning,
        "Suv to'lovlari" to Icons.Default.Warning,
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Kamunal to'lovlar", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(paymentsList) { payment ->
                CommunalItem(
                    payment = payment,
                    onClick = {
                    }
                )
            }
        }
    }
}

@Composable
fun CommunalItem(payment: Pair<String, ImageVector>, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor =  Color(0xFFBCE1F6))) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = payment.second, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text =
                payment.first, style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
            )
        }
    }
}

