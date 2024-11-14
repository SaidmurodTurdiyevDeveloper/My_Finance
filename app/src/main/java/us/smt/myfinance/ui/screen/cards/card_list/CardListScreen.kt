package us.smt.myfinance.ui.screen.cards.card_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel

class CardListScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<CardListViewModel>()
        val state by viewModel.state.collectAsState()
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
            TopAppBar(title = { Text("Credit Cards") }, navigationIcon = {
                IconButton(
                    onClick = { onAction(CardListIntent.Back) },
                    content = { Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back") }
                )
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onAction(CardListIntent.AddCard)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Credit Card")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.creditCards) { card ->
                CreditCardItem(card = card)
            }
        }
    }
}
