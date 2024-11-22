package us.smt.myfinance.ui.screen.cards.card_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import us.smt.myfinance.R
import us.smt.myfinance.domen.model.CreditCard
import us.smt.myfinance.util.toMoneyType

@Composable
fun CreditCardItem(card: CreditCard) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .background(Color(0xFF2A2A2A), shape = RoundedCornerShape(16.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.credit_card),
                contentDescription = "Card Provider Logo",
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(
                    text = card.cardNumber,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color(0xFFD1D1D1),
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = card.cardHolderName,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color(0xFF9E9E9E),
                        letterSpacing = 1.5.sp
                    )
                )
                Text(
                    text = "Exp: ${card.expiryDate}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF757575)
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Balance: ${card.money.toString().toMoneyType()}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color(0xFFFFD700),
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

