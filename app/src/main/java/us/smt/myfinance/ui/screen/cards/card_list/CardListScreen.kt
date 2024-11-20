package us.smt.myfinance.ui.screen.cards.card_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.LifecycleEffectOnce
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import us.smt.myfinance.domen.model.CreditCard

class CardListScreen : Screen {
    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val viewModel = getViewModel<CardListViewModel>()
        val state by viewModel.state.collectAsState()
        LifecycleEffectOnce {
            viewModel.onAction(CardListIntent.LoadData)
        }
        CreditCardListScreen(state = state, onAction = viewModel::onAction)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreditCardListScreen(
    state: CardListState,
    onAction: (CardListIntent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Credit Cards", style = MaterialTheme.typography.titleLarge.copy(color = Color.White)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(CardListIntent.Back) }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1E88E5), // Blue color for the app bar
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAction(CardListIntent.AddCard) },
                containerColor = Color(0xFF1E88E5)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Credit Card", tint = Color.White)
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .background(Color(0xFFF5F5F5))
            ) {
                Text(
                    text = "My Credit Cards",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color(0xFF1E88E5), // Blue color for the title text
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn(
                    contentPadding = PaddingValues(vertical = 16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.creditCards) { card ->
                        CreditCardItem(card = card)
                    }
                }
            }
        }
    )
}


@Preview
@Composable
private fun CreditCardListScreenPrev() {
    CreditCardListScreen(
        CardListState(
            creditCards = listOf(
                CreditCard(
                    cardHolderName = "Saidmurod",
                    cardNumber = "1234 1234 1234 1234",
                    expiryDate = "12/25",
                    money = 12000000,
                    cvv = ""
                )
            )
        )
    ) {}
}
