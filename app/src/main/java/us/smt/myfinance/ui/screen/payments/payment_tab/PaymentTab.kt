package us.smt.myfinance.ui.screen.payments.payment_tab

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
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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
        KamunallarScreen()
    }
}

@Composable
private fun KamunallarScreen() {
    // Kamunal to'lovlar ro'yxati
    val paymentsList = listOf(
        "Elektr energiyasi",
        "Internet to'lovlari",
        "Gaz to'lovlari",
        "Suv to'lovlari"
    )

    // To'lov tafsilotlarini ko'rsatish uchun state
    val selectedPayment = remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text("Kamunal to'lovlar", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        // Ro'yxatni ko'rsatish
        LazyColumn {
            items(paymentsList) { payment ->
                KamunalItem(payment = payment, onClick = {
                    selectedPayment.value = payment
                })
            }
        }

        // Agar item bosilgan bo'lsa, tafsilot oynasini ko'rsatish
        selectedPayment.value?.let {
            KamunalDetailsScreen(payment = it, onClose = {
                selectedPayment.value = null
            })
        }
    }
}

@Composable
private fun KamunalItem(payment: String, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onClick() }) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.ThumbUp, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = payment, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun KamunalDetailsScreen(payment: String, onClose: () -> Unit) {
    // Tafsilotlar oynasi
    Dialog(onDismissRequest = onClose) {
        Surface(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "$payment uchun tafsilotlar", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                // To'lov miqdori yoki boshqa tafsilotlar
                Text("To'lov miqdori: 100,000 so'm", fontSize = 18.sp)

                Spacer(modifier = Modifier.height(24.dp))

                // Yopish tugmasi
                Button(onClick = onClose) {
                    Text("Yopish")
                }
            }
        }
    }
}
